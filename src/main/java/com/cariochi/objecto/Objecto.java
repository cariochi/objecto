package com.cariochi.objecto;

import com.cariochi.objecto.generators.ObjectoGenerator;
import com.cariochi.objecto.proxy.HasSeed;
import com.cariochi.objecto.proxy.ObjectModifier;
import com.cariochi.objecto.proxy.ObjectoProxyHandler;
import com.cariochi.reflecto.methods.TargetMethod;
import com.cariochi.reflecto.parameters.ReflectoParameters;
import com.cariochi.reflecto.proxy.ProxyType;
import com.cariochi.reflecto.types.ReflectoType;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import static com.cariochi.reflecto.Reflecto.proxy;
import static com.cariochi.reflecto.Reflecto.reflect;
import static java.text.MessageFormat.format;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Slf4j
@UtilityClass
public class Objecto {

    public static <T> T create(Class<T> targetClass) {
        final Long seed = reflect(targetClass).annotations().find(Seed.class).map(Seed::value).orElse(null);
        return create(targetClass, seed);
    }

    public static <T> T create(Class<T> targetClass, Long seed) {
        final ObjectoGenerator generator = new ObjectoGenerator();
        if (seed != null) {
            generator.getRandom().setSeed(seed);
        }
        final ProxyType proxyType = proxy(targetClass, ObjectModifier.class, HasSeed.class);
        final T proxy = proxyType
                .with(() -> new ObjectoProxyHandler(proxyType, generator))
                .getConstructor()
                .newInstance();

        final List<TargetMethod> methods = reflect(targetClass).methods().stream()
                .map(m -> m.withTarget(proxy))
                .toList();

        addProviders(generator, methods);
        addReferenceGenerators(generator, methods);
        addFactoryMethods(generator, methods);
        addPostProcessors(generator, methods);

        return proxy;
    }

    private static void addProviders(ObjectoGenerator generator, List<TargetMethod> methods) {
        methods.stream()
                .filter(method -> method.annotations().contains(Constructor.class))
                .forEach(method -> generator.addProvider(method.returnType(), method::invoke));
    }

    private static void addReferenceGenerators(ObjectoGenerator generator, List<TargetMethod> methods) {
        methods.forEach(method -> method.annotations().find(References.class)
                .ifPresent(annotation -> generator.addReferenceGenerators(method.returnType().actualType(), annotation.value()))
        );
    }

    private static void addFactoryMethods(ObjectoGenerator generator, List<TargetMethod> methods) {

        final List<TargetMethod> factoryMethods = methods.stream()
                .filter(method -> !method.declaringType().actualClass().equals(Object.class))
                .filter(method -> {
                    final ReflectoParameters parameters = method.parameters();
                    if (parameters.isEmpty()) {
                        return true;
                    } else if (parameters.size() == 1) {
                        final ReflectoType type = parameters.get(0).type();
                        return type.is(Random.class) || type.is(ObjectoRandom.class);
                    } else {
                        return false;
                    }
                })
                .toList();

        // Fields Factories
        factoryMethods.stream()
                .filter(method -> method.annotations().contains(FieldFactory.class))
                .forEach(method -> {
                    final FieldFactory annotation = method.annotations().get(FieldFactory.class);
                    generator.addFieldFactory(reflect(annotation.type()), annotation.field(), method);
                });

        // Type Factories
        final Map<ReflectoType, List<TargetMethod>> map = factoryMethods.stream()
                .filter(method -> !method.annotations().contains(FieldFactory.class))
                .collect(groupingBy(TargetMethod::returnType, LinkedHashMap::new, toList()));

        map.forEach((type, list) -> {

            if (list.size() == 1) {
                generator.addTypeFactory(list.get(0));
                return;
            }

            final List<TargetMethod> annotatedMethods = list.stream()
                    .filter(method -> method.annotations().contains(TypeFactory.class))
                    .toList();

            if (annotatedMethods.size() == 1) {
                generator.addTypeFactory(annotatedMethods.get(0));
                return;
            }

            if (annotatedMethods.size() > 1) {
                throw new IllegalArgumentException(format(
                        "Multiple factory methods annotated with @TypeFactory for type `{0}`: {1}. "
                        + "Please ensure only one method is annotated with @TypeFactory.",
                        type, annotatedMethods
                ));
            } else {
                log.warn("Multiple factory methods found for type `{}`. "
                         + "To designate a default factory method, annotate one with @TypeFactory: {}.", type, list);
            }

        });

    }

    private static void addPostProcessors(ObjectoGenerator generator, List<TargetMethod> methods) {
        methods.stream()
                .filter(method -> method.annotations().contains(PostProcessor.class))
                .filter(method -> method.returnType().is(void.class))
                .filter(method -> method.parameters().size() == 1 || method.parameters().size() == 2)
                .forEach(method -> {
                    final ReflectoType type = method.parameters().get(0).type();
                    generator.addPostProcessor(type, method);
                });
    }

}
