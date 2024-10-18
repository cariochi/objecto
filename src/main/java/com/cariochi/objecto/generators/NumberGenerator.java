package com.cariochi.objecto.generators;

import com.cariochi.objecto.ObjectoRandom;
import com.cariochi.objecto.settings.ObjectoSettings;
import com.cariochi.objecto.settings.Range;
import com.cariochi.reflecto.types.ReflectoType;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
            return random.nextInt(range.from(), range.to());
        } else if (type.is(Double.class)) {
            Range<Double> range = settings.doubles();
            return random.nextDouble(range.from(), range.to());
        } else if (type.is(Float.class)) {
            Range<Float> range = settings.floats();
            return random.nextFloat(range.from(), range.to());
        } else if (type.is(Long.class)) {
            Range<Long> range = settings.longs();
            return random.nextLong(range.from(), range.to());
        } else if (type.is(Short.class)) {
            Range<Short> range = settings.shorts();
            return (short) random.nextInt(range.from(), range.to());
        } else if (type.is(Byte.class)) {
            Range<Byte> range = settings.bytes();
            return (byte) random.nextInt(range.from(), range.to());
        } else if (type.is(BigDecimal.class)) {
            Range<Double> range = Range.of(settings.bigDecimals().from(), settings.bigDecimals().to());
            return BigDecimal.valueOf(random.nextDouble(range.from(), range.to()))
                    .setScale(settings.bigDecimals().scale(), RoundingMode.DOWN);
        } else {
            return null;
        }
    }

}
