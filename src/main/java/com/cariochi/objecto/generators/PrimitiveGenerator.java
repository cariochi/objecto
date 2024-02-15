package com.cariochi.objecto.generators;

import com.cariochi.objecto.settings.Settings;
import com.cariochi.reflecto.types.ReflectoType;

class PrimitiveGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return context.getType().isPrimitive();
    }

    @Override
    public Object generate(Context context) {
        final ReflectoType type = context.getType();
        final Settings settings = context.getSettings();
        if (type.is(int.class)) {
            return context.getRandom().nextInt(settings.integers());
        } else if (type.is(double.class)) {
            return context.getRandom().nextDouble(settings.doubles());
        } else if (type.is(float.class)) {
            return context.getRandom().nextFloat(settings.floats());
        } else if (type.is(long.class)) {
            return context.getRandom().nextLong(settings.longs());
        } else if (type.is(short.class)) {
            return (short) context.getRandom().nextInt(settings.integers());
        } else if (type.is(byte.class)) {
            return (byte) context.getRandom().nextInt(settings.bytes());
        } else if (type.is(char.class)) {
            return (char) context.getRandom().nextInt(settings.bytes());
        } else if (type.is(boolean.class)) {
            return context.getRandom().nextBoolean();
        } else {
            return null;
        }
    }

}
