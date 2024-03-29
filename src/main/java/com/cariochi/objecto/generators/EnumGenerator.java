package com.cariochi.objecto.generators;

import com.cariochi.objecto.settings.Range;
import java.util.List;

class EnumGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return context.getType().isEnum();
    }

    @Override
    public Object generate(Context context) {
        final List<?> enumConstants = context.getType().asEnum().constants().list();
        final int randomIndex = context.getRandom().nextInt(Range.of(0, enumConstants.size()));
        return enumConstants.get(randomIndex);
    }

}
