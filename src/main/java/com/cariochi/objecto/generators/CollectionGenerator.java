package com.cariochi.objecto.generators;

import com.cariochi.reflecto.types.ReflectoType;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class CollectionGenerator extends AbstractObjectsGenerator {

    public CollectionGenerator(ObjectoGenerator generator) {
        super(generator);
    }

    @Override
    public boolean isSupported(Context context) {
        return context.getType().is(Collection.class);
    }

    @Override
    public Object generate(Context context) {
        final ReflectoType elementType = context.getType().as(Iterable.class).arguments().get(0);
        final Collection<Object> collection = generator.newInstance(context);
        context.setInstance(collection);
        if (collection != null) {
            final int size = context.getSettings().collections().size().generate(generator.getRandom());
            for (int i = 0; i < size; i++) {
                Context nextContext = context.nextContext("[" + i + "]", elementType);
                final Object item = generator.generate(nextContext);
                if (item == null) {
                    return collection;
                }
                collection.add(item);
            }
        }
        return collection;
    }

}
