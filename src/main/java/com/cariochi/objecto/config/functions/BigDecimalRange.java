package com.cariochi.objecto.config.functions;

import com.cariochi.objecto.Spec.BigDecimals.Range;
import com.cariochi.objecto.config.ObjectoConfig;
import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.model.ConfigFunction;
import com.cariochi.reflecto.methods.ReflectoMethod;
import java.util.function.BiFunction;

public class BigDecimalRange implements ConfigFunctionFactory<Range> {

    @Override
    public ConfigFunction createSettings(Range annotation, ReflectoMethod method) {
        final BiFunction<ObjectoConfig, Context, ObjectoConfig> function = (config, context) -> config.withBigDecimals(
                config.bigDecimals().withFrom(annotation.from()).withTo(annotation.to()));
        return new ConfigFunction(method.returnType(), annotation.field(), function);
    }
}
