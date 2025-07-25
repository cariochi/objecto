package com.cariochi.objecto.config;

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
public class ObjectoConfig {

    public static final ObjectoConfig DEFAULT_SETTINGS = ObjectoConfig.builder()
            .maxDepth(4)
            .maxRecursionDepth(2)
            .longs(Range.of(1L, 100_000L))
            .integers(Range.of(1, Integer.MAX_VALUE))
            .shorts(Range.of((short) 1, Short.MAX_VALUE))
            .bytes(Range.of((byte) 1, Byte.MAX_VALUE))
            .chars(Range.of('a', 'z'))
            .bigDecimals(BigDecimals.builder().from(1).to(100_000).scale(4).build())
            .doubles(Range.of(1.0, 100_000.0))
            .floats(Range.of(1F, 100_000F))
            .dates(Range.of(Instant.now().minus(365, DAYS), Instant.now().plus(365, DAYS)))
            .collections(Collections.builder()
                    .size(Size.builder().value(null).range(Range.of(2, 5)).build())
                    .build())
            .arrays(Collections.builder()
                    .size(Size.builder().value(null).range(Range.of(2, 5)).build())
                    .build())
            .maps(Collections.builder()
                    .size(Size.builder().value(null).range(Range.of(2, 5)).build())
                    .build())
            .strings(Strings.builder()
                    .from('A')
                    .to('Z')
                    .fieldNamePrefix(false)
                    .length(Size.builder().value(null).range(Range.of(8, 17)).build())
                    .build())
            .faker(FakerConfig.builder().locale("en").build())
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
    FakerConfig faker;
    boolean nullable;
    boolean setNull;
    String setValue;

    @Value
    @With
    @Accessors(fluent = true)
    @Builder
    public static class Strings {

        @Builder.Default Size length = Size.builder().value(null).range(Range.of(8, 16)).build();
        @Builder.Default String chars = "";
        @Builder.Default char from = 0;
        @Builder.Default char to = 0;
        @Builder.Default boolean fieldNamePrefix = false;

    }

    @Value
    @With
    @Accessors(fluent = true)
    @Builder(access = AccessLevel.PACKAGE)
    public static class BigDecimals {

        double from;
        double to;
        int scale;

    }

    @Value
    @With
    @Accessors(fluent = true)
    @Builder(access = AccessLevel.PACKAGE)
    public static class Collections {

        Size size;

    }

    @Value
    @With
    @Accessors(fluent = true)
    @Builder
    public static class FakerConfig {

        @Builder.Default String locale = "en";
        String expression;

    }

}
