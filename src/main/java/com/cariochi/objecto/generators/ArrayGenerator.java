package com.cariochi.objecto.generators;

import com.cariochi.reflecto.types.TypeReflection;
import java.lang.reflect.Array;

class ArrayGenerator extends AbstractGenerator {

    public ArrayGenerator(ObjectoGenerator generator) {
        super(generator);
    }

    @Override
    public boolean isSupported(Context context) {
        return context.getType().isArray();
    }

    @Override
    public Object generate(Context context) {
        final TypeReflection type = context.getType().arrayComponentType();
        final Object firstItem = generateObject(context.nextContext("[" + 0 + "]", type));
        if (firstItem == null) {
            return null;
        }
        int arrayLength = context.getRandom().nextInt(context.getSettings().arrays().size());
        final Object array = Array.newInstance(type.asClass(), arrayLength);
        Array.set(array, 0, firstItem);
        for (int i = 1; i < arrayLength; i++) {
            final Object item = generateObject(context.nextContext("[" + i + "]", type));
            Array.set(array, i, item);
        }
        return array;
    }

}
