package com.cariochi.objecto.settings.functions.settings;

import com.cariochi.objecto.Settings;
import com.cariochi.objecto.settings.ObjectoSettings;
import com.cariochi.objecto.settings.ObjectoSettings.Strings;
import java.util.function.UnaryOperator;

public class StringsParameters implements SettingsFunction<Settings.Strings.Parameters> {

    @Override
    public UnaryOperator<ObjectoSettings> apply(Settings.Strings.Parameters annotation) {
        return settings -> {
            final Strings strings = settings.strings()
                    .withLetters(annotation.letters())
                    .withNumbers(annotation.digits())
                    .withUppercase(annotation.uppercase())
                    .withFieldNamePrefix(annotation.useFieldNamePrefix());
            return settings.withStrings(strings);
        };
    }
}
