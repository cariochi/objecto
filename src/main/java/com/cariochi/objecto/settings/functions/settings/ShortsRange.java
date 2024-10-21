package com.cariochi.objecto.settings.functions.settings;

import com.cariochi.objecto.Settings;
import com.cariochi.objecto.settings.ObjectoSettings;
import com.cariochi.objecto.settings.Range;
import java.util.function.UnaryOperator;

public class ShortsRange implements SettingsFunction<Settings.Shorts.Range> {

    @Override
    public UnaryOperator<ObjectoSettings> apply(Settings.Shorts.Range annotation) {
        return settings -> settings.withShorts(Range.of(annotation.from(), annotation.to()));
    }
}
