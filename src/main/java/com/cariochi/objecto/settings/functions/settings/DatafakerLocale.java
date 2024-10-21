package com.cariochi.objecto.settings.functions.settings;

import com.cariochi.objecto.Settings;
import com.cariochi.objecto.settings.ObjectoSettings;
import java.util.function.UnaryOperator;

public class DatafakerLocale implements SettingsFunction<Settings.Datafaker.Locale> {

    @Override
    public UnaryOperator<ObjectoSettings> apply(Settings.Datafaker.Locale annotation) {
        return settings -> settings.withDatafaker(settings.datafaker().withLocale(annotation.value()));
    }
}
