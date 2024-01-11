package com.cariochi.objecto.generators;

import com.cariochi.objecto.settings.Range;
import com.cariochi.objecto.settings.Settings;
import com.cariochi.objecto.utils.Random;
import java.lang.reflect.Type;
import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

class NumberGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return Number.class.isAssignableFrom(context.getRawClass());
    }

    @Override
    public Object generate(Context context) {
        final Type type = context.getType();
        final Settings settings = context.getSettings();
        if (type.equals(Integer.class)) {
            return Random.nextInt(settings.integers());
        } else if (type.equals(Double.class)) {
            return Random.nextDouble(settings.doubles());
        } else if (type.equals(Float.class)) {
            return Random.nextFloat(settings.floats());
        } else if (type.equals(Long.class)) {
            return Random.nextLong(settings.longs());
        } else if (type.equals(Short.class)) {
            return (short) Random.nextInt(settings.integers());
        } else if (type.equals(Byte.class)) {
            return (byte) Random.nextInt(settings.bytes());
        } else if (type.equals(BigDecimal.class)) {
            return BigDecimal.valueOf(Random.nextDouble(Range.of(settings.bigDecimals().min(), settings.bigDecimals().max())))
                    .setScale(settings.bigDecimals().scale(), HALF_UP);
        } else {
            return null;
        }
    }

}
