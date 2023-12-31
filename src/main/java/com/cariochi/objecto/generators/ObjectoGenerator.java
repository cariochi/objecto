package com.cariochi.objecto.generators;

import com.cariochi.objecto.ObjectoSettings;
import com.cariochi.objecto.creators.ObjectoInstanceCreator;
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

    private final ObjectoInstanceCreator instanceCreator = new ObjectoInstanceCreator(this);
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
        generators.get("types").add(new CustomTypeGenerator(this, type, generator));
    }

    public void addFieldGenerator(Type objectType, Type fieldType, String fieldName, Supplier<Object> generator) {
        generators.get("fields").add(new CustomFieldGenerator(this, objectType, fieldType, fieldName, generator));
    }

    public void addPostProcessor(Type type, Consumer<Object> postProcessor) {
        postProcessors.computeIfAbsent(type, t -> new ArrayList<>()).add(postProcessor);
    }

    public void addCustomInstanceCreator(Type type, Supplier<Object> creator) {
        this.instanceCreator.addCustomCreator(type, creator);
    }

    public Object generateInstance(Type type) {
        return generateInstance(type, defaultSettings());
    }

    public Object generateInstance(Type type, ObjectoSettings settings) {
        return generateInstance(type, new GenerationContext(settings));
    }

    public Object generateInstance(Type type, GenerationContext context) {
        final Optional<Type> rawTypeOptional = getRawType(type, context);
        if (rawTypeOptional.isEmpty()) {
            log.info("Cannot recognize a raw type `{}` of field `{}`. Please create an @InstanceCreator method to specify how to instantiate this class.", type.getTypeName(), context.path());
            return null;
        }
        final Type rawType = rawTypeOptional.get();
        log.trace("Generating `{}` with type `{}`", context.path(), rawType.getTypeName());
        if (context.isLimitReached(rawType)) {
            log.trace("Limit is reached for `{}` ({} recursive instances)", type.getTypeName(), context.settings().depth());
            return null;
        }
        return generate(rawType, context.withType(rawType));
    }

    private Optional<Type> getRawType(Type type, GenerationContext context) {
        return Optional.ofNullable(GenericTypeUtils.getRawType(type, context.ownerType()))
                .or(() -> Optional.ofNullable(context.instance()).map(Object::getClass));
    }

    private Object generate(Type type, GenerationContext context) {
        return generators.values().stream().flatMap(Collection::stream)
                .filter(generator -> generator.isSupported(type, context)).findFirst()
                .map(generator -> generator.generate(type, context))
                .map(instance -> postProcess(type, instance))
                .orElse(null);
    }

    private Object postProcess(Type actualType, Object instance) {
        Optional.ofNullable(postProcessors.get(actualType)).stream()
                .flatMap(Collection::stream)
                .forEach(postProcessor -> postProcessor.accept(instance));
        return instance;
    }

    public Object createInstance(Type type, GenerationContext context) {
        return instanceCreator.createInstance(type, context);
    }

}
