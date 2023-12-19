package com.cariochi.objecto;

import com.cariochi.objecto.generator.ExternalFieldGenerator;
import com.cariochi.objecto.generator.ExternalTypeGenerator;
import com.cariochi.objecto.proxy.ProxyHandler;
import com.cariochi.reflecto.proxy.ProxyFactory;
import java.lang.reflect.Type;
import java.util.List;
import lombok.experimental.UtilityClass;

import static com.cariochi.reflecto.Reflecto.reflect;
import static java.util.stream.Collectors.toList;

@UtilityClass
public class Objecto {

    public static <T> T create(Class<T> targetClass) {
        final RandomObjectGenerator randomObjectGenerator = new RandomObjectGenerator();
        final T proxy = ProxyFactory.builder()
                .extendsClass(targetClass)
                .methodInterceptor(() -> new ProxyHandler<>(randomObjectGenerator, targetClass))
                .build()
                .create();
        randomObjectGenerator.setTypeConstructors(getTypeSeed(proxy));
        randomObjectGenerator.setTypeGenerators(getTypeGenerators(proxy));
        randomObjectGenerator.setFieldGenerators(getFieldGenerators(proxy));
        return proxy;
    }

    private static List<ExternalTypeGenerator> getTypeSeed(Object proxy) {
        return reflect(proxy).methods().withAnnotation(TypeConstructor.class).stream()
                .map(method -> new ExternalTypeGenerator(method.getReturnType(), method::invoke))
                .collect(toList());
    }

    private static List<ExternalTypeGenerator> getTypeGenerators(Object proxy) {
        return reflect(proxy).methods().withAnnotation(TypeGenerator.class).stream()
                .map(method -> new ExternalTypeGenerator(method.getReturnType(), method::invoke))
                .collect(toList());
    }

    private static List<ExternalFieldGenerator> getFieldGenerators(Object proxy) {
        return reflect(proxy).methods().withAnnotation(FieldGenerator.class).stream()
                .map(method -> {
                    final FieldGenerator annotation = method.findAnnotation(FieldGenerator.class).orElseThrow();
                    final Class<?> objectType = annotation.type();
                    final String fieldName = annotation.field();
                    final Type fielType = method.getReturnType();
                    return new ExternalFieldGenerator(objectType, fielType, fieldName, method::invoke);
                })
                .collect(toList());
    }

}
