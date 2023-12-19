package com.cariochi.objecto.generator;

import com.cariochi.objecto.RandomObjectGenerator;
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
    public Object create(Type type, int depth) {
        if (type.equals(Integer.class)) {
            return Random.nextInt(1, 10000);
        } else if (type.equals(Double.class)) {
            return Random.nextDouble(1, 10000);
        } else if (type.equals(Float.class)) {
            return Random.nextFloat(1, 10000);
        } else if (type.equals(Long.class)) {
            return Random.nextLong(1, 10000);
        } else if (type.equals(Short.class)) {
            return (short) Random.nextInt(1, 10000);
        } else if (type.equals(Byte.class)) {
            return (byte) Random.nextInt(65, 91);
        } else if (type.equals(BigDecimal.class)) {
            return BigDecimal.valueOf(Random.nextDouble(1, 10000)).setScale(4, HALF_UP);
        } else {
            return null;
        }
    }

}
