package com.cariochi.objecto.generator;

import com.cariochi.objecto.ObjectoSettings;
import com.cariochi.objecto.utils.FieldKey;
import com.cariochi.objecto.utils.GenericTypeUtils;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;

import static com.cariochi.objecto.ObjectoSettings.defaultSettings;

@Slf4j
public class ObjectoGenerator {

    private final InstanceCreator instanceCreator = new InstanceCreator(this);
    private final Map<Type, Supplier<Object>> typeGenerators = new HashMap<>();
    private final Map<FieldKey, Supplier<Object>> fieldGenerators = new HashMap<>();

    private final List<Generator> defaultGenerators = List.of(
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
    );

    public void addTypeGenerator(Type type, Supplier<Object> generator) {
        typeGenerators.put(type, generator);
    }

    public void addFieldGenerator(FieldKey key, Supplier<Object> generator) {
        fieldGenerators.put(key, generator);
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
        }
        return rawType
                .flatMap(actualType -> Optional.empty()
                        .or(() -> useFieldGenerator(actualType, context))
                        .or(() -> useTypeGenerator(actualType))
                        .or(() -> useDefaultGenerator(actualType, context))
                )
                .orElse(null);
    }

    private Optional<Type> getRawType(Type type, GenerationContext context) {
        return Optional.ofNullable(GenericTypeUtils.getRawType(type, context.ownerType()))
                .or(() -> Optional.ofNullable(context.instance()).map(Object::getClass));
    }

    private Optional<?> useTypeGenerator(Type fieldType) {
        return Optional.ofNullable(typeGenerators.get(fieldType)).map(Supplier::get);
    }

    private Optional<?> useFieldGenerator(Type fieldType, GenerationContext context) {
        final FieldKey key = FieldKey.builder().objectType(context.ownerType()).fieldType(fieldType).fieldName(context.fieldName()).build();
        return Optional.ofNullable(fieldGenerators.get(key)).map(Supplier::get);
    }

    private Optional<?> useDefaultGenerator(Type type, GenerationContext context) {
        return defaultGenerators.stream()
                .filter(generator -> generator.isSupported(type, context))
                .findFirst()
                .map(generator -> generator.create(type, context));
    }

    Object createInstance(Type type, GenerationContext context) {
        return instanceCreator.createInstance(type, context);
    }

}
