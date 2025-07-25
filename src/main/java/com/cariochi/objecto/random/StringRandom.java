package com.cariochi.objecto.random;

import java.util.Locale;
import java.util.Random;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.text.RandomStringGenerator;
import org.apache.commons.text.RandomStringGenerator.Builder;

@RequiredArgsConstructor
public class StringRandom {

    private final UniformRandomProvider randomProvider;
    private final Random random;
    private String chars = "";
    private char minimumCodePoint = 0;
    private char maximumCodePoint = 0;

    @Getter(lazy = true)
    private final RandomStringGenerator generator = getBuild();

    private RandomStringGenerator getBuild() {
        final Builder builder = new Builder().usingRandom(randomProvider::nextInt);
        if (!chars.isEmpty()) {
            builder.selectFrom(chars.toCharArray());
        } else if (minimumCodePoint != 0 || maximumCodePoint != 0) {
            builder.withinRange(minimumCodePoint, maximumCodePoint);
        }
        return builder.get();
    }

    public StringRandom selectFrom(String chars) {
        this.chars = chars;
        return this;
    }

    public StringRandom withinRange(char minimumCodePoint, char maximumCodePoint) {
        this.minimumCodePoint = minimumCodePoint;
        this.maximumCodePoint = maximumCodePoint;
        return this;
    }

    public String nextString(int length) {
        return getGenerator().generate(length);
    }

    public String nextString(int minLengthInclusive, int maxLengthInclusive) {
        return getGenerator().generate(minLengthInclusive, maxLengthInclusive);
    }

    public FakerRandom faker() {
        return faker("en");
    }

    public FakerRandom faker(String locale) {
        return new FakerRandom(locale, random);
    }


    public static class FakerRandom {

        private final Faker faker;

        public FakerRandom(String locale, Random random) {
            this.faker = new Faker(new Locale(locale), random);
        }

        public String nextString(String expression) {
            return faker.expression(expression);
        }

    }
}
