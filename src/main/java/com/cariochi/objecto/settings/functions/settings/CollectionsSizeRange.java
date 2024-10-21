package com.cariochi.objecto.settings.functions.settings;

import com.cariochi.objecto.Settings;
import com.cariochi.objecto.settings.ObjectoSettings;
import com.cariochi.objecto.settings.Range;
import java.util.function.UnaryOperator;

public class CollectionsSizeRange implements SettingsFunction<Settings.Collections.Size.Range> {

    @Override
    public UnaryOperator<ObjectoSettings> apply(Settings.Collections.Size.Range annotation) {
        return settings -> {
            final ObjectoSettings.Collections collections = settings.collections();
            return settings.withCollections(
                    collections.withSize(collections.size().withRange(Range.of(annotation.from(), annotation.to())))
            );
        };
    }
}
