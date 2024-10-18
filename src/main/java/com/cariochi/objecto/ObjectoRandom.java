package com.cariochi.objecto;

import com.cariochi.objecto.proxy.HasSeed;
import com.cariochi.reflecto.invocations.model.Reflection;
import java.time.Instant;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import lombok.Getter;
import net.datafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.JDKRandomWrapper;
import org.apache.commons.rng.simple.RandomSource;

import static com.cariochi.reflecto.Reflecto.reflect;

public class ObjectoRandom implements HasSeed {

    @Getter private final java.util.Random random = new Random();
    private final UniformRandomProvider randomProvider = new JDKRandomWrapper(random);
    @Getter private long seed;
    @Getter private boolean customSeed = false;

    private final Map<String, Reflection> fakers = new HashMap<>();

    public ObjectoRandom() {
        seed = randomSeed();
        random.setSeed(seed);
    }

    public static long randomSeed() {
        return RandomSource.JDK.create().nextLong();
    }

    @Override
    public void setSeed(long seed) {
        this.customSeed = true;
        this.seed = seed;
        this.random.setSeed(seed);
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

    public String nextString() {
        return nextString(8, true, false, false);
    }

    public String nextString(int size) {
        return nextString(size, true, false, false);
    }

    public String nextString(int count, boolean letters, boolean numbers, boolean uppercase) {
        String string = RandomStringUtils.random(count, 0, 0, letters, numbers, null, random);
        return uppercase ? string.toUpperCase() : string;
    }

    public String nextDatafakerString(String method) {
        return nextDatafakerString("en", method);
    }

    public String nextDatafakerString(String locale, String method) {
        if (!method.contains("(")) {
            method = method.replace(".", "().") + "()";
        }
        final Reflection faker = getFaker(locale);
        return Objects.toString(faker.perform(method));
    }

    private Instant nextInstant() {
        return Instant.ofEpochSecond(nextLong());
    }

    public Instant nextInstant(Instant from, Instant to) {
        long minSeconds = from.getEpochSecond();
        long maxSeconds = to.getEpochSecond();
        return Instant.ofEpochSecond(nextLong(minSeconds, maxSeconds));
    }

    public Instant nextDatafakerInstant(String method) {
        return nextDatafakerInstant("en", method);
    }

    public Instant nextDatafakerInstant(String locale, String method) {
        if (!method.contains("(")) {
            method = method.replace(".", "().") + "()";
        }
        final Reflection faker = getFaker(locale);
        return faker.perform(method);
    }

    private Reflection getFaker(String locale) {
        return fakers.computeIfAbsent(locale, key -> reflect(new Faker(new Locale(key), random)));
    }
}
