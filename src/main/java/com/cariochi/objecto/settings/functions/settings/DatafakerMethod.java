package com.cariochi.objecto.settings.functions.settings;

import com.cariochi.objecto.Settings;
import com.cariochi.objecto.settings.ObjectoSettings;
import java.util.function.UnaryOperator;

public class DatafakerMethod implements SettingsFunction<Settings.Datafaker.Method> {

    @Override
    public UnaryOperator<ObjectoSettings> apply(Settings.Datafaker.Method annotation) {
        return settings -> settings.withDatafaker(settings.datafaker().withMethod(annotation.value()));
    }
}
