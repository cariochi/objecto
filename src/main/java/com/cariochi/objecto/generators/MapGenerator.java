package com.cariochi.objecto.generators;

import com.cariochi.objecto.utils.Random;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

import static com.cariochi.objecto.utils.GenericTypeUtils.getRawClass;

@Slf4j
class MapGenerator extends Generator {

    public MapGenerator(ObjectoGenerator objectoGenerator) {
        super(objectoGenerator);
    }

    @Override
    public boolean isSupported(Type type, GenerationContext context) {
        final Class<?> rawType = getRawClass(type, context.ownerType());
        return rawType != null && Map.class.isAssignableFrom(rawType);
    }

    @Override
    public Object generate(Type type, GenerationContext context) {
        final ParameterizedType parameterizedType = (ParameterizedType) type;
        final Type keyType = parameterizedType.getActualTypeArguments()[0];
        final Type valueType = parameterizedType.getActualTypeArguments()[1];
        final Map<Object, Object> map = (Map<Object, Object>) createInstance(parameterizedType, context);
        if (map != null) {
            map.clear();
            for (int i = 0; i < Random.nextInt(context.settings().maps()); i++) {
                final Object key = generateRandomObject(keyType, context.withField("[key]"));
                final Object value = generateRandomObject(valueType, context.withField("[value]"));
                if (key == null || value == null) {
                    return map;
                }
                map.put(key, value);
            }
        }
        return map;
    }

}
