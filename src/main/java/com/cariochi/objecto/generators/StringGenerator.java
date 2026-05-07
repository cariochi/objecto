package com.cariochi.objecto.generators;

import com.cariochi.objecto.config.ObjectoConfig;
import com.cariochi.objecto.config.ObjectoConfig.FakerConfig;
import com.cariochi.objecto.config.Size;
import com.cariochi.objecto.random.ObjectoRandom;
import com.cariochi.objecto.random.Strings;

import static org.apache.commons.lang3.StringUtils.isEmpty;

class StringGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return context.getType().is(String.class);
    }

    @Override
    public Object generate(Context context) {
        final ObjectoRandom objectoRandom = context.getRandom();
        final ObjectoConfig config = context.getConfig();
        final ObjectoConfig.Strings stringSettings = config.strings();
        final FakerConfig datafakerSettings = config.faker();

        final Strings strings = objectoRandom.strings()
                .selectFrom(stringSettings.chars())
                .withinRange(stringSettings.from(), stringSettings.to());

        String random;
        if (isEmpty(datafakerSettings.expression())) {

            final Size length = stringSettings.length();
            if (length.value() != null) {
                random = strings.nextString(length.value());
            } else {
                random = strings.nextString(length.range().from(), length.range().to());
            }

        } else {
            random = strings.datafaker(datafakerSettings.locale()).nextString(datafakerSettings.expression());
        }

        if (stringSettings.fieldNamePrefix()) {
            random = context.getFieldName() + " " + random;
        }
        return random;
    }


}
