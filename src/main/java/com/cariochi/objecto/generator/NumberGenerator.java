package com.cariochi.objecto.generator;

import com.cariochi.objecto.ObjectoSettings;
import com.cariochi.objecto.utils.Random;
import java.lang.reflect.Type;
import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

public class NumberGenerator extends Generator {

    public NumberGenerator(RandomObjectGenerator randomObjectGenerator) {
        super(randomObjectGenerator);
    }

    @Override
    public boolean isSupported(Type type) {
        return type instanceof Class<?> && Number.class.isAssignableFrom((Class<?>) type);
    }

    @Override
    public Object create(Type type, Type ownerType, ObjectoSettings settings) {
        if (type.equals(Integer.class)) {
            return Random.nextInt(settings.integers());
        } else if (type.equals(Double.class)) {
            return Random.nextDouble(settings.doubles());
        } else if (type.equals(Float.class)) {
            return Random.nextFloat(settings.floats());
        } else if (type.equals(Long.class)) {
            return Random.nextLong(settings.longs());
        } else if (type.equals(Short.class)) {
            return (short) Random.nextInt(settings.integers());
        } else if (type.equals(Byte.class)) {
            return (byte) Random.nextInt(settings.bytes());
        } else if (type.equals(BigDecimal.class)) {
            return BigDecimal.valueOf(Random.nextDouble(settings.doubles())).setScale(4, HALF_UP);
        } else {
            return null;
        }
    }

}
