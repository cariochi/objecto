package com.cariochi.objecto.generator;

import com.cariochi.objecto.ObjectoSettings;
import com.cariochi.objecto.utils.Random;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CollectionGenerator extends Generator {

    public CollectionGenerator(RandomObjectGenerator randomObjectGenerator) {
        super(randomObjectGenerator);
    }

    @Override
    public boolean isSupported(Type type) {
        if (!(type instanceof ParameterizedType)) {
            return false;
        }
        final ParameterizedType parameterizedType = (ParameterizedType) type;
        final Class<?> rawType = (Class<?>) parameterizedType.getRawType();
        return Iterable.class.isAssignableFrom(rawType);
    }

    @Override
    public Object create(Type type, Type ownerType, ObjectoSettings settings) {
        final ParameterizedType parameterizedType = (ParameterizedType) type;
        final Type elementType = parameterizedType.getActualTypeArguments()[0];
        final Collection<Object> collection = createCollectionInstance(parameterizedType, settings);
        if (collection != null) {
            for (int i = 0; i < Random.nextInt(settings.collections()); i++) {
                final Object item = generateRandomObject(elementType, ownerType, null, settings);
                if (item == null) {
                    return collection;
                }
                collection.add(item);
            }
        }
        return collection;
    }

    private Collection<Object> createCollectionInstance(ParameterizedType parameterizedType, ObjectoSettings settings) {
        final Class<?> rawType = (Class<?>) parameterizedType.getRawType();
        if (rawType.isInterface()) {
            if (Set.class.isAssignableFrom(rawType)) {
                return new HashSet<>();
            } else if (Queue.class.isAssignableFrom(rawType)) {
                return new LinkedList<>();
            } else {
                return new ArrayList<>();
            }
        } else {
            return (Collection<Object>) createInstance(parameterizedType, settings);
        }
    }

}
