package com.cariochi.objecto.generator;

import com.cariochi.objecto.ObjectoSettings;
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
    public Object create(Type type, Type ownerType, ObjectoSettings settings) {
        if (type.equals(int.class)) {
            return Random.nextInt(settings.integers());
        } else if (type.equals(double.class)) {
            return Random.nextDouble(settings.doubles());
        } else if (type.equals(float.class)) {
            return Random.nextFloat(settings.floats());
        } else if (type.equals(long.class)) {
            return Random.nextLong(settings.longs());
        } else if (type.equals(short.class)) {
            return (short) Random.nextInt(settings.integers());
        } else if (type.equals(byte.class)) {
            return (byte) Random.nextInt(settings.bytes());
        } else if (type.equals(char.class)) {
            return (char) Random.nextInt(settings.bytes());
        } else if (type.equals(boolean.class)) {
            return Random.nextBoolean();
        } else {
            return null;
        }
    }

}
