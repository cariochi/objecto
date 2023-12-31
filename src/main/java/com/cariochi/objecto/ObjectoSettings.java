package com.cariochi.objecto;

import com.cariochi.objecto.utils.Range;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.experimental.Accessors;

import static com.cariochi.objecto.ObjectoSettings.Strings.defaultStrings;
import static lombok.AccessLevel.PRIVATE;

@Value
@Accessors(fluent = true)
@Builder(access = PRIVATE)
@With
public class ObjectoSettings {

    int depth;
    Range<Long> longs;
    Range<Integer> integers;
    Range<Integer> bytes;
    Range<Double> doubles;
    Range<Float> floats;
    Range<Integer> collections;
    Range<Integer> arrays;
    Range<Integer> maps;
    Strings strings;
    Range<Double> years;

    public static ObjectoSettings defaultSettings() {
        return ObjectoSettings.builder()
                .depth(2)
                .longs(Range.of(1L, 100_000L))
                .integers(Range.of(1, 100_000))
                .bytes(Range.of(65, 91))
                .doubles(Range.of(1D, 100_000D))
                .floats(Range.of(1F, 100_000F))
                .collections(Range.of(2, 5))
                .arrays(Range.of(2, 5))
                .maps(Range.of(2, 5))
                .strings(defaultStrings())
                .years(Range.of(-5.0, 1.0))
                .build();
    }

    @Value
    @Accessors(fluent = true)
    @Builder(access = PRIVATE)
    @With
    public static class Strings {

        Range<Integer> size;
        boolean uppercase;
        Type type;

        public static Strings defaultStrings() {
            return Strings.builder()
                    .type(Strings.Type.ALPHABETIC)
                    .size(Range.of(8, 16))
                    .uppercase(true)
                    .build();
        }

        public enum Type {
            ALPHABETIC, ALPHANUMERIC, ASCII, NUMERIC, GRAPH, PRINT;
        }

    }

}
