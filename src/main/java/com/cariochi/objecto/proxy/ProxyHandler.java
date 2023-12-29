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
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProxyHandler<T> implements MethodHandler {

    private final Class<T> targetClass;
    private final ObjectoGenerator objectoGenerator;
    private final ObjectoSettings settings;
    private final Map<String, Object> parameters = new LinkedHashMap<>();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args, MethodProceed proceed) throws Throwable {

        if (Object.class.equals(method.getDeclaringClass())) {
            return proceed.proceed();
        }

        if (method.getName().equals("modifyObject")) {
            return ObjectUtils.modifyObject(args[0], parameters);
        }

        if (proceed == null) {
            final Map<String, Object> methodParameters = readMethodParameters(method, args);
            if (method.getAnnotation(Modifier.class) != null && method.getReturnType().equals(method.getDeclaringClass())) {
                final ProxyHandler<T> childMethodHandler = new ProxyHandler<>(targetClass, objectoGenerator, settings);
                childMethodHandler.parameters.putAll(parameters);
                childMethodHandler.parameters.putAll(methodParameters);
                return ProxyFactory.createInstance(childMethodHandler, targetClass, ObjectModifier.class);
            } else {
                final Object instance = objectoGenerator.generateInstance(method.getGenericReturnType(), settings);
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
        final Modifier methodModifier = method.getAnnotation(Modifier.class);
        if (methodModifier != null) {
            methodParameters.put(methodModifier.value(), args[0]);
        } else {
            final Parameter[] params = method.getParameters();
            for (int i = 0; i < params.length; i++) {
                final Parameter param = params[i];
                final Modifier modifierParameter = param.getAnnotation(Modifier.class);
                if (modifierParameter == null) {
                    if (param.isNamePresent()) {
                        final Object value = args[i];
                        methodParameters.put(param.getName(), value);
                    } else {
                        throw new IllegalArgumentException("Cannot recognize parameter name. Please use @Modifier annotation");
                    }
                } else {
                    methodParameters.put(modifierParameter.value(), args[i]);
                }
            }
        }
        return methodParameters;
    }

}
