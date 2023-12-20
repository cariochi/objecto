package com.cariochi.objecto.generator;

import com.cariochi.objecto.ObjectoSettings;
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
    public Object create(Type type, ObjectoSettings settings) {
        Class<?> componentType = ((Class<?>) type).getComponentType();
        if (settings.depth() == 1) {
            return Array.newInstance(componentType, 0);
        }
        int arrayLength = Random.nextInt(settings.arrays());
        Object array = Array.newInstance(componentType, arrayLength);
        for (int i = 0; i < arrayLength; i++) {
            Array.set(array, i, generateRandomObject(componentType, settings));
        }
        return array;
    }

}
