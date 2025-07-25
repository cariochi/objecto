package com.cariochi.objecto.config.functions;

import com.cariochi.objecto.Spec.Strings.Parameters;
import com.cariochi.objecto.config.ObjectoConfig;
import com.cariochi.objecto.config.ObjectoConfig.Strings;
import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.model.ConfigFunction;
import com.cariochi.reflecto.methods.ReflectoMethod;
import java.util.function.BiFunction;

public class StringsParameters implements ConfigFunctionFactory<Parameters> {

    @Override
    public ConfigFunction createSettings(Parameters annotation, ReflectoMethod method) {
        final BiFunction<ObjectoConfig, Context, ObjectoConfig> function = (config, context) -> {
            final Strings strings = config.strings()
                    .withChars(annotation.chars())
                    .withFrom(annotation.from()).withTo(annotation.to())
                    .withFieldNamePrefix(annotation.useFieldNamePrefix());
            return config.withStrings(strings);
        };
        return new ConfigFunction(method.returnType(), annotation.field(), function);
    }
}
