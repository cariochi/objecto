package com.cariochi.objecto.config.functions;

import com.cariochi.objecto.Spec.Bytes;
import com.cariochi.objecto.config.ObjectoConfig;
import com.cariochi.objecto.config.Range;
import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.model.ConfigFunction;
import com.cariochi.reflecto.methods.ReflectoMethod;
import java.util.function.BiFunction;

public class BytesRange implements ConfigFunctionFactory<Bytes.Range> {

    @Override
    public ConfigFunction createSettings(Bytes.Range annotation, ReflectoMethod method) {
        final BiFunction<ObjectoConfig, Context, ObjectoConfig> function = (config, context) -> config.withBytes(Range.of(annotation.from(), annotation.to()));
        return new ConfigFunction(method.returnType(), annotation.field(), function);
    }
}
