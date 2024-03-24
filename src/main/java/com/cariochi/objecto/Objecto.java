package com.cariochi.objecto;

import com.cariochi.objecto.generators.ObjectoGenerator;
import com.cariochi.objecto.proxy.HasSeed;
import com.cariochi.objecto.proxy.ObjectModifier;
import com.cariochi.objecto.proxy.ObjectoGeneratorProxy;
import com.cariochi.objecto.settings.Settings;
import com.cariochi.objecto.settings.SettingsMapper;
import com.cariochi.reflecto.methods.TargetMethods;
import com.cariochi.reflecto.proxy.ProxyType;
import com.cariochi.reflecto.types.ReflectoType;
import java.util.List;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import static com.cariochi.objecto.settings.SettingsMapper.map;
import static com.cariochi.reflecto.Reflecto.proxy;
import static com.cariochi.reflecto.Reflecto.reflect;

@Slf4j
@UtilityClass
@WithSettings
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
                .with(() -> new ObjectoGeneratorProxy(proxyType, generator, getSettings(targetClass)))
                .getConstructor()
                .newInstance();

        final TargetMethods methods = reflect(proxy).methods();
        addConstructors(generator, methods);
        addReferenceGenerators(generator, methods);
        addFieldSettings(generator, methods);
        addGenerators(generator, methods);
        addPostProcessors(generator, methods);
        return proxy;
    }

    public static Settings defaultSettings() {
        return map(Objecto.class.getAnnotation(WithSettings.class));
    }

    private static <T> Settings getSettings(Class<T> targetClass) {
        final ReflectoType reflectoType = reflect(targetClass);
        return Stream.of(List.of(reflectoType), reflectoType.allInterfaces(), reflectoType.allSuperTypes()).flatMap(List::stream)
                .flatMap(type -> type.annotations().find(WithSettings.class).stream())
                .findFirst()
                .map(SettingsMapper::map)
                .orElseGet(Objecto::defaultSettings);
    }

    private static void addConstructors(ObjectoGenerator generator, TargetMethods methods) {
        methods.stream()
                .filter(method -> method.annotations().contains(Instantiator.class))
                .forEach(method -> generator.addCustomConstructor(method.returnType(), method::invoke));
    }

    private static void addReferenceGenerators(ObjectoGenerator generator, TargetMethods methods) {
        methods.forEach(method -> method.annotations().find(References.class)
                .ifPresent(annotation -> generator.addReferenceGenerators(method.returnType().actualType(), annotation.value()))
        );
    }

    private static void addFieldSettings(ObjectoGenerator generator, TargetMethods methods) {
        methods.forEach(method ->
                method.annotations().find(WithSettingsList.class)
                        .map(WithSettingsList::value).stream().flatMap(Stream::of)
                        .forEach(annotation -> generator.addFieldSettings(method.returnType(), annotation.path(), map(annotation)))
        );

        methods.forEach(method ->
                method.annotations().find(WithSettings.class)
                        .ifPresent(annotation -> generator.addFieldSettings(method.returnType(), annotation.path(), map(annotation)))
        );
    }

    private static void addGenerators(ObjectoGenerator generator, TargetMethods methods) {
        methods.forEach(method ->
                method.annotations().find(Generator.class)
                        .ifPresent(annotation -> generator.addCustomGenerator(annotation.type(), annotation.expression(), method))
        );
    }

    private static void addPostProcessors(ObjectoGenerator generator, TargetMethods methods) {
        methods.stream()
                .filter(method -> method.annotations().contains(PostProcessor.class))
                .filter(method -> method.returnType().is(void.class))
                .filter(method -> method.parameters().size() == 1)
                .forEach(method -> {
                    final ReflectoType type = method.parameters().get(0).type();
                    generator.addPostProcessor(type, method::invoke);
                });
    }

}
