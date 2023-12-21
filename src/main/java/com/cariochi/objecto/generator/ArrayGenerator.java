package com.cariochi.objecto.generator;

import com.cariochi.objecto.ObjectoSettings;
import com.cariochi.objecto.utils.Random;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import org.apache.commons.lang3.reflect.TypeUtils;

public class ArrayGenerator extends Generator {

    public ArrayGenerator(RandomObjectGenerator randomObjectGenerator) {
        super(randomObjectGenerator);
    }

    @Override
    public boolean isSupported(Type type) {
        final Class<?> rawType = TypeUtils.getRawType(type, null);
        return rawType != null && rawType.isArray();
    }

    @Override
    public Object create(Type type, Type ownerType, ObjectoSettings settings) {
        int arrayLength = Random.nextInt(settings.arrays());
        final Type componentType = getComponentType(type);
        final Object firstItem = generateRandomObject(componentType, ownerType, null, settings);
        if (firstItem == null) {
            return null;
        }
        Object array = Array.newInstance(firstItem.getClass(), arrayLength);
        Array.set(array, 0, firstItem);
        for (int i = 1; i < arrayLength; i++) {
            final Object item = generateRandomObject(componentType, ownerType, null, settings);
            Array.set(array, i, item);
        }
        return array;
    }

    private Type getComponentType(Type arrayType) {
        if (arrayType instanceof GenericArrayType) {
            return ((GenericArrayType) arrayType).getGenericComponentType();
        } else {
            return ((Class<?>) arrayType).getComponentType();
        }

    }

}
