package com.cariochi.objecto.creators;

import com.cariochi.objecto.generators.GenerationContext;
import com.cariochi.objecto.generators.ObjectoGenerator;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.cariochi.objecto.utils.GenericTypeUtils.getRawClass;
import static java.lang.reflect.Modifier.isPublic;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

class ConstructorCreator extends DefaultCreator {

    public ConstructorCreator(ObjectoGenerator objectoGenerator) {
        super(objectoGenerator);
    }

    @Override
    public Object apply(Type type, GenerationContext context) {
        final Class<?> rawType = getRawClass(type, context.ownerType());
        final List<Constructor<?>> constructors = Stream.of(rawType.getDeclaredConstructors())
                .sorted(comparingInt((Constructor<?> c) -> getAccessibilityOrder(c.getModifiers())).thenComparingInt(Constructor::getParameterCount))
                .collect(toList());
        return constructors.stream()
                .map(c -> newInstance(c, type, context))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    private Object newInstance(Constructor<?> constructor, Type ownerType, GenerationContext context) {
        try {
            final Object[] args = generateRandomParameters(constructor.getParameters(), ownerType, context);
            if (!isPublic(constructor.getModifiers())) {
                constructor.setAccessible(true);
            }
            return constructor.newInstance(args);
        } catch (Exception e) {
            return null;
        }
    }

}
