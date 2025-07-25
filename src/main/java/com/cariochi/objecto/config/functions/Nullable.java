package com.cariochi.objecto.config.functions;

import com.cariochi.objecto.Spec;
import com.cariochi.objecto.config.ObjectoConfig;
import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.model.ConfigFunction;
import com.cariochi.reflecto.methods.ReflectoMethod;
import java.util.function.BiFunction;

public class Nullable implements ConfigFunctionFactory<Spec.Nullable> {

    @Override
    public ConfigFunction createSettings(Spec.Nullable annotation, ReflectoMethod method) {
        final BiFunction<ObjectoConfig, Context, ObjectoConfig> function = (config, context) -> config.withNullable(annotation.value());
        return new ConfigFunction(method.returnType(), annotation.field(), function);
    }
}
