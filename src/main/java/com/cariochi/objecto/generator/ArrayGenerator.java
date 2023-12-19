package com.cariochi.objecto.generator;

import com.cariochi.objecto.RandomObjectGenerator;
import com.cariochi.objecto.utils.Random;
import java.lang.reflect.Array;
import java.lang.reflect.Type;

public class ArrayGenerator extends Generator {

    public ArrayGenerator(RandomObjectGenerator randomObjectGenerator) {
        super(randomObjectGenerator);
    }

    @Override
    public boolean isSupported(Type type) {
        return type instanceof Class && ((Class<?>) type).isArray();
    }

    @Override
    public Object create(Type type, int depth) {
        Class<?> componentType = ((Class<?>) type).getComponentType();
        if (depth == 0) {
            return Array.newInstance(componentType, 0);
        }
        int arrayLength = Random.nextInt(2, 5);
        Object array = Array.newInstance(componentType, arrayLength);
        for (int i = 0; i < arrayLength; i++) {
            Array.set(array, i, generateRandomObject(componentType, depth));
        }
        return array;
    }

}
