package com.cariochi.objecto.generators;

import com.cariochi.objecto.instantiators.ObjectoInstantiator;
import com.cariochi.objecto.settings.Settings;
import com.cariochi.objecto.utils.Random;
import com.cariochi.reflecto.types.TypeReflection;
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
import static com.cariochi.objecto.utils.Random.randomSeed;


@Slf4j
public class ObjectoGenerator {

    private final List<ReferenceGenerator> referenceGenerators = new ArrayList<>();
    private final List<FieldGenerator> fieldGenerators = new ArrayList<>();
    private final List<TypeGenerator> typeGenerators = new ArrayList<>();
    private final List<ComplexGenerator> complexGenerators = new ArrayList<>();
    private final List<AbstractGenerator> defaultGenerators = List.of(
            new StringGenerator(this),
            new NumberGenerator(this),
            new BooleanGenerator(this),
            new CollectionGenerator(this),
            new MapGenerator(this),
            new ArrayGenerator(this),
            new TemporalGenerator(this),
            new CharacterGenerator(this),
            new EnumGenerator(this),
            new CustomObjectGenerator(this)
    );

    private final Map<Type, List<FieldSettings>> fieldSettings = new HashMap<>();
    private final Map<Type, List<Consumer<Object>>> postProcessors = new HashMap<>();
    @Getter private final ObjectoInstantiator instantiator = new ObjectoInstantiator(this);
    private final Random random;

    public ObjectoGenerator() {
        this(randomSeed());
    }

    public ObjectoGenerator(Long seed) {
        this.random = new Random(seed);
    }

    public void addCustomConstructor(Type type, Supplier<Object> instantiator) {
        this.instantiator.addCustomConstructor(type, instantiator);
    }

    public void addReferenceGenerators(Type type, String[] paths) {
        Stream.of(paths)
                .forEach(path -> referenceGenerators.add(new ReferenceGenerator(this, type, path)));
    }

    public void addFieldSettings(Type type, String path, Settings settings) {
        fieldSettings.computeIfAbsent(type, t -> new ArrayList<>()).add(new FieldSettings(path, settings));
    }

    public void addCustomGenerator(Class<?> objectType, Type type, String expression, Supplier<Object> generator) {
        if (objectType.equals(Object.class)) {
            typeGenerators.add(new TypeGenerator(this, type, generator));
        } else if (expression.contains("(")) {
            complexGenerators.add(new ComplexGenerator(this, objectType, expression, generator));
        } else {
            fieldGenerators.add(new FieldGenerator(this, objectType, type, expression, generator));
        }
    }

    public void addPostProcessor(Type type, Consumer<Object> postProcessor) {
        postProcessors.computeIfAbsent(type, t -> new ArrayList<>()).add(postProcessor);
    }

    public Object generate(Type type) {
        return generate(type, defaultSettings(), null);
    }

    public Object generate(Type type, Settings settings, Long customSeed) {
        return generate(new Context(type, settings, Optional.ofNullable(customSeed).map(Random::new).orElse(random)));
    }

    public Object generate(Context context) {
        final Type type = context.getType().actualType();
        final String contextPath = context.getPath();
        final Context fieldContext = Optional.of(context.getType())
                .map(TypeReflection::getParentType)
                .map(TypeReflection::actualType)
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
