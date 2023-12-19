package com.cariochi.objecto.generator;

import com.cariochi.objecto.RandomObjectGenerator;
import com.cariochi.objecto.utils.Random;
import java.lang.reflect.Type;

public class StringGenerator extends Generator {

    public StringGenerator(RandomObjectGenerator randomObjectGenerator) {
        super(randomObjectGenerator);
    }

    @Override
    public boolean isSupported(Type type) {
        return type.equals(String.class);
    }

    @Override
    public Object create(Type type, int depth) {
        return Random.nextString(8, 16).toUpperCase();
    }


}
