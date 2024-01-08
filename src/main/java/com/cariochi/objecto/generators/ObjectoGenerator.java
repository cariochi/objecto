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

    private final List<BackReferenceGenerator> backReferenceGenerators = new ArrayList<>();
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

    private final Map<Type, List<Consumer<Object>>> postProcessors = new HashMap<>();

    public void addCustomConstructor(Type type, Supplier<Object> constructor) {
        objectConstructors.add(0, context -> type.equals(context.getType()) ? constructor.get() : null);
    }

    public void addBackReferenceGenerators(Type type, String[] paths) {
        Stream.of(paths)
                .forEach(path -> backReferenceGenerators.add(new BackReferenceGenerator(type, path)));
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
        return newContext(type, defaultSettings()).generate();
    }

    public Object generate(Context context) {
        final Type rawType = context.getType();
        if (rawType == null) {
            log.info("Cannot recognize a raw type `{}` of field `{}`. Please create an @Instantiator method to specify how to instantiate this class.", context.getType().getTypeName(), context.getPath());
            return null;
        }
        log.trace("Generating `{}` with type `{}`", context.getPath(), rawType.getTypeName());
        if (context.getDepth() >= context.getSettings().depth()) {
            log.debug("Max depth {} reached. Path: {}", context.getSettings().depth(), context.getPath());
            return null;
        }

        final Object backReferenceObject = backReferenceGenerators.stream()
                .filter(generator -> generator.isSupported(context)).findFirst()
                .map(generator -> generator.generate(context))
                .orElse(null);
        if (backReferenceObject != null) {
            return backReferenceObject;
        }

        if (context.getTypeDepth(rawType) >= context.getSettings().typeDepth()) {
            log.debug("Max depth {} reached for type {}. Path: {}", context.getSettings().typeDepth(), context.getType().getTypeName(), context.getPath());
            return null;
        }

        return Stream.of(fieldGenerators, typeGenerators, defaultGenerators).flatMap(List::stream)
                .filter(generator -> generator.isSupported(context)).findFirst()
                .map(generator -> generator.generate(context))
                .map(instance -> {
                    applyComplexGenerators(context);
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
