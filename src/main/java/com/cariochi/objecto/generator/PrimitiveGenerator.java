package com.cariochi.objecto.generator;

import com.cariochi.objecto.RandomObjectGenerator;
import com.cariochi.objecto.utils.Random;
import java.lang.reflect.Type;

public class PrimitiveGenerator extends Generator {

    public PrimitiveGenerator(RandomObjectGenerator randomObjectGenerator) {
        super(randomObjectGenerator);
    }

    @Override
    public boolean isSupported(Type type) {
        return type instanceof Class && ((Class<?>) type).isPrimitive();
    }

    @Override
    public Object create(Type type, int depth) {
        if (type.equals(int.class)) {
            return Random.nextInt(1, 10000);
        } else if (type.equals(double.class)) {
            return Random.nextDouble(1, 10000);
        } else if (type.equals(float.class)) {
            return Random.nextFloat(1, 10000);
        } else if (type.equals(long.class)) {
            return Random.nextLong(1, 10000);
        } else if (type.equals(short.class)) {
            return (short) Random.nextInt(1, 10000);
        } else if (type.equals(byte.class)) {
            return (byte) Random.nextInt(65, 91);
        } else if (type.equals(char.class)) {
            return (char) Random.nextInt(65, 91);
        } else if (type.equals(boolean.class)) {
            return Random.nextBoolean();
        } else {
            return null;
        }
    }

}
