package com.cariochi.objecto.proxy;

import com.cariochi.objecto.Modifier;
import com.cariochi.objecto.Seed;
import com.cariochi.objecto.generators.ObjectoGenerator;
import com.cariochi.objecto.modifiers.ObjectoModifier;
import com.cariochi.objecto.settings.Settings;
import com.cariochi.reflecto.methods.ReflectoMethod;
import com.cariochi.reflecto.methods.TargetMethod;
import com.cariochi.reflecto.parameters.ReflectoParameter;
import com.cariochi.reflecto.parameters.ReflectoParameters;
import com.cariochi.reflecto.proxy.InvocationHandler;
import com.cariochi.reflecto.proxy.ProxyType;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ObjectoGeneratorProxy implements ObjectModifier, HasSeed, InvocationHandler {

    private final ProxyType proxyType;
    private final ObjectoGenerator generator;
    private final Settings settings;
    private final Map<String, Object[]> parameters = new LinkedHashMap<>();

    @Override
    public Object invoke(Object proxy, ReflectoMethod thisMethod, Object[] args, TargetMethod proceed) {

        if (proceed != null) {
            final Object result = proceed.invoke(args);
            return Object.class.equals(thisMethod.declaringType().actualType())
                    ? result
                    : modifyObject(result);
        }

        final Map<String, Object[]> methodParameter = getMethodParameters(thisMethod, args);
        if (thisMethod.returnType().equals(thisMethod.declaringType())) {
            return proxyType.with(() -> {
                        final ObjectoGeneratorProxy childHandler = new ObjectoGeneratorProxy(proxyType, generator, settings);
                        childHandler.parameters.putAll(parameters);
                        childHandler.parameters.putAll(methodParameter);
                        return childHandler;
                    })
                    .getConstructor()
                    .newInstance();
        } else {
            thisMethod.annotations().find(Seed.class).map(Seed::value).ifPresent(seed -> generator.getRandom().setSeed(seed));
            final Object instance = generator.generate(thisMethod.returnType().actualType(), settings);
            final Map<String, Object[]> tmpMap = new LinkedHashMap<>();
            tmpMap.putAll(parameters);
            tmpMap.putAll(methodParameter);
            return ObjectoModifier.modifyObject(instance, tmpMap);
        }

    }

    private Map<String, Object[]> getMethodParameters(ReflectoMethod method, Object[] args) {
        return method.annotations().find(Modifier.class)
                .map(methodModifier -> getParametersFromMethodAnnotation(methodModifier, method.parameters(), args))
                .orElseGet(() -> getParametersFromParametersAnnotations(method.parameters(), args));
    }

    private Map<String, Object[]> getParametersFromMethodAnnotation(Modifier methodModifier, ReflectoParameters parameters, Object[] args) {
        final Map<String, Object[]> methodParameters = new LinkedHashMap<>();
        if (methodModifier != null) {
            for (String value : methodModifier.value()) {
                methodParameters.put(value, args);
            }
        } else {
            final ReflectoParameter param = parameters.get(0);
            if (param.isNamePresent()) {
                methodParameters.put(param.name(), args);
            } else {
                throw new IllegalArgumentException("Cannot recognize parameter name. Please use @Modifier annotation");
            }
        }
        return methodParameters;
    }

    private Map<String, Object[]> getParametersFromParametersAnnotations(ReflectoParameters parameters, Object[] args) {
        final Map<String, Object[]> methodParameters = new LinkedHashMap<>();
        for (int i = 0; i < parameters.size(); i++) {
            final ReflectoParameter param = parameters.get(i);
            final Modifier modifierParameter = param.annotations().find(Modifier.class).orElse(null);
            if (modifierParameter == null) {
                if (param.isNamePresent()) {
                    methodParameters.put(param.name(), Stream.of(args[i]).toArray());
                } else {
                    throw new IllegalArgumentException("Cannot recognize parameter name. Please use @Modifier annotation");
                }
            } else {
                for (String value : modifierParameter.value()) {
                    methodParameters.put(value, Stream.of(args[i]).toArray());
                }
            }
        }
        return methodParameters;
    }

    @Override
    public <T> T modifyObject(T object) {
        return ObjectoModifier.modifyObject(object, parameters);
    }

    @Override
    public long getSeed() {
        return generator.getRandom().getSeed();
    }

    @Override
    public void setSeed(long seed) {
        generator.getRandom().setSeed(seed);
    }

    @Override
    public boolean isCustomSeed() {
        return generator.getRandom().isCustomSeed();
    }

}
