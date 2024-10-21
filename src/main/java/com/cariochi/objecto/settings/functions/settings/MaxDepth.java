package com.cariochi.objecto.settings.functions.settings;

import com.cariochi.objecto.Settings;
import com.cariochi.objecto.settings.ObjectoSettings;
import java.util.function.UnaryOperator;

public class MaxDepth implements SettingsFunction<Settings.MaxDepth> {

    @Override
    public UnaryOperator<ObjectoSettings> apply(Settings.MaxDepth annotation) {
        return settings -> settings.withMaxDepth(annotation.value());
    }
}
