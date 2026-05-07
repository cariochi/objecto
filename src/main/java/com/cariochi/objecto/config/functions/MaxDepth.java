package com.cariochi.objecto.config.functions;

import com.cariochi.objecto.Generate;
import com.cariochi.objecto.config.ObjectoConfig;
import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.model.ConfigFunction;
import com.cariochi.reflecto.methods.ReflectoMethod;
import java.util.function.BiFunction;

public class MaxDepth implements ConfigFunctionFactory<Generate.MaxDepth> {

    @Override
    public ConfigFunction createSettings(Generate.MaxDepth annotation, ReflectoMethod method) {
        final BiFunction<ObjectoConfig, Context, ObjectoConfig> function = (config, context) -> config.withMaxDepth(annotation.value());
        return new ConfigFunction(method.returnType(), annotation.field(), function);
    }
}
