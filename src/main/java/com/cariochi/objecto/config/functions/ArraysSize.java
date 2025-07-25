package com.cariochi.objecto.config.functions;

import com.cariochi.objecto.Spec.Arrays.Size;
import com.cariochi.objecto.config.ObjectoConfig;
import com.cariochi.objecto.config.ObjectoConfig.Collections;
import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.model.ConfigFunction;
import com.cariochi.reflecto.methods.ReflectoMethod;
import java.util.function.BiFunction;

public class ArraysSize implements ConfigFunctionFactory<Size> {

    @Override
    public ConfigFunction createSettings(Size annotation, ReflectoMethod method) {
        final BiFunction<ObjectoConfig, Context, ObjectoConfig> function = (config, context) -> {
            final Collections arrays = config.arrays();
            return config.withArrays(arrays.withSize(arrays.size().withValue(annotation.value())));
        };
        return new ConfigFunction(method.returnType(), annotation.field(), function);
    }
}
