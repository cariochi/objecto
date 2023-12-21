package com.cariochi.objecto.generator;

import com.cariochi.objecto.ObjectoSettings;
import com.cariochi.objecto.utils.Random;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        final Class<?> rawType = (Class<?>) parameterizedType.getRawType();
        return Map.class.isAssignableFrom(rawType);
    }

    @Override
    public Object create(Type type, Type ownerType, ObjectoSettings settings) {
        final ParameterizedType parameterizedType = (ParameterizedType) type;
        final Type keyType = parameterizedType.getActualTypeArguments()[0];
        final Type valueType = parameterizedType.getActualTypeArguments()[1];
        final Map<Object, Object> map = createMapInstance(parameterizedType, settings);
        if (map != null) {
            for (int i = 0; i < Random.nextInt(settings.maps()); i++) {
                final Object key = generateRandomObject(keyType, ownerType, null, settings);
                final Object value = generateRandomObject(valueType, ownerType, null, settings);
                if (key == null || value == null) {
                    return map;
                }
                map.put(key, value);
            }
        }
        return map;
    }

    private Map<Object, Object> createMapInstance(ParameterizedType parameterizedType, ObjectoSettings settings) {
        final Class<?> rawType = (Class<?>) parameterizedType.getRawType();
        if (rawType.isInterface()) {
            return new HashMap<>();
        } else {
            return createInstance(parameterizedType, settings);
        }
    }

}
