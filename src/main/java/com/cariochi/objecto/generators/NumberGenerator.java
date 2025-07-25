package com.cariochi.objecto.generators;

import com.cariochi.objecto.config.ObjectoConfig;
import com.cariochi.objecto.config.ObjectoConfig.FakerConfig;
import com.cariochi.objecto.config.Range;
import com.cariochi.objecto.random.ObjectoRandom;
import com.cariochi.reflecto.types.ReflectoType;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.apache.commons.lang3.StringUtils.isEmpty;

class NumberGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return context.getType().is(Number.class);
    }

    @Override
    public Object generate(Context context) {
        final ReflectoType type = context.getType();
        final ObjectoConfig config = context.getConfig();
        final ObjectoRandom random = context.getRandom();
        final FakerConfig fakerConfig = config.faker();
        return isEmpty(fakerConfig.expression())
                ? generateNumber(type, config, random)
                : generateDatafaker(type, fakerConfig, random);
    }

    private static Object generateNumber(ReflectoType type, ObjectoConfig config, ObjectoRandom random) {
        if (type.is(Integer.class)) {
            Range<Integer> range = config.integers();
            return random.nextInt(range.from(), range.to());
        } else if (type.is(Double.class)) {
            Range<Double> range = config.doubles();
            return random.nextDouble(range.from(), range.to());
        } else if (type.is(Float.class)) {
            Range<Float> range = config.floats();
            return random.nextFloat(range.from(), range.to());
        } else if (type.is(Long.class)) {
            Range<Long> range = config.longs();
            return random.nextLong(range.from(), range.to());
        } else if (type.is(Short.class)) {
            Range<Short> range = config.shorts();
            return (short) random.nextInt(range.from(), range.to());
        } else if (type.is(Byte.class)) {
            Range<Byte> range = config.bytes();
            return (byte) random.nextInt(range.from(), range.to());
        } else if (type.is(BigDecimal.class)) {
            Range<Double> range = Range.of(config.bigDecimals().from(), config.bigDecimals().to());
            return BigDecimal.valueOf(random.nextDouble(range.from(), range.to()))
                    .setScale(config.bigDecimals().scale(), RoundingMode.DOWN);
        } else {
            return null;
        }
    }

    private static Object generateDatafaker(ReflectoType type, FakerConfig fakerConfig, ObjectoRandom random) {
        final String s = random.strings().faker(fakerConfig.locale()).nextString(fakerConfig.expression());
        if (type.is(Integer.class)) {
            return Integer.valueOf(s);
        } else if (type.is(Double.class)) {
            return Double.valueOf(s);
        } else if (type.is(Float.class)) {
            return Float.valueOf(s);
        } else if (type.is(Long.class)) {
            return Long.valueOf(s);
        } else if (type.is(Short.class)) {
            return Short.valueOf(s);
        } else if (type.is(Byte.class)) {
            return Byte.valueOf(s);
        } else if (type.is(BigDecimal.class)) {
            return new BigDecimal(s);
        } else {
            return null;
        }
    }

}
