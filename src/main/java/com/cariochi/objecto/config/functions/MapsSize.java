package com.cariochi.objecto.config.functions;

import com.cariochi.objecto.Spec.Maps.Size;
import com.cariochi.objecto.config.ObjectoConfig;
import com.cariochi.objecto.config.ObjectoConfig.Collections;
import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.model.ConfigFunction;
import com.cariochi.reflecto.methods.ReflectoMethod;
import java.util.function.BiFunction;

public class MapsSize implements ConfigFunctionFactory<Size> {

    @Override
    public ConfigFunction createSettings(Size annotation, ReflectoMethod method) {
        final BiFunction<ObjectoConfig, Context, ObjectoConfig> function = (config, context) -> {
            final Collections maps = config.maps();
            return config.withMaps(maps.withSize(maps.size().withValue(annotation.value())));
        };
        return new ConfigFunction(method.returnType(), annotation.field(), function);
    }
}
