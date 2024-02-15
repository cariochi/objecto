package com.cariochi.objecto.instantiators;

import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.ObjectoGenerator;
import com.cariochi.reflecto.types.ReflectoType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ObjectoInstantiator {

    private final List<Function<Context, Object>> objectConstructors;

    public ObjectoInstantiator(ObjectoGenerator generator) {
        this.objectConstructors = new ArrayList<>(List.of(
                new InterfaceInstantiator(generator),
                new StaticMethodInstantiator(generator),
                new ConstructorInstantiator(generator)
        ));
    }

    public void addCustomConstructor(ReflectoType type, Supplier<Object> instantiator) {
        objectConstructors.add(0, context -> type.equals(context.getType()) ? instantiator.get() : null);
    }

    public <T> T newInstance(Type type) {
        return newInstance(new Context(type));
    }

        @SuppressWarnings("unchecked")
    public <T> T newInstance(Context context) {
        log.trace("Creating instance of `{}` with type `{}`", context.getPath(), context.getType().name());
        return (T) objectConstructors.stream()
                .map(creator -> creator.apply(context))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

}
