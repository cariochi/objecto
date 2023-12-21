package com.cariochi.objecto.generator;

import com.cariochi.objecto.ObjectoSettings;
import com.cariochi.objecto.utils.Random;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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
        return Collection.class.isAssignableFrom((Class<?>) parameterizedType.getRawType());
    }

    @Override
    public Object create(Type type, Type ownerType, ObjectoSettings settings) {
        final ParameterizedType parameterizedType = (ParameterizedType) type;
        final Class<?> rawType = (Class<?>) parameterizedType.getRawType();
        final Type elementType = parameterizedType.getActualTypeArguments()[0];
        Collection<Object> collection = null;
        if (List.class.isAssignableFrom(rawType)) {
            collection = rawType.isInterface() ? new ArrayList<>() : createInstance(rawType);
        } else if (Set.class.isAssignableFrom(rawType)) {
            collection = rawType.isInterface() ? new HashSet<>() : createInstance(rawType);
        } else {
            collection = rawType.isInterface() ? new ArrayList<>() : createInstance(rawType);
        }
        if (settings.depth() == 1) {
            return collection;
        }
        if (collection != null) {
            for (int i = 0; i < Random.nextInt(settings.collections()); i++) {
                collection.add(generateRandomObject(elementType, ownerType, null, settings));
            }
        }
        return collection;
    }

}
