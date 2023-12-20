package com.cariochi.objecto;

import com.cariochi.objecto.utils.Range;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.With;
import lombok.experimental.Accessors;

import static lombok.AccessLevel.PRIVATE;

@Value
@Accessors(fluent = true)
@With
@Builder(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class ObjectoSettings {

    @Builder.Default
    int depth = 3;

    @Builder.Default
    Range<Long> longs = Range.of(1L, 100_000L);

    @Builder.Default
    Range<Integer> integers = Range.of(1, 100_000);

    @Builder.Default
    Range<Integer> bytes = Range.of(65, 91);

    @Builder.Default
    Range<Double> doubles = Range.of(1D, 100_000D);

    @Builder.Default
    Range<Float> floats = Range.of(1F, 100_000F);

    @Builder.Default
    Range<Integer> collections = Range.of(2, 5);

    @Builder.Default
    Range<Integer> arrays = Range.of(2, 5);

    @Builder.Default
    Range<Integer> maps = Range.of(2, 5);

    @Builder.Default
    Strings strings = Strings.strings();

    public static ObjectoSettings settings() {
        return new ObjectoSettings();
    }

    @Value
    @Accessors(fluent = true)
    @With
    @Builder(access = PRIVATE)
    @NoArgsConstructor(access = PRIVATE)
    @AllArgsConstructor(access = PRIVATE)
    public static class Strings {

        @Builder.Default
        Range<Integer> size = Range.of(8, 16);

        @Builder.Default
        boolean uppercase = true;

        @Builder.Default
        Type type = Type.ALPHABETIC;

        public static Strings strings() {
            return new Strings();
        }

        public enum Type {
            ALPHABETIC,
            ALPHANUMERIC,
            ASCII,
            NUMERIC,
            GRAPH,
            PRINT
        }

    }

}
