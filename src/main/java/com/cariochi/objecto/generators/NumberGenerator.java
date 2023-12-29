package com.cariochi.objecto.generators;

import com.cariochi.objecto.ObjectoSettings;
import com.cariochi.objecto.utils.Random;
import java.lang.reflect.Type;
import java.math.BigDecimal;

import static com.cariochi.objecto.utils.GenericTypeUtils.getRawClass;
import static java.math.RoundingMode.HALF_UP;

class NumberGenerator extends Generator {

    public NumberGenerator(ObjectoGenerator objectoGenerator) {
        super(objectoGenerator);
    }

    @Override
    public boolean isSupported(Type type, GenerationContext context) {
        final Class<?> rawType = getRawClass(type, context.ownerType());
        return rawType != null && Number.class.isAssignableFrom(rawType);
    }

    @Override
    public Object generate(Type type, GenerationContext context) {
        final ObjectoSettings settings = context.settings();
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
            return BigDecimal.valueOf(Random.nextDouble(settings.doubles())).setScale(4, HALF_UP);
        } else {
            return null;
        }
    }

}
