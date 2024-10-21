package com.cariochi.objecto.settings.functions.settings;

import com.cariochi.objecto.Settings;
import com.cariochi.objecto.settings.ObjectoSettings;
import com.cariochi.objecto.settings.Range;
import java.util.function.UnaryOperator;

public class BytesRange implements SettingsFunction<Settings.Bytes.Range> {

    @Override
    public UnaryOperator<ObjectoSettings> apply(Settings.Bytes.Range annotation) {
        return settings -> settings.withBytes(Range.of(annotation.from(), annotation.to()));
    }
}
