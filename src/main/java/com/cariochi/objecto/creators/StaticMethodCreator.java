package com.cariochi.objecto.creators;

import com.cariochi.objecto.generators.GenerationContext;
import com.cariochi.objecto.generators.ObjectoGenerator;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.cariochi.objecto.utils.GenericTypeUtils.getRawClass;
import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

class StaticMethodCreator extends DefaultCreator {

    public StaticMethodCreator(ObjectoGenerator objectoGenerator) {
        super(objectoGenerator);
    }

    @Override
    public Object apply(Type type, GenerationContext context) {
        final Class<?> rawType = getRawClass(type, context.ownerType());
        final List<Method> methods = Stream.of(rawType.getDeclaredMethods())
                .filter(m -> isPublic(m.getModifiers()))
                .filter(m -> isStatic(m.getModifiers()))
                .filter(m -> rawType.isAssignableFrom(m.getReturnType()))
                .sorted(comparingInt((Method m) -> getAccessibilityOrder(m.getModifiers())).thenComparingInt(Method::getParameterCount))
                .collect(toList());
        return methods.stream()
                .map(c -> newInstance(c, type, context))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    private Object newInstance(Method staticConstructor, Type ownerType, GenerationContext context) {
        try {
            final Object[] args = generateRandomParameters(staticConstructor.getParameters(), ownerType, context);
            return staticConstructor.invoke(null, args);
        } catch (Exception e) {
            return null;
        }
    }

}
