package com.cariochi.objecto.instantiators;

import com.cariochi.objecto.generators.Context;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;

public class InterfaceInstantiator implements Function<Context, Object> {

    @Override
    public Object apply(Context context) {
        final Class<?> rawClass = context.getRawClass();
        if (!rawClass.isInterface()) {
            return null;
        }
        if (Set.class.isAssignableFrom(rawClass)) {
            return new HashSet<>();
        } else if (Queue.class.isAssignableFrom(rawClass)) {
            return new LinkedList<>();
        } else if (Collection.class.isAssignableFrom(rawClass)) {
            return new ArrayList<>();
        } else if (Map.class.isAssignableFrom(rawClass)) {
            return new HashMap<>();
        }
        return null;
    }


}
