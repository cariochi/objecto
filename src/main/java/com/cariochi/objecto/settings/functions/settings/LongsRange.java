package com.cariochi.objecto.settings.functions.settings;

import com.cariochi.objecto.Settings;
import com.cariochi.objecto.settings.ObjectoSettings;
import com.cariochi.objecto.settings.Range;
import java.util.function.UnaryOperator;

public class LongsRange implements SettingsFunction<Settings.Longs.Range> {

    @Override
    public UnaryOperator<ObjectoSettings> apply(Settings.Longs.Range annotation) {
        return settings -> settings.withLongs(Range.of(annotation.from(), annotation.to()));
    }
}
