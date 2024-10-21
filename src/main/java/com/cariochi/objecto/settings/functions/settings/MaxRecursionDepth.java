package com.cariochi.objecto.settings.functions.settings;

import com.cariochi.objecto.Settings;
import com.cariochi.objecto.settings.ObjectoSettings;
import java.util.function.UnaryOperator;

public class MaxRecursionDepth implements SettingsFunction<Settings.MaxRecursionDepth> {

    @Override
    public UnaryOperator<ObjectoSettings> apply(Settings.MaxRecursionDepth annotation) {
        return settings -> settings.withMaxRecursionDepth(annotation.value());
    }
}
