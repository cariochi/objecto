package com.cariochi.objecto.config.functions;

import com.cariochi.objecto.Spec.Collections.Size;
import com.cariochi.objecto.config.ObjectoConfig;
import com.cariochi.objecto.config.ObjectoConfig.Collections;
import com.cariochi.objecto.config.Range;
import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.model.ConfigFunction;
import com.cariochi.reflecto.methods.ReflectoMethod;
import java.util.function.BiFunction;

public class CollectionsSizeRange implements ConfigFunctionFactory<Size.Range> {

    @Override
    public ConfigFunction createSettings(Size.Range annotation, ReflectoMethod method) {
        final BiFunction<ObjectoConfig, Context, ObjectoConfig> function = (config, context) -> {
            final Collections collections = config.collections();
            return config.withCollections(
                    collections.withSize(collections.size().withRange(Range.of(annotation.from(), annotation.to())))
            );
        };
        return new ConfigFunction(method.returnType(), annotation.field(), function);
    }
}
