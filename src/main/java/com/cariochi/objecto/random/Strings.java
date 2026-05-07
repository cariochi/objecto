package com.cariochi.objecto.random;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.text.RandomStringGenerator;
import org.apache.commons.text.RandomStringGenerator.Builder;

import java.util.Locale;
import java.util.Random;

/**
 * Helper for generating random strings and Datafaker expression values.
 * <p>
 * Instances are created from {@link ObjectoRandom#strings()} and share the same Objecto-managed random
 * source.
 *
 * <pre>{@code
 * String code = random.strings()
 *         .selectFrom("ABCDEF0123456789")
 *         .nextString(8);
 *
 * String company = random.strings()
 *         .datafaker()
 *         .nextString(com.cariochi.objecto.Datafaker.Base.Company.NAME);
 * }</pre>
 */
@RequiredArgsConstructor
public class Strings {

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

    /**
     * Restricts generated strings to the supplied characters.
     *
     * @param chars characters to choose from
     * @return this helper for chaining
     */
    public Strings selectFrom(String chars) {
        this.chars = chars;
        return this;
    }

    /**
     * Restricts generated strings to the supplied code point range.
     *
     * @param minimumCodePoint minimum code point
     * @param maximumCodePoint maximum code point
     * @return this helper for chaining
     */
    public Strings withinRange(char minimumCodePoint, char maximumCodePoint) {
        this.minimumCodePoint = minimumCodePoint;
        this.maximumCodePoint = maximumCodePoint;
        return this;
    }

    /**
     * Generates a string with the exact length.
     *
     * @param length generated string length
     * @return generated string
     */
    public String nextString(int length) {
        return getGenerator().generate(length);
    }

    /**
     * Generates a string with a length in the supplied range.
     *
     * @param minLengthInclusive inclusive lower bound
     * @param maxLengthInclusive inclusive upper bound
     * @return generated string
     */
    public String nextString(int minLengthInclusive, int maxLengthInclusive) {
        return getGenerator().generate(minLengthInclusive, maxLengthInclusive);
    }

    /**
     * Creates a Datafaker helper with the default {@code en} locale.
     *
     * @return Datafaker helper
     */
    public DatafakerRandom datafaker() {
        return datafaker("en");
    }

    /**
     * Creates a Datafaker helper with the supplied locale.
     *
     * @param locale locale tag accepted by Datafaker
     * @return Datafaker helper
     */
    public DatafakerRandom datafaker(String locale) {
        return new DatafakerRandom(locale, random);
    }


    /**
     * Datafaker expression helper backed by Objecto-managed randomness.
     */
    public static class DatafakerRandom {

        private final Faker faker;

        /**
         * Creates a helper for the supplied locale and random source.
         *
         * @param locale locale tag accepted by Datafaker
         * @param random random source used by Datafaker
         */
        public DatafakerRandom(String locale, Random random) {
            this.faker = new Faker(new Locale(locale), random);
        }

        /**
         * Evaluates a Datafaker expression.
         *
         * @param expression Datafaker expression, for example {@code "#{company.name}"}
         * @return generated string value
         */
        public String nextString(String expression) {
            return faker.expression(expression);
        }

    }
}
