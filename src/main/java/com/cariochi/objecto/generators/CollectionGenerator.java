package com.cariochi.objecto.generators;

import com.cariochi.reflecto.types.TypeReflection;
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
        final TypeReflection elementType = context.getType().as(Iterable.class).typeParameter(0);
        final Collection<Object> collection = (Collection<Object>) generator.getInstantiator().newInstance(context);
        context.setInstance(collection);
        if (collection != null) {
            for (int i = 0; i < context.getRandom().nextInt(context.getSettings().collections().size()); i++) {
                Context context1 = context.nextContext("[" + i + "]", elementType);
                final Object item = generator.generate(context1);
                if (item == null) {
                    return collection;
                }
                collection.add(item);
            }
        }
        return collection;
    }

}
