package com.cariochi.objecto.instantiators;

import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.ObjectoGenerator;
import com.cariochi.reflecto.types.TypeReflection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class InterfaceInstantiator extends AbstractInstantiator {

    public InterfaceInstantiator(ObjectoGenerator generator) {
        super(generator);
    }

    @Override
    public Object apply(Context context) {
        final TypeReflection type = context.getType();
        if (!type.isInterface()) {
            return null;
        }
        if (type.is(Set.class)) {
            return new HashSet<>();
        } else if (type.is(Queue.class)) {
            return new LinkedList<>();
        } else if (type.is(Collection.class)) {
            return new ArrayList<>();
        } else if (type.is(Map.class)) {
            return new HashMap<>();
        }
        return null;
    }


}
