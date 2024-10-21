package com.cariochi.objecto.settings.functions.settings;

import com.cariochi.objecto.Settings;
import com.cariochi.objecto.settings.ObjectoSettings;
import java.util.function.UnaryOperator;

public class BigDecimalRange implements SettingsFunction<Settings.BigDecimals.Range> {

    @Override
    public UnaryOperator<ObjectoSettings> apply(Settings.BigDecimals.Range annotation) {
        return settings -> settings.withBigDecimals(settings.bigDecimals().withFrom(annotation.from()).withTo(annotation.to()));
    }
}
