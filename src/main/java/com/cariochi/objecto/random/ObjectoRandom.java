package com.cariochi.objecto.random;

import com.cariochi.objecto.proxy.HasSeed;
import lombok.Getter;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.JDKRandomBridge;
import org.apache.commons.rng.simple.RandomSource;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

/**
 * Objecto-managed random source passed to custom generators and post-processors.
 * <p>
 * Use this type in custom generator method signatures when generated values should follow Objecto's
 * seed behavior.
 *
 * <pre>{@code
 * @FieldGenerator(type = Issue.class, field = "key")
 * private String key(ObjectoRandom random) {
 *     return "ID-" + random.nextInt(1000, 10_000);
 * }
 * }</pre>
 */
public class ObjectoRandom implements HasSeed {

    private final static RandomSource RANDOM_SOURCE = RandomSource.SPLIT_MIX_64;

    private UniformRandomProvider randomProvider;
    @Getter private Random random;
    @Getter private long seed;
    @Getter private boolean customSeed = false;


    /**
     * Generates a new random seed.
     *
     * @return random seed value
     */
    public static long randomSeed() {
        return RandomSource.JDK.create().nextLong();
    }

    /**
     * Creates a random source initialized with a random seed.
     */
    public ObjectoRandom() {
        seed = randomSeed();
        init(seed);
    }

    /**
     * Reinitializes this random source with the supplied seed and marks it as custom-seeded.
     *
     * @param seed seed to apply
     */
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

    /**
     * @return random {@code int}
     */
    public int nextInt() {
        return randomProvider.nextInt();
    }

    /**
     * Returns a random {@code int} in the range {@code [from, to)}.
     *
     * @param from inclusive lower bound
     * @param to   exclusive upper bound
     * @return random {@code int}
     */
    public int nextInt(int from, int to) {
        return randomProvider.nextInt(from, to);
    }

    /**
     * @return random {@code long}
     */
    public long nextLong() {
        return randomProvider.nextLong();
    }

    /**
     * Returns a random {@code long} in the range {@code [from, to)}.
     *
     * @param from inclusive lower bound
     * @param to exclusive upper bound
     * @return random {@code long}
     */
    public long nextLong(long from, long to) {
        return randomProvider.nextLong(from, to);
    }

    /**
     * @return random {@code float}
     */
    public float nextFloat() {
        return randomProvider.nextFloat();
    }

    /**
     * Returns a random {@code float} in the range {@code [from, to)}.
     *
     * @param from inclusive lower bound
     * @param to exclusive upper bound
     * @return random {@code float}
     */
    public float nextFloat(float from, float to) {
        return randomProvider.nextFloat(from, to);
    }

    /**
     * @return random {@code double}
     */
    public double nextDouble() {
        return randomProvider.nextDouble();
    }

    /**
     * Returns a random {@code double} in the range {@code [from, to)}.
     *
     * @param from inclusive lower bound
     * @param to exclusive upper bound
     * @return random {@code double}
     */
    public double nextDouble(double from, double to) {
        return randomProvider.nextDouble(from, to);
    }

    /**
     * @return random boolean
     */
    public boolean nextBoolean() {
        return randomProvider.nextBoolean();
    }

    /**
     * @return random UUID generated from this random source
     */
    public UUID nextUUID() {
        return new UUID(randomProvider.nextLong(), randomProvider.nextLong());
    }

    private Instant nextInstant() {
        return Instant.ofEpochSecond(nextLong());
    }

    /**
     * Returns a random instant in the range {@code [from, to)}.
     *
     * @param from inclusive lower bound
     * @param to exclusive upper bound
     * @return random instant
     */
    public Instant nextInstant(Instant from, Instant to) {
        return Instant.ofEpochSecond(nextLong(from.getEpochSecond(), to.getEpochSecond()));
    }

    /**
     * Creates a string-oriented random helper backed by the same random source.
     *
     * @return string random helper
     */
    public Strings strings() {
        return new Strings(randomProvider, random);
    }

}
