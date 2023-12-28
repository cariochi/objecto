package com.cariochi.objecto.generators;

import com.cariochi.objecto.utils.Random;
import java.lang.reflect.Type;

class BooleanGenerator extends Generator {

    public BooleanGenerator(ObjectoGenerator objectoGenerator) {
        super(objectoGenerator);
    }

    @Override
    public boolean isSupported(Type type, GenerationContext context) {
        return type.equals(Boolean.class);
    }

    @Override
    public Object generate(Type type, GenerationContext context) {
        return Random.nextBoolean();
    }

}
