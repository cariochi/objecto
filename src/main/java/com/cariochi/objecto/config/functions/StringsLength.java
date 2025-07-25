package com.cariochi.objecto.config.functions;

import com.cariochi.objecto.Spec.Strings.Length;
import com.cariochi.objecto.config.ObjectoConfig;
import com.cariochi.objecto.config.ObjectoConfig.Strings;
import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.model.ConfigFunction;
import com.cariochi.reflecto.methods.ReflectoMethod;
import java.util.function.BiFunction;

public class StringsLength implements ConfigFunctionFactory<Length> {

    @Override
    public ConfigFunction createSettings(Length annotation, ReflectoMethod method) {
        final BiFunction<ObjectoConfig, Context, ObjectoConfig> function = (config, context) -> {
            final Strings strings = config.strings();
            return config.withStrings(strings.withLength(strings.length().withValue(annotation.value())));
        };
        return new ConfigFunction(method.returnType(), annotation.field(), function);
    }
}
