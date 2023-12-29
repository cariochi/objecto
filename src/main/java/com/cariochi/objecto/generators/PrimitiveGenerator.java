package com.cariochi.objecto.generators;

import com.cariochi.objecto.ObjectoSettings;
import com.cariochi.objecto.utils.Random;
import java.lang.reflect.Type;

import static com.cariochi.objecto.utils.GenericTypeUtils.getRawClass;

class PrimitiveGenerator extends Generator {

    public PrimitiveGenerator(ObjectoGenerator objectoGenerator) {
        super(objectoGenerator);
    }

    @Override
    public boolean isSupported(Type type, GenerationContext context) {
        final Class<?> rawType = getRawClass(type, context.ownerType());
        return rawType != null && rawType.isPrimitive();
    }

    @Override
    public Object generate(Type type, GenerationContext context) {
        final ObjectoSettings settings = context.settings();
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
