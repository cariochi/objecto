package com.cariochi.objecto.generator;

import com.cariochi.objecto.RandomObjectGenerator;
import com.cariochi.objecto.utils.Random;
import java.lang.reflect.Type;

public class EnumGenerator extends Generator {

    public EnumGenerator(RandomObjectGenerator randomObjectGenerator) {
        super(randomObjectGenerator);
    }

    @Override
    public boolean isSupported(Type type) {
        return type instanceof Class && ((Class<?>) type).isEnum();
    }

    @Override
    public Object create(Type type, int depth) {
        Object[] enumConstants = ((Class<?>) type).getEnumConstants();
        return enumConstants[Random.nextInt(0, enumConstants.length)];
    }

}
