package com.cariochi.objecto.generators;

import com.cariochi.objecto.utils.Random;
import com.cariochi.objecto.utils.Range;

class EnumGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return context.getRawClass().isEnum();
    }

    @Override
    public Object generate(Context context) {
        Object[] enumConstants = context.getRawClass().getEnumConstants();
        return enumConstants[Random.nextInt(Range.of(0, enumConstants.length))];
    }

}
