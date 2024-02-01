package com.cariochi.objecto;

import com.cariochi.objecto.generators.ObjectoGenerator;
import com.cariochi.objecto.proxy.ObjectModifier;
import com.cariochi.objecto.proxy.ProxyHandler;
import com.cariochi.objecto.settings.Settings;
import com.cariochi.objecto.settings.SettingsMapper;
import com.cariochi.reflecto.objects.methods.ObjectMethods;
import com.cariochi.reflecto.proxy.ProxyFactory;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;

import static com.cariochi.objecto.settings.SettingsMapper.map;
import static com.cariochi.objecto.utils.AnnotationUtils.findAllAnnotations;
import static com.cariochi.reflecto.Reflecto.reflect;

@UtilityClass
@WithSettings
public class Objecto {

    public static <T> T create(Class<T> targetClass) {
        final ObjectoGenerator generator = new ObjectoGenerator();
        final ProxyHandler<T> methodHandler = new ProxyHandler<>(targetClass, generator, getSettings(targetClass));
        final T proxy = ProxyFactory.createInstance(methodHandler, targetClass, ObjectModifier.class);
        addConstructors(proxy, generator);
        addReferenceGenerators(proxy, generator);
        addFieldSettings(proxy, generator);
        addGenerators(proxy, generator);
        addPostProcessors(proxy, generator);
        Optional.ofNullable(targetClass.getAnnotation(Seed.class)).map(Seed::value).ifPresent(generator::setSeed);
        return proxy;
    }

    public static Settings defaultSettings() {
        return map(Objecto.class.getAnnotation(WithSettings.class));
    }

    private static <T> Settings getSettings(Class<T> targetClass) {
        return findAllAnnotations(targetClass, WithSettings.class).stream().findFirst()
                .map(SettingsMapper::map)
                .orElse(defaultSettings());
    }

    private static void addConstructors(Object proxy, ObjectoGenerator generator) {
        reflect(proxy).methods().withAnnotation(Instantiator.class)
                .forEach(method -> generator.addCustomConstructor(method.getReturnType().actualType(), method::invoke));
    }

    private static void addReferenceGenerators(Object proxy, ObjectoGenerator generator) {
        reflect(proxy).methods().withAnnotation(References.class)
                .forEach(method -> method.findAnnotation(References.class)
                        .ifPresent(annotation -> generator.addReferenceGenerators(method.getReturnType().actualType(), annotation.value()))
                );
    }

    private static void addFieldSettings(Object proxy, ObjectoGenerator generator) {
        final ObjectMethods methods = reflect(proxy).methods();

        methods.withAnnotation(WithSettingsList.class)
                .forEach(method -> method.findAnnotation(WithSettingsList.class)
                        .map(WithSettingsList::value).stream().flatMap(Stream::of)
                        .forEach(annotation -> generator.addFieldSettings(method.getReturnType().actualType(), annotation.path(), map(annotation)))
                );

        methods.withAnnotation(WithSettings.class)
                .forEach(method -> method.findAnnotation(WithSettings.class)
                        .ifPresent(annotation -> generator.addFieldSettings(method.getReturnType().actualType(), annotation.path(), map(annotation)))
                );
    }

    private static void addGenerators(Object proxy, ObjectoGenerator generator) {
        reflect(proxy).methods().withAnnotation(Generator.class)
                .forEach(method -> method.findAnnotation(Generator.class)
                        .ifPresent(annotation -> generator.addCustomGenerator(annotation.type(), method.getReturnType().actualType(), annotation.expression(), method::invoke))
                );
    }

    private static void addPostProcessors(Object proxy, ObjectoGenerator generator) {
        reflect(proxy).methods().withAnnotation(PostProcessor.class).stream()
                .filter(method -> void.class.equals(method.getReturnType().actualType()))
                .filter(method -> method.getParameters().size() == 1)
                .forEach(method -> {
                    final Type type = method.getParameters().get(0).getType().actualType();
                    generator.addPostProcessor(type, method::invoke);
                });
    }

}
