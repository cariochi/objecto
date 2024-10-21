package com.cariochi.objecto.settings.functions.settings;

import com.cariochi.objecto.Settings;
import com.cariochi.objecto.settings.ObjectoSettings;
import java.util.function.UnaryOperator;

public class BigDecimalScale implements SettingsFunction<Settings.BigDecimals.Scale> {

    @Override
    public UnaryOperator<ObjectoSettings> apply(Settings.BigDecimals.Scale annotation) {
        return settings -> settings.withBigDecimals(settings.bigDecimals().withScale(annotation.value()));
    }
}
