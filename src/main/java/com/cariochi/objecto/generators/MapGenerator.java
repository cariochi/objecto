package com.cariochi.objecto.generators;

import com.cariochi.reflecto.types.ReflectoType;
import com.cariochi.reflecto.types.ReflectoType.TypeArguments;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class MapGenerator extends AbstractObjectsGenerator {

    public MapGenerator(ObjectoGenerator generator) {
        super(generator);
    }

    @Override
    public boolean isSupported(Context context) {
        return context.getType().is(Map.class);
    }

    @Override
    public Object generate(Context context) {
        final TypeArguments typeArguments = context.getType().as(Map.class).arguments();
        final ReflectoType keyType = typeArguments.get(0);
        final ReflectoType valueType = typeArguments.get(1);

        final Map<Object, Object> map = generator.getInstantiator().newInstance(context);
        context.setInstance(map);
        if (map != null) {
            map.clear();
            for (int i = 0; i < context.getRandom().nextInt(context.getSettings().maps().size()); i++) {
                Context context2 = context.nextContext("[key]", keyType);
                final Object key = generator.generate(context2);
                Context context1 = context.nextContext("[value]", valueType);
                final Object value = generator.generate(context1);
                if (key == null || value == null) {
                    return map;
                }
                map.put(key, value);
            }
        }
        return map;
    }

}
