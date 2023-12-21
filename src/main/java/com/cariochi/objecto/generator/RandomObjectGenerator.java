package com.cariochi.objecto.generator;

import com.cariochi.objecto.ObjectoSettings;
import com.cariochi.objecto.utils.GenericTypeUtils;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RandomObjectGenerator {

    private final ObjectCreator objectCreator;
    private final List<ExternalTypeGenerator> typeGenerators;
    private final List<ExternalFieldGenerator> fieldGenerators;

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

    public RandomObjectGenerator(List<ExternalTypeGenerator> instanceCreators,
                                 List<ExternalTypeGenerator> typeGenerators,
                                 List<ExternalFieldGenerator> fieldGenerators) {
        this.objectCreator = new ObjectCreator(instanceCreators, this);
        this.typeGenerators = typeGenerators;
        this.fieldGenerators = fieldGenerators;
    }

    public <T> T generateRandomObject(Type type, ObjectoSettings settings) {
        return generateRandomObject(type, null, null, settings);
    }

    <T> T generateRandomObject(Type type, Type ownerType, String fieldName, ObjectoSettings settings) {
        if (settings.depth() == 0) {
            return null;
        }
        final Type actualType = GenericTypeUtils.getActualType(type, ownerType);
        if (actualType == null) {
            return null;
        }
        return (T) Optional.empty()
                .or(() -> useFieldGenerator(ownerType, actualType, fieldName))
                .or(() -> useTypeGenerator(actualType))
                .or(() -> useDefaultGenerator(actualType, ownerType, settings))
                .orElse(null);
    }

    private <T> Optional<T> useTypeGenerator(Type fieldType) {
        return (Optional<T>) typeGenerators.stream()
                .filter(generator -> generator.isSupported(fieldType))
                .findFirst()
                .map(ExternalTypeGenerator::create);
    }

    private <T> Optional<T> useFieldGenerator(Type objectType, Type fieldType, String fieldName) {
        return (Optional<T>) fieldGenerators.stream()
                .filter(generator -> generator.isSupported(objectType, fieldType, fieldName))
                .findFirst()
                .map(ExternalFieldGenerator::create);
    }

    private <T> Optional<T> useDefaultGenerator(Type type, Type ownerType, ObjectoSettings settings) {
        return (Optional<T>) defaultGenerators.stream()
                .filter(generator -> generator.isSupported(type))
                .findFirst()
                .map(generator -> generator.create(type, ownerType, settings));
    }

    public <T> T createInstance(Type type, ObjectoSettings settings) {
        return objectCreator.createInstance(type, settings);
    }

}
