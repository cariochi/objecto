package com.cariochi.objecto.generators;

import com.cariochi.reflecto.types.TypeReflection;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class MapGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return context.getType().is(Map.class);
    }

    @Override
    public Object generate(Context context) {
        final TypeReflection mapType = context.getType().as(Map.class);
        final TypeReflection keyType = mapType.typeParameter(0);
        final TypeReflection valueType = mapType.typeParameter(1);

        final Map<Object, Object> map = (Map<Object, Object>) context.newInstance();
        if (map != null) {
            map.clear();
            for (int i = 0; i < context.getRandom().nextInt(context.getSettings().maps().size()); i++) {
                final Object key = context.nextContext("[key]", keyType, null).generate();
                final Object value = context.nextContext("[value]", valueType, null).generate();
                if (key == null || value == null) {
                    return map;
                }
                map.put(key, value);
            }
        }
        return map;
    }

}
