package com.cariochi.objecto.settings;

import java.time.Instant;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.experimental.Accessors;

import static java.time.temporal.ChronoUnit.DAYS;

@Value
@With
@Accessors(fluent = true)
@Builder(access = AccessLevel.PACKAGE)
public class ObjectoSettings {

    public static final ObjectoSettings DEFAULT_SETTINGS = ObjectoSettings.builder()
            .maxDepth(4)
            .maxRecursionDepth(2)
            .longs(Range.of(1L, 100_000L))
            .integers(Range.of(1, 100_000))
            .shorts(Range.of((short) 1, Short.MAX_VALUE))
            .bytes(Range.of((byte) 65, (byte) 91))
            .chars(Range.of('a', 'z'))
            .bigDecimals(BigDecimals.builder().min(1).max(100_000).scale(4).build())
            .doubles(Range.of(1.0, 100_000.0))
            .floats(Range.of(1F, 100_000F))
            .dates(Range.of(Instant.now().minus(365, DAYS), Instant.now().plus(365, DAYS)))
            .collections(Collections.builder().size(Range.of(2, 5)).build())
            .arrays(Collections.builder().size(Range.of(2, 5)).build())
            .maps(Collections.builder().size(Range.of(2, 5)).build())
            .strings(Strings.builder().letters(true).numbers(false).uppercase(true).fieldNamePrefix(false).size(Range.of(8, 16)).build())
            .datafaker(Datafaker.builder().locale("en").build())
            .nullable(false)
            .setNull(false)
            .setValue(null)
            .build();

    int maxDepth;
    int maxRecursionDepth;
    Range<Long> longs;
    Range<Integer> integers;
    Range<Short> shorts;
    Range<Byte> bytes;
    Range<Character> chars;
    BigDecimals bigDecimals;
    Range<Double> doubles;
    Range<Float> floats;
    Range<Instant> dates;
    Collections collections;
    Collections arrays;
    Collections maps;
    Strings strings;
    Datafaker datafaker;
    boolean nullable;
    boolean setNull;
    String setValue;

    @Value
    @With
    @Accessors(fluent = true)
    @Builder
    public static class Strings {

        @Builder.Default Range<Integer> size = Range.of(8, 16);
        @Builder.Default boolean letters = true;
        @Builder.Default boolean numbers = false;
        @Builder.Default boolean uppercase = false;
        @Builder.Default boolean fieldNamePrefix = false;

    }

    @Value
    @With
    @Accessors(fluent = true)
    @Builder(access = AccessLevel.PACKAGE)
    public static class BigDecimals {

        double min;
        double max;
        int scale;

    }

    @Value
    @With
    @Accessors(fluent = true)
    @Builder(access = AccessLevel.PACKAGE)
    public static class Collections {

        Range<Integer> size;

    }

    @Value
    @With
    @Accessors(fluent = true)
    @Builder
    public static class Datafaker {

        @Builder.Default String locale = "en";
        String method;

    }

}
