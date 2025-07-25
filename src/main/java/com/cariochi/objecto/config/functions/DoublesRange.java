package com.cariochi.objecto.config.functions;

import com.cariochi.objecto.Spec.Doubles;
import com.cariochi.objecto.config.ObjectoConfig;
import com.cariochi.objecto.config.Range;
import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.model.ConfigFunction;
import com.cariochi.reflecto.methods.ReflectoMethod;
import java.util.function.BiFunction;

public class DoublesRange implements ConfigFunctionFactory<Doubles.Range> {

    @Override
    public ConfigFunction createSettings(Doubles.Range annotation, ReflectoMethod method) {
        final BiFunction<ObjectoConfig, Context, ObjectoConfig> function = (config, context) -> config.withDoubles(Range.of(annotation.from(), annotation.to()));
        return new ConfigFunction(method.returnType(), annotation.field(), function);
    }
}
