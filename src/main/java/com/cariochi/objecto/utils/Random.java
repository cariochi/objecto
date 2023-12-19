package com.cariochi.objecto.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;

@UtilityClass
public class Random {

    private static final UniformRandomProvider RANDOM_PROVIDER = RandomSource.XO_RO_SHI_RO_128_PP.create();

    public static int nextInt(int origin, int bound) {
        return RANDOM_PROVIDER.nextInt(origin, bound);
    }

    public static long nextLong(long origin, long bound) {
        return RANDOM_PROVIDER.nextLong(origin, bound);
    }

    public static boolean nextBoolean() {
        return RANDOM_PROVIDER.nextBoolean();
    }

    public static float nextFloat(float origin, float bound) {
        return RANDOM_PROVIDER.nextFloat(origin, bound);
    }

    public static double nextDouble(double origin, double bound) {
        return RANDOM_PROVIDER.nextDouble(origin, bound);
    }

    public static String nextString(int minLength, int maxLength) {
        return RandomStringUtils.randomAlphabetic(minLength, maxLength);
    }

}
