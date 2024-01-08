package com.cariochi.objecto.generators;

import com.cariochi.objecto.instantiators.ConstructorInstantiator;
import com.cariochi.objecto.instantiators.InterfaceInstantiator;
import com.cariochi.objecto.instantiators.StaticMethodInstantiator;
import com.cariochi.objecto.settings.Settings;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

import static com.cariochi.objecto.Objecto.defaultSettings;


@Slf4j
public class ObjectoGenerator {

    private final List<Function<Context, Object>> objectConstructors = new ArrayList<>(List.of(
            new InterfaceInstantiator(),
            new StaticMethodInstantiator(),
            new ConstructorInstantiator()
    ));

    private final List<ReferenceGenerator> referenceGenerators = new ArrayList<>();
    private final List<FieldGenerator> fieldGenerators = new ArrayList<>();
    private final List<TypeGenerator> typeGenerators = new ArrayList<>();
    private final List<ComplexGenerator> complexGenerators = new ArrayList<>();
    private final List<Generator> defaultGenerators = List.of(
            new StringGenerator(),
            new NumberGenerator(),
            new BooleanGenerator(),
            new CollectionGenerator(),
            new MapGenerator(),
            new ArrayGenerator(),
            new TemporalGenerator(),
            new PrimitiveGenerator(),
            new EnumGenerator(),
            new CustomObjectGenerator()
    );

    private final Map<Type, List<FieldSettings>> fieldSettings = new HashMap<>();

    private final Map<Type, List<Consumer<Object>>> postProcessors = new HashMap<>();

    public void addCustomConstructor(Type type, Supplier<Object> constructor) {
        objectConstructors.add(0, context -> type.equals(context.getType()) ? constructor.get() : null);
    }

    public void addReferenceGenerators(Type type, String[] paths) {
        Stream.of(paths)
                .forEach(path -> referenceGenerators.add(new ReferenceGenerator(type, path)));
    }

    public void addFieldSettings(Type type, String path, Settings settings) {
        fieldSettings.computeIfAbsent(type, t -> new ArrayList<>()).add(new FieldSettings(path, settings));
    }

    public void addCustomGenerator(Class<?> objectType, Type type, String expression, Supplier<Object> generator) {
        if (objectType.equals(Object.class)) {
            typeGenerators.add(new TypeGenerator(type, generator));
        } else if (expression.contains("(")) {
            complexGenerators.add(new ComplexGenerator(objectType, expression, generator));
        } else {
            fieldGenerators.add(new FieldGenerator(objectType, type, expression, generator));
        }
    }

    public void addPostProcessor(Type type, Consumer<Object> postProcessor) {
        postProcessors.computeIfAbsent(type, t -> new ArrayList<>()).add(postProcessor);
    }

    public Object newInstance(Context context) {
        log.trace("Creating instance of `{}` with type `{}`", context.getPath(), context.getType().getTypeName());
        return objectConstructors.stream()
                .map(creator -> creator.apply(context))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    public Object generate(Type type) {
        return generate(newContext(type, defaultSettings()));
    }

    public Object generate(Context context) {
        final Type rawType = context.getType();
        final String contextPath = context.getPath();
        if (rawType == null) {
            log.info("Cannot recognize a raw type `{}` of field `{}`. Please create an @Instantiator method to specify how to instantiate this class.", context.getType().getTypeName(), contextPath);
            return null;
        }

        final Context fieldContext;
        final List<FieldSettings> typeSettings = fieldSettings.get(context.getOwnerType());
        if (typeSettings != null) {
            fieldContext = typeSettings.stream()
                    .filter(s -> s.getPath().isEmpty() || context.findPreviousContext(s.getPath()).isPresent()).findFirst()
                    .map(FieldSettings::getSettings)
                    .map(context::withFieldSettings)
                    .orElse(context);
        } else {
            fieldContext = context;
        }

        log.trace("Generating `{}` with type `{}`", contextPath, rawType.getTypeName());
        if (fieldContext.getDepth() > fieldContext.getSettings().maxDepth() + 1) {
            log.debug("Maximum depth ({}) reached. Path: {}", fieldContext.getSettings().maxDepth(), contextPath);
            return null;
        }

        final Object backReferenceObject = referenceGenerators.stream()
                .filter(generator -> generator.isSupported(fieldContext)).findFirst()
                .map(generator -> generator.generate(fieldContext))
                .orElse(null);
        if (backReferenceObject != null) {
            return backReferenceObject;
        }

        if (fieldContext.getTypeDepth(rawType) >= fieldContext.getSettings().maxRecursionDepth()) {
            log.debug("Maximum recursion depth ({}) reached. Path: {}. Type: {}", fieldContext.getSettings().maxRecursionDepth(), contextPath, fieldContext.getType().getTypeName());
            return null;
        }

        return Stream.of(fieldGenerators, typeGenerators, defaultGenerators).flatMap(List::stream)
                .filter(generator -> generator.isSupported(fieldContext)).findFirst()
                .map(generator -> generator.generate(fieldContext))
                .map(instance -> {
                    applyComplexGenerators(fieldContext);
                    return instance;
                })
                .map(instance -> postProcess(rawType, instance))
                .orElse(null);
    }

    public Context newContext(Type type, Settings settings) {
        return new Context(type, settings, this);
    }

    private void applyComplexGenerators(Context context) {
        complexGenerators.stream()
                .filter(generator -> generator.isSupported(context))
                .forEach(generator -> generator.generate(context));
    }

    private Object postProcess(Type actualType, Object instance) {
        if (instance == null) {
            return null;
        }
        Optional.ofNullable(postProcessors.get(actualType)).stream()
                .flatMap(Collection::stream)
                .forEach(postProcessor -> postProcessor.accept(instance));
        return instance;
    }

}
