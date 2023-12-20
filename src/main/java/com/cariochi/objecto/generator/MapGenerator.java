package com.cariochi.objecto.generator;

import com.cariochi.objecto.ObjectoSettings;
import com.cariochi.objecto.RandomObjectGenerator;
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
        return Map.class.isAssignableFrom((Class<?>) parameterizedType.getRawType());
    }

    @Override
    public Object create(Type type, ObjectoSettings settings) {
        final ParameterizedType parameterizedType = (ParameterizedType) type;
        final Class<?> rawType = (Class<?>) parameterizedType.getRawType();
        final Type keyType = parameterizedType.getActualTypeArguments()[0];
        final Type valueType = parameterizedType.getActualTypeArguments()[1];
        final Map<Object, Object> map = rawType.isInterface() ? new HashMap<>() : createInstance(rawType);
        if (settings.depth() == 1) {
            return map;
        }
        if (map != null) {
            for (int i = 0; i < Random.nextInt(settings.maps()); i++) {
                map.put(generateRandomObject(keyType, settings), generateRandomObject(valueType, settings));
            }
        }
        return map;
    }

}
