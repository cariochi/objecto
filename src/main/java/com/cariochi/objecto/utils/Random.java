package com.cariochi.objecto.utils;

import com.cariochi.objecto.ObjectoSettings.Strings;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;

@UtilityClass
public class Random {

    private static final UniformRandomProvider RANDOM_PROVIDER = RandomSource.XO_RO_SHI_RO_128_PP.create();

    public static int nextInt(Range<Integer> range) {
        return RANDOM_PROVIDER.nextInt(range.min(), range.max());
    }

    public static long nextLong(Range<Long> range) {
        return RANDOM_PROVIDER.nextLong(range.min(), range.max());
    }

    public static boolean nextBoolean() {
        return RANDOM_PROVIDER.nextBoolean();
    }

    public static float nextFloat(Range<Float> range) {
        return RANDOM_PROVIDER.nextFloat(range.min(), range.max());
    }

    public static double nextDouble(Range<Double> range) {
        return RANDOM_PROVIDER.nextDouble(range.min(), range.max());
    }

    public static String nextString(Strings strings) {
        String string;
        switch (strings.type()) {
            case ALPHABETIC:
                string = RandomStringUtils.randomAlphabetic(strings.size().min(), strings.size().max());
                break;
            case ALPHANUMERIC:
                string = RandomStringUtils.randomAlphanumeric(strings.size().min(), strings.size().max());
                break;
            case NUMERIC:
                string = RandomStringUtils.randomNumeric(strings.size().min(), strings.size().max());
                break;
            case ASCII:
                string = RandomStringUtils.randomAscii(strings.size().min(), strings.size().max());
                break;
            case GRAPH:
                string = RandomStringUtils.randomGraph(strings.size().min(), strings.size().max());
                break;
            case PRINT:
                string = RandomStringUtils.randomPrint(strings.size().min(), strings.size().max());
                break;
            default:
                string = RandomStringUtils.randomAlphabetic(strings.size().min(), strings.size().max());
                break;
        }
        return strings.uppercase() ? string.toUpperCase() : string;
    }

}
