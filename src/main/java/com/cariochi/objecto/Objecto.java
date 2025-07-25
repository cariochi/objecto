package com.cariochi.objecto;

import com.cariochi.objecto.generators.ObjectoGenerator;
import com.cariochi.objecto.proxy.HasSeed;
import com.cariochi.objecto.proxy.IsGenerator;
import com.cariochi.objecto.proxy.ObjectModifier;
import com.cariochi.objecto.proxy.ObjectoProxyHandler;
import com.cariochi.objecto.random.ObjectoRandom;
import com.cariochi.reflecto.methods.TargetMethod;
import com.cariochi.reflecto.parameters.ReflectoParameters;
import com.cariochi.reflecto.proxy.ProxyType;
import com.cariochi.reflecto.types.ReflectoType;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;
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
        final ProxyType proxyType = proxy(targetClass, IsGenerator.class, ObjectModifier.class, HasSeed.class);
        final T proxy = proxyType
                .with(() -> new ObjectoProxyHandler(proxyType, generator))
                .getConstructor()
                .newInstance();

        final ReflectoType reflectoType = reflect(targetClass);
        final List<TargetMethod> methods = reflectoType.methods().stream()
                .map(m -> m.withTarget(proxy))
                .toList();

        Stream.of(List.of(reflectoType), reflectoType.allInterfaces(), reflectoType.allSuperTypes())
                .flatMap(List::stream)
                .map(ReflectoType::annotations)
                .flatMap(annotations -> annotations.find(UseFactory.class).stream())
                .flatMap(annotation -> Stream.of(annotation.value()))
                .map(factoryClass -> create(factoryClass, seed))
                .map(factory -> ((IsGenerator) factory).getObjectoGenerator())
                .forEach(generator::use);

        addProviders(generator, methods);
        addReferenceGenerators(generator, methods);
        addGeneratorMethods(generator, methods);
        addPostProcessors(generator, methods);

        return proxy;
    }

    private static void addProviders(ObjectoGenerator generator, List<TargetMethod> methods) {
        methods.stream()
                .filter(method -> method.annotations().contains(Construct.class))
                .forEach(method -> generator.addProvider(method.returnType(), method::invoke));
    }

    private static void addReferenceGenerators(ObjectoGenerator generator, List<TargetMethod> methods) {
        methods.forEach(method -> method.annotations().find(Reference.class)
                .ifPresent(annotation -> generator.addReferenceGenerators(method.returnType().actualType(), annotation.value()))
        );
    }

    private static void addGeneratorMethods(ObjectoGenerator generator, List<TargetMethod> methods) {

        final List<TargetMethod> generatorMethods = methods.stream()
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

        // Fields Generators
        generatorMethods.stream()
                .filter(method -> method.annotations().contains(GenerateField.class))
                .forEach(method -> {
                    final GenerateField annotation = method.annotations().get(GenerateField.class);
                    generator.addFieldGenerator(reflect(annotation.type()), annotation.field(), method);
                });

        // Type Generators
        final Map<ReflectoType, List<TargetMethod>> map = generatorMethods.stream()
                .filter(method -> !method.annotations().contains(GenerateField.class))
                .collect(groupingBy(TargetMethod::returnType, LinkedHashMap::new, toList()));

        map.forEach((type, list) -> {

            if (list.size() == 1) {
                generator.addTypeGenerator(list.get(0));
                return;
            }

            final List<TargetMethod> primaryMethods = list.stream()
                    .filter(method -> method.annotations().contains(DefaultGenerator.class))
                    .toList();

            if (primaryMethods.isEmpty()) {
                log.warn("Multiple generator methods found for type `{}`. "
                         + "To designate a default factory method, annotate one with @DefaultGenerator: {}.", type, list);
            } else if (primaryMethods.size() == 1) {
                generator.addTypeGenerator(primaryMethods.get(0));
            } else {
                throw new IllegalArgumentException(format(
                        "Multiple generator methods annotated with @DefaultGenerator for type `{0}`: {1}. "
                        + "Please ensure only one method is annotated with @DefaultGenerator.",
                        type, primaryMethods
                ));
            }

        });
    }

    private static void addPostProcessors(ObjectoGenerator generator, List<TargetMethod> methods) {
        methods.stream()
                .filter(method -> method.annotations().contains(PostGenerate.class))
                .filter(method -> method.returnType().is(void.class))
                .filter(method -> method.parameters().size() == 1 || method.parameters().size() == 2)
                .forEach(method -> {
                    final ReflectoType type = method.parameters().get(0).type();
                    generator.addPostProcessor(type, method);
                });
    }

}
