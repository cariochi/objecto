package com.cariochi.objecto.settings.functions.settings;

import com.cariochi.objecto.Settings;
import com.cariochi.objecto.settings.ObjectoSettings;
import java.util.function.UnaryOperator;

public class StringsLength implements SettingsFunction<Settings.Strings.Length> {

    @Override
    public UnaryOperator<ObjectoSettings> apply(Settings.Strings.Length annotation) {
        return settings -> {
            final ObjectoSettings.Strings strings = settings.strings();
            return settings.withStrings(strings.withLength(strings.length().withValue(annotation.value())));
        };
    }
}
