package com.cariochi.objecto.settings.functions.settings;

import com.cariochi.objecto.Settings;
import com.cariochi.objecto.settings.ObjectoSettings;
import com.cariochi.objecto.settings.Range;
import java.util.function.UnaryOperator;

public class StringsLengthRange implements SettingsFunction<Settings.Strings.Length.Range> {

    @Override
    public UnaryOperator<ObjectoSettings> apply(Settings.Strings.Length.Range annotation) {
        return settings -> {
            final ObjectoSettings.Strings strings = settings.strings();
            return settings.withStrings(
                    strings.withLength(strings.length().withRange(Range.of(annotation.from(), annotation.to())))
            );
        };
    }
}
