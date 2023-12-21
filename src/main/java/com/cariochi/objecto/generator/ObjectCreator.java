package com.cariochi.objecto.generator;

import com.cariochi.objecto.ObjectoSettings;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
public class ObjectCreator {

    private final List<ExternalTypeGenerator> instanceCreators;
    private final RandomObjectGenerator randomObjectGenerator;

    public <T> T createInstance(Type type, ObjectoSettings settings) {
        return (T) instanceCreators.stream()
                .filter(generator -> generator.isSupported(type))
                .findFirst()
                .map(ExternalTypeGenerator::create)
                .orElseGet(() -> createInstanceDefault(type, settings));
    }

    private <T> T createInstanceDefault(Type type, ObjectoSettings settings) {
        final Class<T> aClass = getRawtype(type);
        if (aClass == null || aClass.isInterface() || Modifier.isAbstract(aClass.getModifiers())) {
            return null;
        }

        return createByStaticConstructor(type, aClass, settings)
                .or(() -> createByPublicConstructor(type, aClass, settings))
                .orElseGet(() -> {
                    log.info("Cannot create an instance of {}. Please create an @InstanceCreator method to specify how to instantiate this class.", type);
                    return null;
                });
    }

    private <T> Class<T> getRawtype(Type type) {
        Class<T> aClass = null;
        if (type instanceof Class) {
            aClass = ((Class<T>) type);
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            aClass = (Class<T>) parameterizedType.getRawType();
        }
        return aClass;
    }

    private <T> Optional<T> createByStaticConstructor(Type type, Class<T> aClass, ObjectoSettings settings) {
        final List<Method> methods = Stream.of(aClass.getDeclaredMethods())
                .filter(m -> Modifier.isPublic(m.getModifiers()))
                .filter(m -> Modifier.isStatic(m.getModifiers()))
                .filter(m -> aClass.isAssignableFrom(m.getReturnType()))
                .sorted(comparingInt((Method m) -> getAccessibilityOrder(m.getModifiers())).thenComparingInt(Method::getParameterCount))
                .collect(toList());
        return methods.stream()
                .map(c -> newInstance(c, type, settings))
                .filter(Objects::nonNull)
                .map(aClass::cast)
                .findFirst();
    }

    private <T> Optional<T> createByPublicConstructor(Type type, Class<T> aClass, ObjectoSettings settings) {
        final List<Constructor<T>> constructors = Stream.of(aClass.getDeclaredConstructors())
                .sorted(comparingInt((Constructor<?> c) -> getAccessibilityOrder(c.getModifiers())).thenComparingInt(Constructor::getParameterCount))
                .map(c -> (Constructor<T>) c)
                .collect(toList());
        return constructors.stream()
                .map(c -> newInstance(c, type, settings))
                .filter(Objects::nonNull)
                .findFirst();
    }

    private <T> T newInstance(Constructor<T> constructor, Type ownerType, ObjectoSettings settings) {
        try {
            if (settings.depth() == 0) {
                return null;
            }
            final Object[] args = generateRandomParameters(constructor.getParameters(), ownerType, settings);
            constructor.setAccessible(true);
            return constructor.newInstance(args);
        } catch (Exception e) {
            return null;
        }
    }

    private <T> T newInstance(Method staticConstructor, Type ownerType, ObjectoSettings settings) {
        try {
            if (settings.depth() == 0) {
                return null;
            }
            final Object[] args = generateRandomParameters(staticConstructor.getParameters(), ownerType, settings);
            return (T) staticConstructor.invoke(null, args);
        } catch (Exception e) {
            return null;
        }
    }

    private Object[] generateRandomParameters(Parameter[] constructor, Type ownerType, ObjectoSettings settings) {
        return Arrays.stream(constructor)
                .map(parameter -> randomObjectGenerator.generateRandomObject(
                        parameter.getParameterizedType(),
                        ownerType,
                        parameter.getName(),
                        settings.withDepth(settings.depth() - 1)
                ))
                .toArray();
    }

    private static int getAccessibilityOrder(int modifiers) {
        if (Modifier.isPublic(modifiers)) {
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
