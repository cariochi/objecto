package com.cariochi.objecto.settings;

import com.cariochi.objecto.WithSettings;
import com.cariochi.objecto.WithSettings.DoubleRange;
import com.cariochi.objecto.WithSettings.FloatRange;
import com.cariochi.objecto.WithSettings.IntRange;
import com.cariochi.objecto.WithSettings.LongRange;
import com.cariochi.objecto.settings.Settings.Collections;
import com.cariochi.objecto.settings.Settings.Strings;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SettingsMapper {

    public static Settings map(WithSettings settings) {
        return Settings.builder()
                .maxDepth(settings.maxDepth())
                .maxRecursionDepth(settings.maxRecursionDepth())
                .longs(map(settings.longs()))
                .integers(map(settings.integers()))
                .bytes(map(settings.bytes()))
                .doubles(map(settings.doubles()))
                .floats(map(settings.floats()))
                .years(map(settings.years()))
                .collections(map(settings.collections()))
                .arrays(map(settings.arrays()))
                .maps(map(settings.maps()))
                .strings(map(settings.strings()))
                .build();
    }

    private static Collections map(WithSettings.Collections settings) {
        return Collections.builder()
                .size(map(settings.size()))
                .build();
    }

    private static Strings map(WithSettings.Strings settings) {
        return Strings.builder()
                .size(map(settings.size()))
                .uppercase(settings.uppercase())
                .type(settings.type())
                .fieldNamePrefix(settings.fieldNamePrefix())
                .build();
    }

    private static Range<Long> map(LongRange range) {
        return Range.of(range.min(), range.max());
    }

    private static Range<Integer> map(IntRange range) {
        return Range.of(range.min(), range.max());
    }

    private static Range<Double> map(DoubleRange range) {
        return Range.of(range.min(), range.max());
    }

    private static Range<Float> map(FloatRange range) {
        return Range.of(range.min(), range.max());
    }

}
