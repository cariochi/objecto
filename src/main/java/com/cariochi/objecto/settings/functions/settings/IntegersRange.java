package com.cariochi.objecto.settings.functions.settings;

import com.cariochi.objecto.Settings;
import com.cariochi.objecto.settings.ObjectoSettings;
import com.cariochi.objecto.settings.Range;
import java.util.function.UnaryOperator;

public class IntegersRange implements SettingsFunction<Settings.Integers.Range> {

    @Override
    public UnaryOperator<ObjectoSettings> apply(Settings.Integers.Range annotation) {
        return settings -> settings.withIntegers(Range.of(annotation.from(), annotation.to()));
    }
}
