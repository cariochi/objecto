package com.cariochi.objecto.settings.functions.settings;

import com.cariochi.objecto.Settings;
import com.cariochi.objecto.settings.ObjectoSettings;
import com.cariochi.objecto.settings.Range;
import java.util.function.UnaryOperator;

public class ArraysSizeRange implements SettingsFunction<Settings.Arrays.Size.Range> {

    @Override
    public UnaryOperator<ObjectoSettings> apply(Settings.Arrays.Size.Range annotation) {
        return settings -> {
            final ObjectoSettings.Collections arrays = settings.arrays();
            return settings.withArrays(
                    arrays.withSize(arrays.size().withRange(Range.of(annotation.from(), annotation.to())))
            );
        };
    }
}
