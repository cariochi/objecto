package com.cariochi.objecto.generator;

import com.cariochi.objecto.utils.Random;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

import static com.cariochi.objecto.utils.GenericTypeUtils.getRawClass;

class ArrayGenerator extends Generator {

    public ArrayGenerator(ObjectoGenerator objectoGenerator) {
        super(objectoGenerator);
    }

    @Override
    public boolean isSupported(Type type, GenerationContext context) {
        final Class<?> rawType = getRawClass(type, context.ownerType());
        return rawType != null && rawType.isArray();
    }

    @Override
    public Object create(Type type, GenerationContext context) {
        int arrayLength = Random.nextInt(context.settings().arrays());
        final Type componentType = getComponentType(type);
        final Object firstItem = generateRandomObject(componentType, context.withField("[" + 0 + "]"));
        if (firstItem == null) {
            return null;
        }
        Object array = Array.newInstance(firstItem.getClass(), arrayLength);
        Array.set(array, 0, firstItem);
        for (int i = 1; i < arrayLength; i++) {
            final Object item = generateRandomObject(componentType, context.withField("[" + i + "]"));
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
