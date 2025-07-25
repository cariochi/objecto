package com.cariochi.objecto.config.functions;

import com.cariochi.objecto.config.ObjectoConfig;
import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.model.ConfigFunction;
import com.cariochi.reflecto.methods.ReflectoMethod;
import java.util.function.BiFunction;

public class Faker implements ConfigFunctionFactory<com.cariochi.objecto.Faker> {

    @Override
    public ConfigFunction createSettings(com.cariochi.objecto.Faker annotation, ReflectoMethod method) {
        final BiFunction<ObjectoConfig, Context, ObjectoConfig> function = (config, context) -> {
            if (!annotation.expression().isEmpty()) {
                config = config.withFaker(config.faker().withExpression(annotation.expression()));
            }
            if (!annotation.locale().isEmpty()) {
                config = config.withFaker(config.faker().withLocale(annotation.locale()));
            }
            return config;
        };
        return new ConfigFunction(method.returnType(), annotation.field(), function);
    }
}
