package com.cariochi.objecto.creators;

import com.cariochi.objecto.generators.GenerationContext;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import static com.cariochi.objecto.utils.GenericTypeUtils.getRawClass;

class InterfaceCreator implements Creator {

    @Override
    public Object apply(Type type, GenerationContext context) {
        final Class<?> rawType = getRawClass(type, context.ownerType());
        if (Set.class.isAssignableFrom(rawType)) {
            return new HashSet<>();
        } else if (Queue.class.isAssignableFrom(rawType)) {
            return new LinkedList<>();
        } else if (Iterable.class.isAssignableFrom(rawType)) {
            return new ArrayList<>();
        } else if (Map.class.isAssignableFrom(rawType)) {
            return new HashMap<>();
        }
        return null;
    }


}
