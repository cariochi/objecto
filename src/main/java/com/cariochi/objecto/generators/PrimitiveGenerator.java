package com.cariochi.objecto.generators;

import com.cariochi.objecto.config.ObjectoConfig;
import com.cariochi.objecto.config.Range;
import com.cariochi.objecto.random.ObjectoRandom;
import com.cariochi.reflecto.types.ReflectoType;

class PrimitiveGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return context.getType().isPrimitive();
    }

    @Override
    public Object generate(Context context) {
        final ReflectoType type = context.getType();
        final ObjectoConfig config = context.getConfig();
        final ObjectoRandom random = context.getRandom();
        if (type.is(int.class)) {
            Range<Integer> range = config.integers();
            return random.nextInt(range.from(), range.to());
        } else if (type.is(double.class)) {
            Range<Double> range = config.doubles();
            return random.nextDouble(range.from(), range.to());
        } else if (type.is(float.class)) {
            Range<Float> range = config.floats();
            return random.nextFloat(range.from(), range.to());
        } else if (type.is(long.class)) {
            Range<Long> range = config.longs();
            return random.nextLong(range.from(), range.to());
        } else if (type.is(short.class)) {
            Range<Short> range = config.shorts();
            return (short) random.nextInt(range.from(), range.to());
        } else if (type.is(byte.class)) {
            Range<Byte> range = config.bytes();
            return (byte) random.nextInt(range.from(), range.to());
        } else if (type.is(char.class)) {
            Range<Character> range = config.chars();
            return (char) random.nextInt(range.from(), range.to());
        } else if (type.is(boolean.class)) {
            return random.nextBoolean();
        } else {
            return null;
        }
    }

}
