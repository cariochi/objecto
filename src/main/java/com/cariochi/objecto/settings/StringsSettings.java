package com.cariochi.objecto.settings;

import com.cariochi.objecto.utils.Range;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(fluent = true)
@Builder(access = AccessLevel.PACKAGE)
public class StringsSettings {

    Range<Integer> size;
    boolean uppercase;
    StringGenerationType type;

}
