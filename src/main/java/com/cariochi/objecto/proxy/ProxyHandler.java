package com.cariochi.objecto.proxy;

import com.cariochi.objecto.FieldGenerator;
import com.cariochi.objecto.Param;
import com.cariochi.objecto.RandomObjectGenerator;
import com.cariochi.objecto.utils.ObjectUtils;
import com.cariochi.reflecto.proxy.ProxyFactory;
import com.cariochi.reflecto.proxy.ProxyFactory.MethodPostProcessor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProxyHandler<T> implements InvocationHandler, MethodPostProcessor {

    private final RandomObjectGenerator randomObjectGenerator;
    private final Class<T> targetClass;
    private final Map<String, Object> parameters = new LinkedHashMap<>();

    public ProxyHandler(RandomObjectGenerator randomObjectGenerator, Class<T> targetClass) {
        this.randomObjectGenerator = randomObjectGenerator;
        this.targetClass = targetClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        final Map<String, Object> methodParameters = readMethodParameters(method, args);

        if (method.getReturnType().equals(method.getDeclaringClass())) {

            final ProxyFactory proxyFactory = ProxyFactory.builder()
                    .extendsClass(targetClass)
                    .methodInterceptor(() -> {
                        final ProxyHandler<T> handler = new ProxyHandler<>(randomObjectGenerator, targetClass);
                        handler.parameters.putAll(parameters);
                        handler.parameters.putAll(methodParameters);
                        return handler;
                    })
                    .build();
            return proxyFactory.create();

        } else {
            final Object instance = randomObjectGenerator.generateRandomObject(method.getGenericReturnType(), 6);
            final Map<String, Object> tmpMap = new LinkedHashMap<>();
            tmpMap.putAll(parameters);
            tmpMap.putAll(methodParameters);
            return ObjectUtils.modifyObject(instance, tmpMap);
        }
    }

    @Override
    public Object postProcess(Object proxy, Method method, Object[] args, Object result) {
        return method.getAnnotation(FieldGenerator.class) == null
                ? ObjectUtils.modifyObject(result, parameters)
                : result;
    }

    private Map<String, Object> readMethodParameters(Method method, Object[] args) {
        final Map<String, Object> methodParameters = new LinkedHashMap<>();
        final Parameter[] params = method.getParameters();
        for (int i = 0; i < params.length; i++) {
            final Parameter param = params[i];
            final Param annotation = param.getAnnotation(Param.class);
            if (annotation == null) {
                if (param.isNamePresent()) {
                    final Object value = args[i];
                    methodParameters.put(param.getName(), value);
                } else {
                    throw new IllegalArgumentException("Cannot recognize parameter name. Please use @Param annotation");
                }
            } else {
                for (String name : annotation.value()) {
                    final Object value = args[i];
                    methodParameters.put(name, value);
                }
            }
        }
        return methodParameters;
    }

}
