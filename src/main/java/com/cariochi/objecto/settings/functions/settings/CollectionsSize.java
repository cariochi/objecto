package com.cariochi.objecto.settings.functions.settings;

import com.cariochi.objecto.Settings;
import com.cariochi.objecto.settings.ObjectoSettings;
import java.util.function.UnaryOperator;

public class CollectionsSize implements SettingsFunction<Settings.Collections.Size> {

    @Override
    public UnaryOperator<ObjectoSettings> apply(Settings.Collections.Size annotation) {
        return settings -> {
            final ObjectoSettings.Collections collections = settings.collections();
            return settings.withCollections(collections.withSize(collections.size().withValue(annotation.value())));
        };
    }
}
