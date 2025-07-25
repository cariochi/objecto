package com.cariochi.objecto.proxy;

import com.cariochi.objecto.Modify;
import com.cariochi.objecto.Seed;
import com.cariochi.objecto.config.ObjectoConfig;
import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.ObjectoGenerator;
import com.cariochi.objecto.generators.model.ConfigFunction;
import com.cariochi.objecto.modifiers.ObjectoModifier;
import com.cariochi.objecto.utils.ConfigUtils;
import com.cariochi.reflecto.methods.ReflectoMethod;
import com.cariochi.reflecto.methods.TargetMethod;
import com.cariochi.reflecto.parameters.ReflectoParameter;
import com.cariochi.reflecto.parameters.ReflectoParameters;
import com.cariochi.reflecto.proxy.InvocationHandler;
import com.cariochi.reflecto.proxy.ProxyType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.cariochi.objecto.config.ObjectoConfig.DEFAULT_SETTINGS;
import static java.util.stream.Collectors.toCollection;

@Slf4j
@RequiredArgsConstructor
public class ObjectoProxyHandler implements IsGenerator, ObjectModifier, HasSeed, InvocationHandler {

    private final ProxyType proxyType;
    private final ObjectoGenerator generator;
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
                        final ObjectoProxyHandler childHandler = new ObjectoProxyHandler(proxyType, generator);
                        childHandler.parameters.putAll(parameters);
                        childHandler.parameters.putAll(methodParameter);
                        return childHandler;
                    })
                    .getConstructor()
                    .newInstance();
        } else {

            final Context context = Context.builder()
                    .config(DEFAULT_SETTINGS)
                    .type(thisMethod.returnType())
                    .random(generator.getRandom())
                    .build();

            return invoke(thisMethod, methodParameter, context);
        }

    }

    public Object invoke(ReflectoMethod thisMethod, Map<String, ? extends Object[]> methodParameter, Context context) {

        ObjectoConfig config = context.getConfig();

        final List<ConfigFunction> methodSettings = ConfigUtils.getMethodSettings(thisMethod);

        // Apply type config
        for (ConfigFunction s : methodSettings) {
            if (s.getField().isEmpty()) {
                config = s.getFunction().apply(config, context);
            }
        }
        context = context.withConfig(config);

        // Apply field config
        List<ConfigFunction> fieldSettings = methodSettings.stream().filter(s -> !s.getField().isEmpty()).collect(toCollection(ArrayList::new));
        fieldSettings.addAll(context.getFieldConfigs());
        context = context.withFieldConfigs(fieldSettings);

        thisMethod.annotations().find(Seed.class).map(Seed::value).ifPresent(context.getRandom()::setSeed);

        final Object instance = generator.generate(context);

        final Map<String, Object[]> tmpMap = new LinkedHashMap<>();
        tmpMap.putAll(parameters);
        tmpMap.putAll(methodParameter);
        return ObjectoModifier.modifyObject(instance, tmpMap);

    }

    public Map<String, Object[]> getMethodParameters(ReflectoMethod method, Object[] args) {
        return method.annotations().find(Modify.class)
                .map(methodModifier -> getParametersFromMethodAnnotation(methodModifier, method.parameters(), args))
                .orElseGet(() -> getParametersFromParametersAnnotations(method.parameters(), args));
    }

    private Map<String, Object[]> getParametersFromMethodAnnotation(Modify methodModifier, ReflectoParameters parameters, Object[] args) {
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
                throw new IllegalArgumentException("Cannot recognize parameter name. Please use @Modify annotation");
            }
        }
        return methodParameters;
    }

    private Map<String, Object[]> getParametersFromParametersAnnotations(ReflectoParameters parameters, Object[] args) {
        final Map<String, Object[]> methodParameters = new LinkedHashMap<>();
        for (int i = 0; i < parameters.size(); i++) {
            final ReflectoParameter param = parameters.get(i);
            final Modify modifierParameter = param.annotations().find(Modify.class).orElse(null);
            if (modifierParameter == null) {
                if (param.isNamePresent()) {
                    methodParameters.put(param.name(), Stream.of(args[i]).toArray());
                } else {
                    throw new IllegalArgumentException("Cannot recognize parameter name. Please use @Modify annotation");
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

    @Override
    public ObjectoGenerator getObjectoGenerator() {
        return generator;
    }
}
