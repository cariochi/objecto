package com.cariochi.objecto.settings.functions.settings;

import com.cariochi.objecto.Settings;
import com.cariochi.objecto.settings.ObjectoSettings;
import java.util.function.UnaryOperator;

public class MapsSize implements SettingsFunction<Settings.Maps.Size> {

    @Override
    public UnaryOperator<ObjectoSettings> apply(Settings.Maps.Size annotation) {
        return settings -> {
            final ObjectoSettings.Collections maps = settings.maps();
            return settings.withMaps(maps.withSize(maps.size().withValue(annotation.value())));
        };
    }
}
