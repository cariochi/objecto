package com.cariochi.objecto.settings;

import com.cariochi.objecto.WithSettings;
import com.cariochi.objecto.WithSettings.DoublesRange;
import com.cariochi.objecto.WithSettings.FloatsRange;
import com.cariochi.objecto.WithSettings.IntegersRange;
import com.cariochi.objecto.WithSettings.LongsRange;
import com.cariochi.objecto.utils.Range;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SettingsMapper {

    public static Settings map(WithSettings settings) {
        return Settings.builder()
                .depth(settings.depth())
                .typeDepth(settings.typeDepth())
                .longsRange(map(settings.longsRange()))
                .integersRange(map(settings.integersRange()))
                .bytesRange(map(settings.bytesRange()))
                .doublesRange(map(settings.doublesRange()))
                .floatsRange(map(settings.floatsRange()))
                .yearsRange(map(settings.yearsRange()))
                .collectionsSize(map(settings.collectionsSize()))
                .arraysSize(map(settings.arraysSize()))
                .mapsSize(map(settings.mapsSize()))
                .stringsSettings(map(settings.stringSettings()))
                .build();
    }

    private static StringsSettings map(WithSettings.StringSettings settings) {
        return StringsSettings.builder()
                .size(map(settings.size()))
                .uppercase(settings.uppercase())
                .type(settings.type())
                .build();
    }

    private static Range<Long> map(LongsRange range) {
        return Range.of(range.min(), range.max());
    }

    private static Range<Integer> map(IntegersRange range) {
        return Range.of(range.min(), range.max());
    }

    private static Range<Double> map(DoublesRange range) {
        return Range.of(range.min(), range.max());
    }

    private static Range<Float> map(FloatsRange range) {
        return Range.of(range.min(), range.max());
    }

}
