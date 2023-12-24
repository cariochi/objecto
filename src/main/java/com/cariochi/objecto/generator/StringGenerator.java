package com.cariochi.objecto.generator;

import com.cariochi.objecto.utils.Random;
import java.lang.reflect.Type;

class StringGenerator extends Generator {

    public StringGenerator(ObjectoGenerator objectoGenerator) {
        super(objectoGenerator);
    }

    @Override
    public boolean isSupported(Type type, GenerationContext context) {
        return type.equals(String.class);
    }

    @Override
    public Object create(Type type, GenerationContext context) {
        return Random.nextString(context.settings().strings());
    }


}
