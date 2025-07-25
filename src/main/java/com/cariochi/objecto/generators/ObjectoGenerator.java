package com.cariochi.objecto.generators;

import com.cariochi.objecto.config.ObjectoConfig;
import com.cariochi.objecto.generators.model.PostProcessor;
import com.cariochi.objecto.instantiators.ConstructorProvider;
import com.cariochi.objecto.instantiators.InterfaceProvider;
import com.cariochi.objecto.instantiators.StaticMethodProvider;
import com.cariochi.objecto.random.ObjectoRandom;
import com.cariochi.objecto.utils.ConstructorUtils;
import com.cariochi.reflecto.methods.TargetMethod;
import com.cariochi.reflecto.types.ReflectoType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static com.cariochi.objecto.config.ObjectoConfig.DEFAULT_SETTINGS;
import static com.cariochi.reflecto.Reflecto.reflect;
import static org.apache.commons.lang3.StringUtils.isNoneBlank;


@Slf4j
public class ObjectoGenerator {

    @Getter private final ObjectoRandom random = new ObjectoRandom();

    private final List<Function<Context, Object>> providers;
    private final List<ReferenceGenerator> referenceGenerators = new ArrayList<>();
    private final List<FieldGenerator> fieldGenerators = new ArrayList<>();
    private final List<TypeGenerator> typeGenerators = new ArrayList<>();
    private final List<ComplexGenerator> complexGenerators = new ArrayList<>();
    private final List<Generator> defaultGenerators = List.of(
            new StringGenerator(),
            new NumberGenerator(),
            new UUIDGenerator(),
            new BooleanGenerator(),
            new CollectionGenerator(this),
            new StreamGenerator(this),
            new MapGenerator(this),
            new ArrayGenerator(this),
            new TemporalGenerator(),
            new CharacterGenerator(),
            new OptionalGenerator(this),
            new PrimitiveGenerator(),
            new EnumGenerator(),
            new CustomObjectGenerator(this)
    );

    private final List<PostProcessor> postProcessors = new ArrayList<>();

    public ObjectoGenerator() {
        this.providers = new ArrayList<>(List.of(
                new InterfaceProvider(this),
                new StaticMethodProvider(this),
                new ConstructorProvider(this)
        ));
    }

    public void use(ObjectoGenerator generator) {
        generator.providers.stream().filter(p -> !providers.contains(p)).forEach(providers::add);
        generator.referenceGenerators.stream().filter(p -> !referenceGenerators.contains(p)).forEach(referenceGenerators::add);
        generator.fieldGenerators.stream().filter(p -> !fieldGenerators.contains(p)).forEach(fieldGenerators::add);
        generator.typeGenerators.stream().filter(p -> !typeGenerators.contains(p)).forEach(typeGenerators::add);
        generator.complexGenerators.stream().filter(p -> !complexGenerators.contains(p)).forEach(complexGenerators::add);
        generator.postProcessors.stream().filter(p -> !postProcessors.contains(p)).forEach(postProcessors::add);
    }

    public void addProvider(ReflectoType type, Supplier<Object> provider) {
        providers.add(0, context -> type.equals(context.getType()) ? provider.get() : null);
    }

    @SuppressWarnings("unchecked")
    public <T> T newInstance(Context context) {
        log.trace("Creating instance of `{}` with type `{}`", context.getPath(), context.getType().name());
        return (T) providers.stream()
                .map(creator -> creator.apply(context))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    public void addReferenceGenerators(Type type, String[] paths) {
        Stream.of(paths)
                .forEach(path -> referenceGenerators.add(new ReferenceGenerator(type, path)));
    }

    public void addTypeGenerator(TargetMethod method) {
        typeGenerators.add(new TypeGenerator(method));
    }

    public void addFieldGenerator(ReflectoType objectType, String expression, TargetMethod method) {
        if (expression.contains("(")) {
            complexGenerators.add(new ComplexGenerator(objectType, expression, method));
        } else {
            fieldGenerators.add(new FieldGenerator(objectType, expression, method));
        }
    }

    public void addPostProcessor(ReflectoType type, TargetMethod postProcessor) {
        postProcessors.add(new PostProcessor(type, postProcessor));
    }

    public Object generate(Type type) {
        return generate(reflect(type), DEFAULT_SETTINGS);
    }

    public Object generate(ReflectoType type, ObjectoConfig objectoSettings) {
        final Context context = Context.builder()
                .type(type)
                .random(random)
                .config(objectoSettings)
                .build();
        return generate(context);
    }

    public Object generate(Context context) {

        final ReflectoType type = context.getType();
        final String contextPath = context.getPath();

        ObjectoConfig config = context.getConfig();
        log.trace("Generating `{}` with type `{}`", contextPath, type.getTypeName());
        if (context.getDepth() > config.maxDepth() + 1) {
            log.debug("Maximum depth ({}) reached. Path: {}", config.maxDepth(), contextPath);
            return null;
        }

        for (ReferenceGenerator generator : referenceGenerators) {
            if (generator.isSupported(context)) {
                return generator.generate(context);
            }
        }

        if (context.getRecursionDepth(type) >= config.maxRecursionDepth()) {
            log.debug(
                    "Maximum recursion depth ({}) reached. Path: {}. Type: {}",
                    config.maxRecursionDepth(),
                    contextPath,
                    context.getType().actualType().getTypeName()
            );
            return null;
        }

        if (context.getType().actualClass() == null) {
            return null;
        }

        if (context.getPrevious() != null) {

            final String value = config.setValue();
            if (isNoneBlank(value)) {
                final Object o = ConstructorUtils.parseString(type, value).orElse(null);
                if (o != null) {
                    return o;
                }
            }

            final ReflectoType parentType = context.getPrevious().getType();
            boolean isCollection = parentType.isArray() || parentType.is(Iterable.class);
            if (!isCollection) {
                if (config.setNull()) {
                    return null;
                }
                boolean withNulls = config.nullable() && (type.actualClass() == null || !type.isPrimitive());
                if (withNulls && context.getRandom().nextBoolean()) {
                    return null;
                }
            }

        }

        final List<? extends Generator> generators = Stream.of(fieldGenerators, typeGenerators, defaultGenerators)
                .flatMap(List::stream)
                .toList();

        for (Generator generator : generators) {
            if (generator.isSupported(context)) {
                final Object instance = generator.generate(context);
                applyComplexGenerators(context);
                return postProcess(context, instance);
            }
        }

        return null;
    }

    private void applyComplexGenerators(Context context) {
        complexGenerators.stream()
                .filter(generator -> generator.isSupported(context))
                .forEach(generator -> generator.generate(context));
    }

    private Object postProcess(Context context, Object instance) {
        if (instance == null) {
            return null;
        }
        postProcessors.stream()
                .filter(postProcessor -> postProcessor.getType().equals(context.getType()))
                .forEach(postProcessor -> postProcessor.invoke(context, instance));
        return instance;
    }
}
