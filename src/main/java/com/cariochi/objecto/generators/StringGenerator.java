package com.cariochi.objecto.generators;

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
    public Object generate(Type type, GenerationContext context) {
        return Random.nextString(context.settings().strings());
    }


}
