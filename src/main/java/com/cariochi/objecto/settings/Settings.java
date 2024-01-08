package com.cariochi.objecto.settings;

import com.cariochi.objecto.utils.Range;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(fluent = true)
@Builder(access = AccessLevel.PACKAGE)
public class Settings {

    int depth;
    int typeDepth;
    Range<Long> longsRange;
    Range<Integer> integersRange;
    Range<Integer> bytesRange;
    Range<Double> doublesRange;
    Range<Float> floatsRange;
    Range<Double> yearsRange;
    Range<Integer> collectionsSize;
    Range<Integer> arraysSize;
    Range<Integer> mapsSize;
    StringsSettings stringsSettings;

}
