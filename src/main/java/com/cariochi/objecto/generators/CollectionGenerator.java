package com.cariochi.objecto.generators;

import com.cariochi.objecto.utils.Random;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;

import static com.cariochi.objecto.utils.GenericTypeUtils.getRawClass;

@Slf4j
class CollectionGenerator extends Generator {

    public CollectionGenerator(ObjectoGenerator objectoGenerator) {
        super(objectoGenerator);
    }

    @Override
    public boolean isSupported(Type type, GenerationContext context) {
        final Class<?> rawType = getRawClass(type, context.ownerType());
        return rawType != null && Iterable.class.isAssignableFrom(rawType);
    }

    @Override
    public Object generate(Type type, GenerationContext context) {
        final ParameterizedType parameterizedType = (ParameterizedType) type;
        final Type elementType = parameterizedType.getActualTypeArguments()[0];
        final Collection<Object> collection = (Collection<Object>) createInstance(parameterizedType, context);
        if (collection != null) {
            collection.clear();
            for (int i = 0; i < Random.nextInt(context.settings().collections()); i++) {
                final Object item = generateRandomObject(elementType, context.withField("[" + i + "]"));
                if (item == null) {
                    return collection;
                }
                collection.add(item);
            }
        }
        return collection;
    }

}
