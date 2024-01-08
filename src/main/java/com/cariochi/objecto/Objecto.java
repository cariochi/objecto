package com.cariochi.objecto;

import com.cariochi.objecto.generators.ObjectoGenerator;
import com.cariochi.objecto.proxy.ObjectModifier;
import com.cariochi.objecto.proxy.ProxyHandler;
import com.cariochi.objecto.settings.Settings;
import com.cariochi.objecto.settings.SettingsMapper;
import com.cariochi.reflecto.proxy.ProxyFactory;
import java.util.Optional;
import lombok.experimental.UtilityClass;

import static com.cariochi.reflecto.Reflecto.reflect;

@UtilityClass
@WithSettings
public class Objecto {

    public static <T> T create(Class<T> targetClass) {
        final ObjectoGenerator generator = new ObjectoGenerator();
        final ProxyHandler<T> methodHandler = new ProxyHandler<>(targetClass, generator, getSettings(targetClass));
        final T proxy = ProxyFactory.createInstance(methodHandler, targetClass, ObjectModifier.class);
        addConstructors(proxy, generator);
        addBackReferenceGenerators(proxy, generator);
        addGenerators(proxy, generator);
        addPostProcessors(proxy, generator);
        return proxy;
    }

    public static Settings defaultSettings() {
        return SettingsMapper.map(Objecto.class.getAnnotation(WithSettings.class));
    }

    private static <T> Settings getSettings(Class<T> targetClass) {
        return Optional.ofNullable(targetClass.getAnnotation(WithSettings.class))
                .map(SettingsMapper::map)
                .orElse(defaultSettings());
    }

    private static void addConstructors(Object proxy, ObjectoGenerator generator) {
        reflect(proxy).methods().withAnnotation(Instantiator.class)
                .forEach(method -> generator.addCustomConstructor(method.getReturnType(), method::invoke));
    }

    private static void addBackReferenceGenerators(Object proxy, ObjectoGenerator generator) {
        reflect(proxy).methods().withAnnotation(References.class)
                .forEach(method -> method.findAnnotation(References.class)
                        .ifPresent(annotation -> generator.addBackReferenceGenerators(method.getReturnType(), annotation.value()))
                );
    }

    private static void addGenerators(Object proxy, ObjectoGenerator generator) {
        reflect(proxy).methods().withAnnotation(Generator.class)
                .forEach(method -> method.findAnnotation(Generator.class)
                        .ifPresent(annotation -> generator.addCustomGenerator(annotation.type(), method.getReturnType(), annotation.expression(), method::invoke))
                );
    }

    private static void addPostProcessors(Object proxy, ObjectoGenerator generator) {
        reflect(proxy).methods().withAnnotation(PostProcessor.class).stream()
                .filter(method -> void.class.equals(method.getReturnType()))
                .filter(method -> method.getParameterTypes().length == 1)
                .forEach(method -> generator.addPostProcessor(method.getParameterTypes()[0], method::invoke));
    }

}
