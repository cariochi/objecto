package com.cariochi.objecto.generator;

import com.cariochi.objecto.ObjectoSettings;
import com.cariochi.objecto.utils.GenericTypeUtils;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;

import static com.cariochi.objecto.ObjectoSettings.defaultSettings;

@Slf4j
public class ObjectoGenerator {

    private final InstanceCreator instanceCreator = new InstanceCreator(this);
    private final Map<String, List<Generator>> generators = new LinkedHashMap<>();
    private final Map<Type, List<Consumer<Object>>> postProcessors = new HashMap<>();

    public ObjectoGenerator() {
        generators.put("fields", new ArrayList<>());
        generators.put("types", new ArrayList<>());
        generators.put("default", List.of(
                new StringGenerator(this),
                new NumberGenerator(this),
                new BooleanGenerator(this),
                new CollectionGenerator(this),
                new MapGenerator(this),
                new ArrayGenerator(this),
                new TemporalGenerator(this),
                new PrimitiveGenerator(this),
                new EnumGenerator(this),
                new CustomObjectGenerator(this)
        ));
    }

    public void addTypeGenerator(Type type, Supplier<Object> generator) {
        generators.get("types").add(new CustomTypeGenerator(type, generator));
    }

    public void addFieldGenerator(Type objectType, Type fieldType, String fieldName, Supplier<Object> generator) {
        generators.get("fields").add(new CustomFieldGenerator(objectType, fieldType, fieldName, generator));
    }

    public void addPostProcessor(Type type, Consumer<Object> postProcessor) {
        postProcessors.computeIfAbsent(type, t -> new ArrayList<>()).add(postProcessor);
    }

    public void addInstanceCreator(Type type, Supplier<Object> creator) {
        this.instanceCreator.addInstanceCreator(type, creator);
    }

    public Object generateInstance(Type type) {
        return generateInstance(type, defaultSettings());
    }

    public Object generateInstance(Type type, ObjectoSettings settings) {
        final GenerationContext context = GenerationContext.builder().depth(settings.depth()).settings(settings).build();
        return generateInstance(type, context);
    }

    Object generateInstance(Type type, GenerationContext context) {
        if (context.depth() == 0) {
            return null;
        }
        log.debug("Generate {}", context.path());
        final Optional<Type> rawType = getRawType(type, context);
        if (rawType.isEmpty()) {
            log.info("Cannot recognize a raw type `{}` of field `{}` of type `{}`. Please create an @InstanceCreator method to specify how to instantiate this class.", type, context.fieldName(), context.ownerType());
            return null;
        }
        return generate(rawType.get(), context);
    }

    private Optional<Type> getRawType(Type type, GenerationContext context) {
        return Optional.ofNullable(GenericTypeUtils.getRawType(type, context.ownerType()))
                .or(() -> Optional.ofNullable(context.instance()).map(Object::getClass));
    }

    private Object generate(Type type, GenerationContext context) {
        return generators.values().stream().flatMap(Collection::stream)
                .filter(generator -> generator.isSupported(type, context)).findFirst()
                .map(generator -> generator.create(type, context))
                .map(instance -> postProcess(type, instance))
                .orElse(null);
    }

    private Object postProcess(Type actualType, Object instance) {
        Optional.ofNullable(postProcessors.get(actualType)).stream()
                .flatMap(Collection::stream)
                .forEach(postProcessor -> postProcessor.accept(instance));
        return instance;
    }

    Object createInstance(Type type, GenerationContext context) {
        return instanceCreator.createInstance(type, context);
    }

    private class CustomTypeGenerator extends Generator {

        private final Type type;
        private final Supplier<Object> generator;

        public CustomTypeGenerator(Type type, Supplier<Object> generator) {
            super(ObjectoGenerator.this);
            this.type = type;
            this.generator = generator;
        }

        @Override
        public boolean isSupported(Type actualType, GenerationContext context) {
            return type.equals(actualType);
        }

        @Override
        public Object create(Type type, GenerationContext context) {
            return generator.get();
        }

    }

    private class CustomFieldGenerator extends Generator {

        private final Type objectType;
        private final Type fieldType;
        private final String fieldName;
        private final Supplier<Object> generator;

        public CustomFieldGenerator(Type objectType, Type fieldType, String fieldName, Supplier<Object> generator) {
            super(ObjectoGenerator.this);
            this.objectType = objectType;
            this.fieldType = fieldType;
            this.fieldName = fieldName;
            this.generator = generator;
        }

        @Override
        public boolean isSupported(Type actualType, GenerationContext context) {
            return objectType.equals(context.ownerType())
                    && fieldType.equals(actualType)
                    && fieldName.equals(context.fieldName());
        }

        @Override
        public Object create(Type type, GenerationContext context) {
            return generator.get();
        }

    }

}
