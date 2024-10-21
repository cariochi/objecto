package com.cariochi.objecto.settings.functions.settings;

import com.cariochi.objecto.Settings;
import com.cariochi.objecto.settings.ObjectoSettings;
import java.util.function.UnaryOperator;

public class ArraysSize implements SettingsFunction<Settings.Arrays.Size> {

    @Override
    public UnaryOperator<ObjectoSettings> apply(Settings.Arrays.Size annotation) {
        return settings -> {
            final ObjectoSettings.Collections arrays = settings.arrays();
            return settings.withArrays(arrays.withSize(arrays.size().withValue(annotation.value())));
        };
    }
}
