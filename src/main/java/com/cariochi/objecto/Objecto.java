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
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import static com.cariochi.reflecto.Reflecto.proxy;
import static com.cariochi.reflecto.Reflecto.reflect;
import static java.text.MessageFormat.format;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * Entry point for creating Objecto factory implementations.
 * <p>
 * A factory is usually an interface or abstract class whose methods describe generated objects,
 * modifiers, custom generators, providers, references, and post-processors.
 *
 * <pre>{@code
 * interface UserFactory {
 *     User createUser();
 * }
 *
 * UserFactory users = Objecto.create(UserFactory.class);
 * User user = users.createUser();
 * }</pre>
 *
 * <p>
 * Factories can be seeded with {@link Seed} or with {@link #create(Class, Long)} to make generated
 * values reproducible.
 */
@Slf4j
@UtilityClass
public class Objecto {

    /**
     * Creates an Objecto implementation of the given factory type.
     * <p>
     * If the factory type is annotated with {@link Seed}, that seed is applied to the created factory.
     *
     * @param factoryClass factory interface or abstract class to implement
     * @param <T>         factory type
     * @return generated factory implementation
     */
    public static <T> T create(Class<T> factoryClass) {
        final Long seed = reflect(factoryClass).annotations().find(Seed.class).map(Seed::value).orElse(null);
        return create(factoryClass, seed);
    }

    /**
     * Creates an Objecto implementation of the given factory type and applies the supplied seed.
     *
     * <pre>{@code
     * IssueFactory issues = Objecto.create(IssueFactory.class, 42L);
     * Issue issue = issues.createIssue();
     * }</pre>
     *
     * @param factoryClass factory interface or abstract class to implement
     * @param seed deterministic seed, or {@code null} to use a generated random seed
     * @param <T> factory type
     * @return generated factory implementation
     */
    public static <T> T create(Class<T> factoryClass, Long seed) {
        final ObjectoGenerator generator = new ObjectoGenerator();
        if (seed != null) {
            generator.getRandom().setSeed(seed);
        }
        final ProxyType proxyType = proxy(factoryClass, IsGenerator.class, ObjectModifier.class, HasSeed.class);
        final T proxy = proxyType
                .with(() -> new ObjectoProxyHandler(proxyType, generator))
                .getConstructor()
                .newInstance();

        final ReflectoType reflectoType = reflect(factoryClass);
        final List<TargetMethod> methods = reflectoType.methods().stream()
                .map(m -> m.withTarget(proxy))
                .toList();

        Stream.of(List.of(reflectoType), reflectoType.allInterfaces(), reflectoType.allSuperTypes())
                .flatMap(List::stream)
                .map(ReflectoType::annotations)
                .flatMap(annotations -> annotations.find(ImportFactory.class).stream())
                .flatMap(annotation -> Stream.of(annotation.value()))
                .map(aClass -> create(aClass, seed))
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
                .filter(method -> method.annotations().contains(Provider.class))
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
                .filter(method -> method.annotations().contains(FieldGenerator.class))
                .forEach(method -> {
                    final FieldGenerator annotation = method.annotations().get(FieldGenerator.class);
                    generator.addFieldGenerator(reflect(annotation.type()), annotation.field(), method);
                });

        // Type Generators
        final Map<ReflectoType, List<TargetMethod>> map = generatorMethods.stream()
                .filter(method -> !method.annotations().contains(FieldGenerator.class))
                .collect(groupingBy(TargetMethod::returnType, LinkedHashMap::new, toList()));

        map.forEach((type, list) -> {

            if (list.size() == 1) {
                generator.addTypeGenerator(list.get(0));
                return;
            }

            final List<TargetMethod> primaryMethods = list.stream()
                    .filter(method -> method.annotations().contains(PrimaryGenerator.class))
                    .toList();

            if (primaryMethods.isEmpty()) {
                log.warn("Multiple generator methods found for type `{}`. "
                        + "To designate a default factory method, annotate one with @PrimaryGenerator: {}.", type, list);
            } else if (primaryMethods.size() == 1) {
                generator.addTypeGenerator(primaryMethods.get(0));
            } else {
                throw new IllegalArgumentException(format(
                        "Multiple generator methods annotated with @PrimaryGenerator for type `{0}`: {1}. "
                                + "Please ensure only one method is annotated with @PrimaryGenerator.",
                        type, primaryMethods
                ));
            }

        });
    }

    private static void addPostProcessors(ObjectoGenerator generator, List<TargetMethod> methods) {
        methods.stream()
                .filter(method -> method.annotations().contains(PostProcess.class))
                .filter(method -> method.returnType().is(void.class))
                .filter(method -> method.parameters().size() == 1 || method.parameters().size() == 2)
                .forEach(method -> {
                    final ReflectoType type = method.parameters().get(0).type();
                    generator.addPostProcessor(type, method);
                });
    }

}
