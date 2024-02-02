package com.cariochi.objecto.generators;

import com.cariochi.reflecto.types.TypeReflection;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class CollectionGenerator extends AbstractGenerator {

    public CollectionGenerator(ObjectoGenerator generator) {
        super(generator);
    }

    @Override
    public boolean isSupported(Context context) {
        return context.getType().is(Collection.class);
    }

    @Override
    public Object generate(Context context) {
        final TypeReflection elementType = context.getType().as(Iterable.class).typeParameter(0);
        final Collection<Object> collection = (Collection<Object>) newInstance(context);
        if (collection != null) {
            for (int i = 0; i < context.getRandom().nextInt(context.getSettings().collections().size()); i++) {
                final Object item = generateObject(context.nextContext("[" + i + "]", elementType));
                if (item == null) {
                    return collection;
                }
                collection.add(item);
            }
        }
        return collection;
    }

}
