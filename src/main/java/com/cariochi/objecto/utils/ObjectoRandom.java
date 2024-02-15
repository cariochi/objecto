package com.cariochi.objecto.utils;

import com.cariochi.objecto.proxy.HasSeed;
import com.cariochi.objecto.settings.Range;
import com.cariochi.objecto.settings.Settings.Strings;
import java.util.Random;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.JDKRandomWrapper;
import org.apache.commons.rng.simple.RandomSource;

public class ObjectoRandom implements HasSeed {

    @Getter private final java.util.Random random = new Random();
    private final UniformRandomProvider randomProvider = new JDKRandomWrapper(random);
    @Getter private long seed;
    @Getter private boolean customSeed = false;

    public ObjectoRandom() {
        seed = randomSeed();
        random.setSeed(seed);
    }

    public static long randomSeed() {
        return RandomSource.JDK.create().nextLong();
    }

    public int nextInt(Range<Integer> range) {
        return randomProvider.nextInt(range.min(), range.max());
    }

    public long nextLong(Range<Long> range) {
        return randomProvider.nextLong(range.min(), range.max());
    }

    public boolean nextBoolean() {
        return randomProvider.nextBoolean();
    }

    public float nextFloat(Range<Float> range) {
        return randomProvider.nextFloat(range.min(), range.max());
    }

    public double nextDouble(Range<Double> range) {
        return randomProvider.nextDouble(range.min(), range.max());
    }

    public String nextString(Strings stringsSettings) {
        String string;
        final int count = nextInt(stringsSettings.size());
        switch (stringsSettings.type()) {
            case ALPHANUMERIC:
                string = RandomStringUtils.random(count, 0, 0, true, true, null, random);
                break;
            case NUMERIC:
                string = RandomStringUtils.random(count, 0, 0, false, true, null, random);
                break;
            case ASCII:
                string = RandomStringUtils.random(count, 32, 127, false, false, null, random);
                break;
            case GRAPH:
                string = RandomStringUtils.random(count, 33, 126, false, false, null, random);
                break;
            case PRINT:
                string = RandomStringUtils.random(count, 32, 126, false, false, null, random);
                break;
            case ALPHABETIC:
            default:
                string = RandomStringUtils.random(count, 0, 0, true, false, null, random);
                break;
        }
        return stringsSettings.uppercase() ? string.toUpperCase() : string;
    }

    @Override
    public void setSeed(long seed) {
        this.customSeed = true;
        this.seed = seed;
        this.random.setSeed(seed);
    }

}
