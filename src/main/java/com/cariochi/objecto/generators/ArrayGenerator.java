package com.cariochi.objecto.generators;

import com.cariochi.objecto.utils.Random;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

import static com.cariochi.objecto.utils.GenericTypeUtils.getRawClass;

class ArrayGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return context.getRawClass().isArray();
    }

    @Override
    public Object generate(Context context) {
        int arrayLength = Random.nextInt(context.getSettings().arrays().size());
        final Type componentType = getComponentType(context.getType());
        final Class<?> componentClass = getRawClass(componentType, context.getOwnerType());
        final Object firstItem = context.nextContext("[" + 0 + "]", componentType, context.getOwnerType()).generate();
        if (firstItem == null) {
            return null;
        }
        final Object array = Array.newInstance(componentClass, arrayLength);
        Array.set(array, 0, firstItem);
        for (int i = 1; i < arrayLength; i++) {
            final Object item = context.nextContext("[" + i + "]", componentType, context.getOwnerType()).generate();
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
