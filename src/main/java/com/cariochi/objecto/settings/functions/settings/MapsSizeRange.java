package com.cariochi.objecto.settings.functions.settings;

import com.cariochi.objecto.Settings;
import com.cariochi.objecto.settings.ObjectoSettings;
import com.cariochi.objecto.settings.Range;
import java.util.function.UnaryOperator;

public class MapsSizeRange implements SettingsFunction<Settings.Maps.Size.Range> {

    @Override
    public UnaryOperator<ObjectoSettings> apply(Settings.Maps.Size.Range annotation) {
        return settings -> {
            final ObjectoSettings.Collections maps = settings.maps();
            return settings.withMaps(
                    maps.withSize(maps.size().withRange(Range.of(annotation.from(), annotation.to())))
            );
        };
    }
}
