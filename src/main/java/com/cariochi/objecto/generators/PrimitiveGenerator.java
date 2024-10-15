package com.cariochi.objecto.generators;

import com.cariochi.objecto.ObjectoRandom;
import com.cariochi.objecto.settings.ObjectoSettings;
import com.cariochi.objecto.settings.Range;
import com.cariochi.reflecto.types.ReflectoType;

class PrimitiveGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return context.getType().isPrimitive();
    }

    @Override
    public Object generate(Context context) {
        final ReflectoType type = context.getType();
        final ObjectoSettings settings = context.getSettings();
        final ObjectoRandom random = context.getRandom();
        if (type.is(int.class)) {
            Range<Integer> range = settings.integers();
            return random.nextInt(range.min(), range.max());
        } else if (type.is(double.class)) {
            Range<Double> range = settings.doubles();
            return random.nextDouble(range.min(), range.max());
        } else if (type.is(float.class)) {
            Range<Float> range = settings.floats();
            return random.nextFloat(range.min(), range.max());
        } else if (type.is(long.class)) {
            Range<Long> range = settings.longs();
            return random.nextLong(range.min(), range.max());
        } else if (type.is(short.class)) {
            Range<Short> range = settings.shorts();
            return (short) random.nextInt(range.min(), range.max());
        } else if (type.is(byte.class)) {
            Range<Byte> range = settings.bytes();
            return (byte) random.nextInt(range.min(), range.max());
        } else if (type.is(char.class)) {
            Range<Character> range = settings.chars();
            return (char) random.nextInt(range.min(), range.max());
        } else if (type.is(boolean.class)) {
            return random.nextBoolean();
        } else {
            return null;
        }
    }

}
