package com.cariochi.objecto.settings;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(fluent = true)
@Builder(access = AccessLevel.PACKAGE)
public class Settings {

    int maxDepth;
    int maxRecursionDepth;
    Range<Long> longs;
    Range<Integer> integers;
    Range<Integer> bytes;
    BigDecimals bigDecimals;
    Range<Double> doubles;
    Range<Float> floats;
    Range<Double> years;
    Collections collections;
    Collections arrays;
    Collections maps;
    Strings strings;

    @Value
    @Accessors(fluent = true)
    @Builder(access = AccessLevel.PACKAGE)
    public static class Strings {

        Range<Integer> size;
        boolean uppercase;
        Type type;
        boolean fieldNamePrefix;


        public enum Type {

            ALPHABETIC,
            ALPHANUMERIC,
            ASCII,
            NUMERIC,
            GRAPH,
            PRINT;

        }

    }

    @Value
    @Accessors(fluent = true)
    @Builder(access = AccessLevel.PACKAGE)
    public static class BigDecimals {

        double min;
        double max;
        int scale;

    }

    @Value
    @Accessors(fluent = true)
    @Builder(access = AccessLevel.PACKAGE)
    public static class Collections {

        Range<Integer> size;

    }

}
