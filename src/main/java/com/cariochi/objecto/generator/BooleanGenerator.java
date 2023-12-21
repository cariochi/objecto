package com.cariochi.objecto.generator;

import com.cariochi.objecto.ObjectoSettings;
import com.cariochi.objecto.utils.Random;
import java.lang.reflect.Type;

public class BooleanGenerator extends Generator {

    public BooleanGenerator(RandomObjectGenerator randomObjectGenerator) {
        super(randomObjectGenerator);
    }

    @Override
    public boolean isSupported(Type type) {
        return type.equals(Boolean.class);
    }

    @Override
    public Object create(Type type, Type ownerType, ObjectoSettings settings) {
        return Random.nextBoolean();
    }

}
