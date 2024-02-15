package com.cariochi.objecto.generators;

import com.cariochi.reflecto.types.ReflectoType;
import java.lang.reflect.Array;

class ArrayGenerator extends AbstractObjectsGenerator {

    public ArrayGenerator(ObjectoGenerator generator) {
        super(generator);
    }

    @Override
    public boolean isSupported(Context context) {
        return context.getType().isArray();
    }

    @Override
    public Object generate(Context context) {
        final ReflectoType type = context.getType().asArray().componentType();
        Context context2 = context.nextContext("[" + 0 + "]", type);
        final Object firstItem = generator.generate(context2);
        if (firstItem == null) {
            return null;
        }
        int arrayLength = context.getRandom().nextInt(context.getSettings().arrays().size());
        final Object array = Array.newInstance(type.actualClass(), arrayLength);
        Array.set(array, 0, firstItem);
        for (int i = 1; i < arrayLength; i++) {
            Context context1 = context.nextContext("[" + i + "]", type);
            final Object item = generator.generate(context1);
            Array.set(array, i, item);
        }
        return array;
    }

}
