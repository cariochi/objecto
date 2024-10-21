package com.cariochi.objecto.settings.functions.settings;

import com.cariochi.objecto.Settings;
import com.cariochi.objecto.settings.ObjectoSettings;
import java.util.function.UnaryOperator;

public class Nullable implements SettingsFunction<Settings.Nullable> {

    @Override
    public UnaryOperator<ObjectoSettings> apply(Settings.Nullable annotation) {
        return settings -> settings.withNullable(annotation.value());
    }
}
