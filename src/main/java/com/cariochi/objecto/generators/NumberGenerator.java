package com.cariochi.objecto.generators;

import com.cariochi.objecto.settings.Range;
import com.cariochi.objecto.settings.Settings;
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
        final Settings settings = context.getSettings();
        if (type.is(Integer.class)) {
            return context.getRandom().nextInt(settings.integers());
        } else if (type.is(Double.class)) {
            return context.getRandom().nextDouble(settings.doubles());
        } else if (type.is(Float.class)) {
            return context.getRandom().nextFloat(settings.floats());
        } else if (type.is(Long.class)) {
            return context.getRandom().nextLong(settings.longs());
        } else if (type.is(Short.class)) {
            return (short) context.getRandom().nextInt(settings.integers());
        } else if (type.is(Byte.class)) {
            return (byte) context.getRandom().nextInt(settings.bytes());
        } else if (type.is(BigDecimal.class)) {
            return BigDecimal.valueOf(context.getRandom().nextDouble(Range.of(settings.bigDecimals().min(), settings.bigDecimals().max())))
                    .setScale(settings.bigDecimals().scale(), HALF_UP);
        } else {
            return null;
        }
    }

}
