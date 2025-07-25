package com.cariochi.objecto.random;

import com.cariochi.objecto.proxy.HasSeed;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;
import lombok.Getter;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.JDKRandomBridge;
import org.apache.commons.rng.simple.RandomSource;

public class ObjectoRandom implements HasSeed {

    private final static RandomSource RANDOM_SOURCE = RandomSource.SPLIT_MIX_64;

    private UniformRandomProvider randomProvider;
    @Getter private Random random;
    @Getter private long seed;
    @Getter private boolean customSeed = false;


    public static long randomSeed() {
        return RandomSource.JDK.create().nextLong();
    }

    public ObjectoRandom() {
        seed = randomSeed();
        init(seed);
    }

    @Override
    public void setSeed(long seed) {
        this.customSeed = true;
        this.seed = seed;
        init(seed);
    }

    private void init(long seed) {
        this.randomProvider = RANDOM_SOURCE.create(seed);
        this.random = new JDKRandomBridge(RANDOM_SOURCE, seed);
    }

    public int nextInt() {
        return randomProvider.nextInt();
    }

    public int nextInt(int from, int to) {
        return randomProvider.nextInt(from, to);
    }

    public long nextLong() {
        return randomProvider.nextLong();
    }

    public long nextLong(long from, long to) {
        return randomProvider.nextLong(from, to);
    }

    public float nextFloat() {
        return randomProvider.nextFloat();
    }

    public float nextFloat(float from, float to) {
        return randomProvider.nextFloat(from, to);
    }

    public double nextDouble() {
        return randomProvider.nextDouble();
    }

    public double nextDouble(double from, double to) {
        return randomProvider.nextDouble(from, to);
    }

    public boolean nextBoolean() {
        return randomProvider.nextBoolean();
    }

    public UUID nextUUID() {
        return new UUID(randomProvider.nextLong(), randomProvider.nextLong());
    }

    private Instant nextInstant() {
        return Instant.ofEpochSecond(nextLong());
    }

    public Instant nextInstant(Instant from, Instant to) {
        return Instant.ofEpochSecond(nextLong(from.getEpochSecond(), to.getEpochSecond()));
    }

    public StringRandom strings() {
        return new StringRandom(randomProvider, random);
    }

}
