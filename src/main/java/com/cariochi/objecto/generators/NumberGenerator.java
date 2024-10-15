package com.cariochi.objecto.generators;

import com.cariochi.objecto.ObjectoRandom;
import com.cariochi.objecto.settings.ObjectoSettings;
import com.cariochi.objecto.settings.Range;
import com.cariochi.reflecto.types.ReflectoType;
import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

class NumberGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return context.getType().is(Number.class);
    }

    @Override
    public Object generate(Context context) {
        final ReflectoType type = context.getType();
        final ObjectoSettings settings = context.getSettings();
        final ObjectoRandom random = context.getRandom();

        if (type.is(Integer.class)) {
            Range<Integer> range = settings.integers();
            return random.nextInt(range.min(), range.max());
        } else if (type.is(Double.class)) {
            Range<Double> range = settings.doubles();
            return random.nextDouble(range.min(), range.max());
        } else if (type.is(Float.class)) {
            Range<Float> range = settings.floats();
            return random.nextFloat(range.min(), range.max());
        } else if (type.is(Long.class)) {
            Range<Long> range = settings.longs();
            return random.nextLong(range.min(), range.max());
        } else if (type.is(Short.class)) {
            Range<Short> range = settings.shorts();
            return (short) random.nextInt(range.min(), range.max());
        } else if (type.is(Byte.class)) {
            Range<Byte> range = settings.bytes();
            return (byte) random.nextInt(range.min(), range.max());
        } else if (type.is(BigDecimal.class)) {
            Range<Double> range = Range.of(settings.bigDecimals().min(), settings.bigDecimals().max());
            return BigDecimal.valueOf(random.nextDouble(range.min(), range.max()))
                    .setScale(settings.bigDecimals().scale(), HALF_UP);
        } else {
            return null;
        }
    }

}
