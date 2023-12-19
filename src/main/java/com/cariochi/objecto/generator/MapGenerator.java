package com.cariochi.objecto.generator;

import com.cariochi.objecto.RandomObjectGenerator;
import com.cariochi.objecto.utils.Random;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;


public class MapGenerator extends Generator {

    public MapGenerator(RandomObjectGenerator randomObjectGenerator) {
        super(randomObjectGenerator);
    }

    @Override
    public boolean isSupported(Type type) {
        if (!(type instanceof ParameterizedType)) {
            return false;
        }
        final ParameterizedType parameterizedType = (ParameterizedType) type;
        return Map.class.isAssignableFrom((Class<?>) parameterizedType.getRawType());
    }

    @Override
    public Object create(Type type, int depth) {
        final ParameterizedType parameterizedType = (ParameterizedType) type;
        final Class<?> rawType = (Class<?>) parameterizedType.getRawType();
        final Type keyType = parameterizedType.getActualTypeArguments()[0];
        final Type valueType = parameterizedType.getActualTypeArguments()[1];
        final Map<Object, Object> map = createMap(rawType);
        if (depth == 0) {
            return map;
        }
        if (map != null) {
            for (int i = 0; i < Random.nextInt(1, 5); i++) {
                map.put(generateRandomObject(keyType, depth), generateRandomObject(valueType, depth));
            }
        }
        return map;
    }

    private static Map<Object, Object> createMap(Class<?> rawType) {
        try {
            return rawType.isInterface()
                    ? new HashMap<>()
                    : (Map<Object, Object>) rawType.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }

}
