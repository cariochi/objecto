package com.cariochi.objecto.settings.functions.settings;

import com.cariochi.objecto.Settings;
import com.cariochi.objecto.settings.ObjectoSettings;
import com.cariochi.objecto.settings.Range;
import java.util.function.UnaryOperator;

public class FloatsRange implements SettingsFunction<Settings.Floats.Range> {

    @Override
    public UnaryOperator<ObjectoSettings> apply(Settings.Floats.Range annotation) {
        return settings -> settings.withFloats(Range.of(annotation.from(), annotation.to()));
    }
}
