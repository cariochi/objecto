package com.cariochi.objecto.generator;

import com.cariochi.objecto.RandomObjectGenerator;
import com.cariochi.objecto.utils.Random;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

public class GenericArrayTypeGenerator extends Generator {

    public GenericArrayTypeGenerator(RandomObjectGenerator randomObjectGenerator) {
        super(randomObjectGenerator);
    }

    @Override
    public boolean isSupported(Type type) {
        return type instanceof GenericArrayType;
    }

    @Override
    public Object create(Type type, int depth) {
        Type componentType = ((GenericArrayType) type).getGenericComponentType();
        if (depth == 1) {
            return Array.newInstance((Class<?>) componentType, 0);
        }
        int arrayLength = Random.nextInt(1, 5);
        Object array = Array.newInstance((Class<?>) componentType, arrayLength);
        for (int i = 0; i < arrayLength; i++) {
            Array.set(array, i, generateRandomObject(componentType, depth));
        }
        return array;
    }

}
