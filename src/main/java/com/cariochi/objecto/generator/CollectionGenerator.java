package com.cariochi.objecto.generator;

import com.cariochi.objecto.RandomObjectGenerator;
import com.cariochi.objecto.utils.Random;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public Object create(Type type, int depth) {
        final ParameterizedType parameterizedType = (ParameterizedType) type;
        final Class<?> rawType = (Class<?>) parameterizedType.getRawType();
        final Type elementType = parameterizedType.getActualTypeArguments()[0];
        Collection<Object> collection = null;
        if (List.class.isAssignableFrom(rawType)) {
            collection = createList(rawType);
        } else if (Set.class.isAssignableFrom(rawType)) {
            collection = createSet(rawType);
        }
        if (depth == 0) {
            return collection;
        }
        if (collection != null) {
            for (int i = 0; i < Random.nextInt(1, 5); i++) {
                collection.add(generateRandomObject(elementType, depth));
            }
        }
        return collection;
    }

    private List<Object> createList(Class<?> rawType) {
        try {
            return rawType.isInterface()
                    ? new ArrayList<>()
                    : createInstance(rawType);
        } catch (Exception e) {
            return null;
        }
    }

    private Set<Object> createSet(Class<?> rawType) {
        try {
            return rawType.isInterface()
                    ? new HashSet<>()
                    : createInstance(rawType);
        } catch (Exception e) {
            return null;
        }
    }

}
