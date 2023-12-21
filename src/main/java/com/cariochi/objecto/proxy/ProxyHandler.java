package com.cariochi.objecto.proxy;

import com.cariochi.objecto.ObjectoSettings;
import com.cariochi.objecto.Param;
import com.cariochi.objecto.generator.RandomObjectGenerator;
import com.cariochi.objecto.utils.ObjectUtils;
import com.cariochi.reflecto.proxy.ProxyFactory;
import com.cariochi.reflecto.proxy.ProxyFactory.MethodHandler;
import com.cariochi.reflecto.proxy.ProxyFactory.MethodProceed;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Setter;

public class ProxyHandler<T> implements MethodHandler {

    private final Class<T> targetClass;
    private final Map<String, Object> parameters = new LinkedHashMap<>();
    private final ObjectoSettings settings;

    @Setter
    private RandomObjectGenerator randomObjectGenerator;

    public ProxyHandler(Class<T> targetClass, ObjectoSettings settings) {
        this.targetClass = targetClass;
        this.settings = settings;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args, MethodProceed proceed) throws Throwable {

        if (Object.class.equals(method.getDeclaringClass())) {
            return proceed.proceed();
        }

        if (proceed == null) {
            final Map<String, Object> methodParameters = readMethodParameters(method, args);
            if (method.getReturnType().equals(method.getDeclaringClass())) {
                final ProxyHandler<T> childMethodHandler = new ProxyHandler<>(targetClass, settings);
                childMethodHandler.parameters.putAll(parameters);
                childMethodHandler.parameters.putAll(methodParameters);
                childMethodHandler.setRandomObjectGenerator(randomObjectGenerator);
                return ProxyFactory.createInstance(childMethodHandler, targetClass);
            } else {
                final Object instance = randomObjectGenerator.generateRandomObject(method.getGenericReturnType(), settings);
                final Map<String, Object> tmpMap = new LinkedHashMap<>();
                tmpMap.putAll(parameters);
                tmpMap.putAll(methodParameters);
                return ObjectUtils.modifyObject(instance, tmpMap);
            }
        } else {
            return ObjectUtils.modifyObject(proceed.proceed(), parameters);
        }

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
                methodParameters.put(annotation.value(), args[i]);
            }
        }
        return methodParameters;
    }

}
