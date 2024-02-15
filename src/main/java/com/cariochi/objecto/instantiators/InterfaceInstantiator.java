package com.cariochi.objecto.instantiators;

import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.ObjectoGenerator;
import com.cariochi.reflecto.types.ReflectoType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Supplier;
import org.apache.commons.lang3.tuple.Pair;

public class InterfaceInstantiator extends AbstractInstantiator {

    private static final List<Pair<Type, Supplier<Object>>> instantiators = List.of(
            Pair.of(Set.class, HashSet::new),
            Pair.of(Queue.class, LinkedList::new),
            Pair.of(Collection.class, ArrayList::new),
            Pair.of(Map.class, HashMap::new)
    );

    public InterfaceInstantiator(ObjectoGenerator generator) {
        super(generator);
    }

    @Override
    public Object apply(Context context) {
        final ReflectoType type = context.getType();
        if (!type.modifiers().isInterface()) {
            return null;
        }
        return instantiators.stream()
                .filter(e -> type.is(e.getKey()))
                .findFirst()
                .map(Map.Entry::getValue)
                .map(Supplier::get)
                .orElse(null);
    }


}
