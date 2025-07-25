package com.cariochi.objecto.config.functions;

import com.cariochi.objecto.Spec;
import com.cariochi.objecto.config.ObjectoConfig;
import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.model.ConfigFunction;
import com.cariochi.reflecto.methods.ReflectoMethod;
import java.util.function.BiFunction;

public class MaxDepth implements ConfigFunctionFactory<Spec.MaxDepth> {

    @Override
    public ConfigFunction createSettings(Spec.MaxDepth annotation, ReflectoMethod method) {
        final BiFunction<ObjectoConfig, Context, ObjectoConfig> function = (config, context) -> config.withMaxDepth(annotation.value());
        return new ConfigFunction(method.returnType(), annotation.field(), function);
    }
}
