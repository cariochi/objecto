package com.cariochi.objecto.generators;

import com.cariochi.objecto.utils.Random;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

import static org.apache.commons.lang3.reflect.TypeUtils.getTypeArguments;

@Slf4j
class MapGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return context.isMap();
    }

    @Override
    public Object generate(Context context) {
        final Iterator<Type> iterator = getTypeArguments(context.getType(), Map.class).values().iterator();
        final Type keyType = iterator.next();
        final Type valueType = iterator.next();
        final Map<Object, Object> map = (Map<Object, Object>) context.newInstance();
        if (map != null) {
            map.clear();
            for (int i = 0; i < Random.nextInt(context.getSettings().mapsSize()); i++) {
                final Object key = context.nextContext("[key]", keyType, context.getOwnerType()).generate();
                final Object value = context.nextContext("[value]", valueType, context.getOwnerType()).generate();
                if (key == null || value == null) {
                    return map;
                }
                map.put(key, value);
            }
        }
        return map;
    }

}
