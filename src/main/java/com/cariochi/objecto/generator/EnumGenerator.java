package com.cariochi.objecto.generator;

import com.cariochi.objecto.utils.Random;
import com.cariochi.objecto.utils.Range;
import java.lang.reflect.Type;

class EnumGenerator extends Generator {

    public EnumGenerator(ObjectoGenerator objectoGenerator) {
        super(objectoGenerator);
    }

    @Override
    public boolean isSupported(Type type, GenerationContext context) {
        return type instanceof Class && ((Class<?>) type).isEnum();
    }

    @Override
    public Object create(Type type, GenerationContext context) {
        Object[] enumConstants = ((Class<?>) type).getEnumConstants();
        return enumConstants[Random.nextInt(Range.of(0, enumConstants.length))];
    }

}
