package com.cariochi.objecto;

import com.cariochi.objecto.generator.ArrayGenerator;
import com.cariochi.objecto.generator.BooleanGenerator;
import com.cariochi.objecto.generator.CollectionGenerator;
import com.cariochi.objecto.generator.CustomObjectGenerator;
import com.cariochi.objecto.generator.EnumGenerator;
import com.cariochi.objecto.generator.ExternalFieldGenerator;
import com.cariochi.objecto.generator.ExternalTypeGenerator;
import com.cariochi.objecto.generator.Generator;
import com.cariochi.objecto.generator.GenericArrayTypeGenerator;
import com.cariochi.objecto.generator.MapGenerator;
import com.cariochi.objecto.generator.NumberGenerator;
import com.cariochi.objecto.generator.PrimitiveGenerator;
import com.cariochi.objecto.generator.StringGenerator;
import com.cariochi.objecto.generator.TemporalGenerator;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class RandomObjectGenerator {

    private final List<ExternalTypeGenerator> typeConstructors = new ArrayList<>();
    private final List<ExternalTypeGenerator> typeGenerators = new ArrayList<>();
    private final List<ExternalFieldGenerator> fieldGenerators = new ArrayList<>();

    private final List<Generator> defaultGenerators = List.of(
            new StringGenerator(this),
            new NumberGenerator(this),
            new BooleanGenerator(this),
            new CollectionGenerator(this),
            new MapGenerator(this),
            new ArrayGenerator(this),
            new TemporalGenerator(this),
            new GenericArrayTypeGenerator(this),
            new PrimitiveGenerator(this),
            new EnumGenerator(this),
            new CustomObjectGenerator(this)
    );

    public void setTypeConstructors(List<ExternalTypeGenerator> typeConstructors) {
        this.typeConstructors.clear();
        this.typeConstructors.addAll(typeConstructors);
    }

    public void setTypeGenerators(List<ExternalTypeGenerator> typeGenerators) {
        this.typeGenerators.clear();
        this.typeGenerators.addAll(typeGenerators);
    }

    public void setFieldGenerators(List<ExternalFieldGenerator> fieldGenerators) {
        this.fieldGenerators.clear();
        this.fieldGenerators.addAll(fieldGenerators);
    }

    public <T> T generateRandomObject(Type type, int depth) {
        return generateFiledValue(null, type, null, depth);
    }

    public <T> T generateFiledValue(Type objectType, Type fieldType, String fieldName, int depth) {
        if (depth == 0) {
            return null;
        }
        return (T) Optional.empty()
                .or(() -> useFieldGenerator(objectType, fieldType, fieldName))
                .or(() -> useTypeGenerator(fieldType))
                .or(() -> useDefaultGenerator(fieldType, depth))
                .orElse(null);
    }

    public <T> T createInstance(Type type) {
        return (T) typeConstructors.stream()
                .filter(generator -> generator.isSupported(type))
                .findFirst()
                .map(ExternalTypeGenerator::create)
                .orElseGet(() -> createInstanceDefault(type));
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

    private <T> Optional<T> useDefaultGenerator(Type type, int depth) {
        return (Optional<T>) defaultGenerators.stream()
                .filter(generator -> generator.isSupported(type))
                .findFirst()
                .map(generator -> generator.create(type, depth));
    }

    private <T> T createInstanceDefault(Type type) {
        try {
            Class<T> aClass = null;
            if (type instanceof Class) {
                aClass = ((Class<T>) type);
            } else if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                aClass = (Class<T>) parameterizedType.getRawType();
            }
            if (aClass == null || aClass.isInterface() || Modifier.isAbstract(aClass.getModifiers())) {
                return null;
            }
            return aClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.info("Cannot create an instance of {}. Please create an @InstanceCreator method to specify how to instantiate this class.",  type);
            return null;
        }
    }

}
