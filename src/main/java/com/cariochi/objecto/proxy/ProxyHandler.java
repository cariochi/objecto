package com.cariochi.objecto.proxy;

import com.cariochi.objecto.Modifier;
import com.cariochi.objecto.ObjectoSettings;
import com.cariochi.objecto.generators.ObjectoGenerator;
import com.cariochi.objecto.utils.ObjectUtils;
import com.cariochi.reflecto.proxy.ProxyFactory;
import com.cariochi.reflecto.proxy.ProxyFactory.MethodHandler;
import com.cariochi.reflecto.proxy.ProxyFactory.MethodProceed;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProxyHandler<T> implements MethodHandler {

    private final Class<T> targetClass;
    private final ObjectoGenerator objectoGenerator;
    private final ObjectoSettings settings;
    private final Map<String, Object[]> parameters = new LinkedHashMap<>();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args, MethodProceed proceed) throws Throwable {

        if (Object.class.equals(method.getDeclaringClass())) {
            return proceed.proceed();
        }

        if (method.getName().equals("modifyObject")) {
            return ObjectUtils.modifyObject(args[0], parameters);
        }

        final Map<String, Object[]> methodParameter = getMethodParameters(method, args);

        if (proceed == null) {
            if (method.getReturnType().equals(method.getDeclaringClass())) {
                final ProxyHandler<T> childMethodHandler = new ProxyHandler<>(targetClass, objectoGenerator, settings);
                childMethodHandler.parameters.putAll(parameters);
                childMethodHandler.parameters.putAll(methodParameter);
                return ProxyFactory.createInstance(childMethodHandler, targetClass, ObjectModifier.class);
            } else {
                final Object instance = objectoGenerator.generateInstance(method.getGenericReturnType(), settings);
                final Map<String, Object[]> tmpMap = new LinkedHashMap<>();
                tmpMap.putAll(parameters);
                tmpMap.putAll(methodParameter);
                return ObjectUtils.modifyObject(instance, tmpMap);
            }
        } else {
            return ObjectUtils.modifyObject(proceed.proceed(), parameters);
        }

    }

    private Map<String, Object[]> getMethodParameters(Method method, Object[] args) {
        final Modifier methodModifier = method.getAnnotation(Modifier.class);
        if (methodModifier != null) {
            return getParametersFromMethodAnnotation(methodModifier, method.getParameters(), args);
        } else {
            return getParametersFromParametersAnnotations(method.getParameters(), args);
        }
    }

    private Map<String, Object[]> getParametersFromMethodAnnotation(Modifier methodModifier, Parameter[] parameters, Object[] args) {
        final Map<String, Object[]> methodParameters = new LinkedHashMap<>();
        if (methodModifier != null) {
            methodParameters.put(methodModifier.value(), args);
        } else {
            final Parameter param = parameters[0];
            if (param.isNamePresent()) {
                methodParameters.put(param.getName(), args);
            } else {
                throw new IllegalArgumentException("Cannot recognize parameter name. Please use @Modifier annotation");
            }
        }
        return methodParameters;
    }

    private Map<String, Object[]> getParametersFromParametersAnnotations(Parameter[] parameters, Object[] args) {
        final Map<String, Object[]> methodParameters = new LinkedHashMap<>();
        for (int i = 0; i < parameters.length; i++) {
            final Parameter param = parameters[i];
            final Modifier modifierParameter = param.getAnnotation(Modifier.class);
            if (modifierParameter == null) {
                if (param.isNamePresent()) {
                    methodParameters.put(param.getName(), Stream.of(args[i]).toArray());
                } else {
                    throw new IllegalArgumentException("Cannot recognize parameter name. Please use @Modifier annotation");
                }
            } else {
                methodParameters.put(modifierParameter.value(), Stream.of(args[i]).toArray());
            }
        }
        return methodParameters;
    }

}
