package com.cariochi.objecto.settings.functions.settings;

import com.cariochi.objecto.Settings;
import com.cariochi.objecto.settings.ObjectoSettings;
import com.cariochi.objecto.settings.Range;
import java.util.function.UnaryOperator;

public class CharsRange implements SettingsFunction<Settings.Chars.Range> {

    @Override
    public UnaryOperator<ObjectoSettings> apply(Settings.Chars.Range annotation) {
        return settings -> settings.withChars(Range.of(annotation.from(), annotation.to()));
    }
}
