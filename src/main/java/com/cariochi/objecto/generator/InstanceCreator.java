package com.cariochi.objecto.generator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.cariochi.objecto.utils.GenericTypeUtils.getRawClass;
import static java.lang.reflect.Modifier.isAbstract;
import static java.lang.reflect.Modifier.isPublic;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
public class InstanceCreator {

    private final ObjectoGenerator objectoGenerator;
    private final Map<Type, Supplier<Object>> externalCreators = new HashMap<>();

    public Object createInstance(Type type, GenerationContext context) {
        return Optional.ofNullable(externalCreators.get(type))
                .map(Supplier::get)
                .orElseGet(() -> createInstanceDefault(type, context));
    }

    public void addInstanceCreator(Type type, Supplier<Object> creator) {
        externalCreators.put(type, creator);
    }

    private Object createInstanceDefault(Type type, GenerationContext context) {
        final Class<?> aClass = getRawClass(type, context.ownerType());
        if (aClass == null || aClass.isInterface() || isAbstract(aClass.getModifiers())) {
            return null;
        }
        return Optional.empty()
                .or(() -> createByStaticConstructor(type, aClass, context))
                .or(() -> createByPublicConstructor(type, aClass, context))
                .orElseGet(() -> {
                    log.info("Cannot create an instance of {}. Please create an @InstanceCreator method to specify how to instantiate this class.", type);
                    return null;
                });
    }

    private Optional<?> createByStaticConstructor(Type type, Class<?> aClass, GenerationContext context) {
        final List<Method> methods = Stream.of(aClass.getDeclaredMethods())
                .filter(m -> isPublic(m.getModifiers()))
                .filter(m -> Modifier.isStatic(m.getModifiers()))
                .filter(m -> aClass.isAssignableFrom(m.getReturnType()))
                .sorted(comparingInt((Method m) -> getAccessibilityOrder(m.getModifiers())).thenComparingInt(Method::getParameterCount))
                .collect(toList());
        return methods.stream()
                .map(c -> newInstance(c, type, context))
                .filter(Objects::nonNull)
                .findFirst();
    }

    private Optional<?> createByPublicConstructor(Type type, Class<?> aClass, GenerationContext context) {
        final List<Constructor<?>> constructors = Stream.of(aClass.getDeclaredConstructors())
                .sorted(comparingInt((Constructor<?> c) -> getAccessibilityOrder(c.getModifiers())).thenComparingInt(Constructor::getParameterCount))
                .collect(toList());
        return constructors.stream()
                .map(c -> newInstance(c, type, context))
                .filter(Objects::nonNull)
                .findFirst();
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

    private Object newInstance(Method staticConstructor, Type ownerType, GenerationContext context) {
        try {
            final Object[] args = generateRandomParameters(staticConstructor.getParameters(), ownerType, context);
            return staticConstructor.invoke(null, args);
        } catch (Exception e) {
            return null;
        }
    }

    private Object[] generateRandomParameters(Parameter[] constructor, Type ownerType, GenerationContext context) {
        return Arrays.stream(constructor)
                .map(parameter -> {
                    final GenerationContext childContext = context.next()
                            .withOwnerType(ownerType)
                            .withFieldName(parameter.getName());
                    return objectoGenerator.generateInstance(parameter.getParameterizedType(), childContext);
                })
                .toArray();
    }

    private static int getAccessibilityOrder(int modifiers) {
        if (isPublic(modifiers)) {
            return 0;
        } else if (Modifier.isProtected(modifiers)) {
            return 1;
        } else if (Modifier.isPrivate(modifiers)) {
            return 2;
        } else {
            return 3;
        }
    }

}
