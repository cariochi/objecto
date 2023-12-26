package com.cariochi.objecto;

import com.cariochi.objecto.generator.ObjectoGenerator;
import com.cariochi.objecto.proxy.ObjectModifier;
import com.cariochi.objecto.proxy.ProxyHandler;
import com.cariochi.reflecto.proxy.ProxyFactory;
import lombok.experimental.UtilityClass;

import static com.cariochi.objecto.ObjectoSettings.defaultSettings;
import static com.cariochi.reflecto.Reflecto.reflect;

@UtilityClass
public class Objecto {

    public static <T> T create(Class<T> targetClass) {
        return create(targetClass, defaultSettings());
    }

    public static <T> T create(Class<T> targetClass, ObjectoSettings settings) {
        final ObjectoGenerator objectoGenerator = new ObjectoGenerator();
        final ProxyHandler<T> methodHandler = new ProxyHandler<>(targetClass, objectoGenerator, settings);
        final T proxy = ProxyFactory.createInstance(methodHandler, targetClass, ObjectModifier.class);
        addInstanceCreators(proxy, objectoGenerator);
        addTypeGenerators(proxy, objectoGenerator);
        addPostProcessors(proxy, objectoGenerator);
        addFieldGenerators(proxy, objectoGenerator);
        return proxy;
    }

    private static void addInstanceCreators(Object proxy, ObjectoGenerator objectoGenerator) {
        reflect(proxy).methods().withAnnotation(InstanceCreator.class)
                .forEach(method -> objectoGenerator.addInstanceCreator(method.getReturnType(), method::invoke));
    }

    private static void addTypeGenerators(Object proxy, ObjectoGenerator objectoGenerator) {
        reflect(proxy).methods().withAnnotation(TypeGenerator.class)
                .forEach(method -> objectoGenerator.addTypeGenerator(method.getReturnType(), method::invoke));
    }

    private static void addPostProcessors(Object proxy, ObjectoGenerator objectoGenerator) {
        reflect(proxy).methods().withAnnotation(PostProcessor.class).stream()
                .filter(method -> void.class.equals(method.getReturnType()))
                .filter(method -> method.getParameterTypes().length == 1)
                .forEach(method -> objectoGenerator.addPostProcessor(method.getParameterTypes()[0], method::invoke));
    }

    private static void addFieldGenerators(Object proxy, ObjectoGenerator objectoGenerator) {
        reflect(proxy).methods().withAnnotation(FieldGenerator.class)
                .forEach(method -> method.findAnnotation(FieldGenerator.class)
                        .ifPresent(annotation -> objectoGenerator.addFieldGenerator(annotation.type(), method.getReturnType(), annotation.field(), method::invoke))
                );
    }

}
