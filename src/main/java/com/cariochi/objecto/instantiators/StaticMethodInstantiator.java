package com.cariochi.objecto.instantiators;

import com.cariochi.objecto.generators.Context;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Slf4j
public class StaticMethodInstantiator extends DefaultInstantiator {

    @Override
    public Object apply(Context context) {
        final Class<?> rawClass = context.getRawClass();
        final List<Method> methods = Stream.of(rawClass.getDeclaredMethods())
                .filter(m -> isPublic(m.getModifiers()))
                .filter(m -> isStatic(m.getModifiers()))
                .filter(m -> rawClass.isAssignableFrom(m.getReturnType()))
                .sorted(comparingInt((Method m) -> getAccessibilityOrder(m.getModifiers())).thenComparingInt(Method::getParameterCount))
                .collect(toList());
        return methods.stream()
                .map(c -> newInstance(c, context))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    private Object newInstance(Method staticConstructor, Context context) {
        try {
            log.trace("Using static method `{}`", staticConstructor.toGenericString());
            final Object[] args = generateRandomParameters(staticConstructor.getParameters(), context);
            log.trace("Static method parameters: `{}({})`", staticConstructor.getName(), Stream.of(args).map(String::valueOf).collect(joining(", ")));
            return staticConstructor.invoke(null, args);
        } catch (Exception e) {
            return null;
        }
    }

}
