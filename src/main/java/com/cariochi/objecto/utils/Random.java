package com.cariochi.objecto.utils;

import com.cariochi.objecto.settings.StringsSettings;
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

    public static String nextString(StringsSettings stringsSettings) {
        String string;
        switch (stringsSettings.type()) {
            case ALPHANUMERIC:
                string = RandomStringUtils.randomAlphanumeric(stringsSettings.size().min(), stringsSettings.size().max());
                break;
            case NUMERIC:
                string = RandomStringUtils.randomNumeric(stringsSettings.size().min(), stringsSettings.size().max());
                break;
            case ASCII:
                string = RandomStringUtils.randomAscii(stringsSettings.size().min(), stringsSettings.size().max());
                break;
            case GRAPH:
                string = RandomStringUtils.randomGraph(stringsSettings.size().min(), stringsSettings.size().max());
                break;
            case PRINT:
                string = RandomStringUtils.randomPrint(stringsSettings.size().min(), stringsSettings.size().max());
                break;
            case ALPHABETIC:
            default:
                string = RandomStringUtils.randomAlphabetic(stringsSettings.size().min(), stringsSettings.size().max());
                break;
        }
        return stringsSettings.uppercase() ? string.toUpperCase() : string;
    }

}
