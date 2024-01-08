package com.cariochi.objecto.generators;

import com.cariochi.objecto.utils.Random;
import java.lang.reflect.Type;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;

import static org.apache.commons.lang3.reflect.TypeUtils.getTypeArguments;

@Slf4j
class CollectionGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return context.isCollection();
    }

    @Override
    public Object generate(Context context) {
        final Type elementType = getTypeArguments(context.getType(), Collection.class).values().iterator().next();
        final Collection<Object> collection = (Collection<Object>) context.newInstance();
        if (collection != null) {
            for (int i = 0; i < Random.nextInt(context.getSettings().collectionsSize()); i++) {
                final Object item = context.nextContext("[" + i + "]", elementType, context.getOwnerType()).generate();
                if (item == null) {
                    return collection;
                }
                collection.add(item);
            }
        }
        return collection;
    }

}
