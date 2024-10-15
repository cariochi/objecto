package com.cariochi.objecto.generators;

import com.cariochi.objecto.ObjectoRandom;
import com.cariochi.objecto.settings.ObjectoSettings;
import com.cariochi.objecto.settings.ObjectoSettings.Datafaker;
import com.cariochi.objecto.settings.ObjectoSettings.Strings;
import com.cariochi.objecto.settings.Range;

import static org.apache.commons.lang3.StringUtils.isEmpty;

class StringGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return context.getType().is(String.class);
    }

    @Override
    public Object generate(Context context) {
        final ObjectoRandom objectoRandom = context.getRandom();
        final ObjectoSettings settings = context.getSettings();
        final Strings stringSettings = settings.strings();
        final Range<Integer> size = stringSettings.size();
        final Datafaker datafakerSettings = settings.datafaker();

        String random = isEmpty(datafakerSettings.method())
                ? objectoRandom.nextString(size.min(), size.max(), stringSettings.letters(), stringSettings.numbers(), stringSettings.uppercase())
                : objectoRandom.nextDatafakerString(datafakerSettings.locale(), datafakerSettings.method());

        if (stringSettings.fieldNamePrefix()) {
            random = context.getFieldName() + " " + random;
        }
        return random;
    }


}
