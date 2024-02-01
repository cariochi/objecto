package com.cariochi.objecto.generators;

import com.cariochi.reflecto.types.TypeReflection;
import java.lang.reflect.Array;

class ArrayGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return context.getType().isArray();
    }

    @Override
    public Object generate(Context context) {
        final TypeReflection type = context.getType().arrayComponentType();
        final Object firstItem = context.nextContext("[" + 0 + "]", type, null).generate();
        if (firstItem == null) {
            return null;
        }
        int arrayLength = context.getRandom().nextInt(context.getSettings().arrays().size());
        final Object array = Array.newInstance(type.asClass(), arrayLength);
        Array.set(array, 0, firstItem);
        for (int i = 1; i < arrayLength; i++) {
            final Object item = context.nextContext("[" + i + "]", type, null).generate();
            Array.set(array, i, item);
        }
        return array;
    }

}
