package com.cariochi.objecto.instantiators;

import com.cariochi.objecto.generators.Context;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

import static java.lang.reflect.Modifier.isPublic;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Slf4j
public class ConstructorInstantiator extends DefaultInstantiator {

    @Override
    public Object apply(Context context) {
        final List<Constructor<?>> constructors = Stream.of(context.getRawClass().getDeclaredConstructors())
                .sorted(comparingInt((Constructor<?> c) -> getAccessibilityOrder(c.getModifiers())).thenComparingInt(Constructor::getParameterCount))
                .collect(toList());
        return constructors.stream()
                .map(c -> newInstance(c, context))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    private Object newInstance(Constructor<?> constructor, Context context) {
        try {
            log.trace("Using constructor `{}`", constructor.toGenericString());
            final Object[] args = generateRandomParameters(constructor.getParameters(), context);
            if (!isPublic(constructor.getModifiers())) {
                constructor.setAccessible(true);
            }
            log.trace("Constructor parameters: `{}({})`", constructor.getName(), Stream.of(args).map(String::valueOf).collect(joining(", ")));
            return constructor.newInstance(args);
        } catch (Exception e) {
            return null;
        }
    }

}
