package com.cariochi.objecto.generators;

import com.cariochi.objecto.instantiators.ObjectoInstantiator;
import com.cariochi.objecto.settings.Settings;
import com.cariochi.objecto.utils.ObjectoRandom;
import com.cariochi.reflecto.methods.TargetMethod;
import com.cariochi.reflecto.types.ReflectoType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static com.cariochi.objecto.Objecto.defaultSettings;


@Slf4j
public class ObjectoGenerator {

    private final List<ReferenceGenerator> referenceGenerators = new ArrayList<>();
    private final List<FieldGenerator> fieldGenerators = new ArrayList<>();
    private final List<TypeGenerator> typeGenerators = new ArrayList<>();
    private final List<ComplexGenerator> complexGenerators = new ArrayList<>();
    private final List<Generator> defaultGenerators = List.of(
            new StringGenerator(),
            new NumberGenerator(),
            new BooleanGenerator(),
            new CollectionGenerator(this),
            new MapGenerator(this),
            new ArrayGenerator(this),
            new TemporalGenerator(),
            new CharacterGenerator(),
            new EnumGenerator(),
            new CustomObjectGenerator(this)
    );

    private final Map<ReflectoType, List<FieldSettings>> fieldSettings = new HashMap<>();
    private final Map<ReflectoType, List<Consumer<Object>>> postProcessors = new HashMap<>();
    @Getter private final ObjectoInstantiator instantiator = new ObjectoInstantiator(this);
    @Getter private final ObjectoRandom random = new ObjectoRandom();

    public void addCustomConstructor(ReflectoType type, Supplier<Object> instantiator) {
        this.instantiator.addCustomConstructor(type, instantiator);
    }

    public void addReferenceGenerators(Type type, String[] paths) {
        Stream.of(paths)
                .forEach(path -> referenceGenerators.add(new ReferenceGenerator(type, path)));
    }

    public void addFieldSettings(ReflectoType type, String path, Settings settings) {
        fieldSettings.computeIfAbsent(type, t -> new ArrayList<>()).add(new FieldSettings(path, settings));
    }

    public void addCustomGenerator(Class<?> objectType, String expression, TargetMethod method) {
        if (objectType.equals(Object.class)) {
            typeGenerators.add(new TypeGenerator(method));
        } else if (expression.contains("(")) {
            complexGenerators.add(new ComplexGenerator(objectType, expression, method));
        } else {
            fieldGenerators.add(new FieldGenerator(objectType, expression, method));
        }
    }

    public void addPostProcessor(ReflectoType type, Consumer<Object> postProcessor) {
        postProcessors.computeIfAbsent(type, t -> new ArrayList<>()).add(postProcessor);
    }

    public Object generate(Type type) {
        return generate(type, defaultSettings());
    }

    public Object generate(Type type, Settings settings) {
        return generate(new Context(type, settings, random));
    }

    public Object generate(Context context) {
        final ReflectoType type = context.getType();
        final String contextPath = context.getPath();
        final Context fieldContext = Optional.ofNullable(context.getPrevious())
                .map(Context::getType)
                .map(fieldSettings::get)
                .flatMap(typeSettings -> typeSettings.stream()
                        .filter(s -> s.getPath().isEmpty() || context.findPreviousContext(s.getPath()).isPresent()).findFirst()
                        .map(FieldSettings::getSettings)
                        .map(context::withFieldSettings))
                .orElse(context);

        log.trace("Generating `{}` with type `{}`", contextPath, type.getTypeName());
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

        if (fieldContext.getRecursionDepth(type) >= fieldContext.getSettings().maxRecursionDepth()) {
            log.debug(
                    "Maximum recursion depth ({}) reached. Path: {}. Type: {}",
                    fieldContext.getSettings().maxRecursionDepth(),
                    contextPath,
                    fieldContext.getType().actualType().getTypeName()
            );
            return null;
        }

        return Stream.of(fieldGenerators, typeGenerators, defaultGenerators).flatMap(List::stream)
                .filter(generator -> generator.isSupported(fieldContext)).findFirst()
                .map(generator -> generator.generate(fieldContext))
                .map(instance -> {
                    applyComplexGenerators(fieldContext);
                    return instance;
                })
                .map(instance -> postProcess(type, instance))
                .orElse(null);
    }

    private void applyComplexGenerators(Context context) {
        complexGenerators.stream()
                .filter(generator -> generator.isSupported(context))
                .forEach(generator -> generator.generate(context));
    }

    private Object postProcess(ReflectoType type, Object instance) {
        if (instance == null) {
            return null;
        }
        Optional.ofNullable(postProcessors.get(type)).stream()
                .flatMap(Collection::stream)
                .forEach(postProcessor -> postProcessor.accept(instance));
        return instance;
    }

}
