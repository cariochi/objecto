package com.cariochi.objecto.generators.model;

import com.cariochi.objecto.config.ObjectoConfig;
import com.cariochi.objecto.generators.Context;
import com.cariochi.reflecto.types.ReflectoType;
import java.util.function.BiFunction;
import lombok.Value;

@Value
public class ConfigFunction {

    ReflectoType type;
    String field;
    BiFunction<ObjectoConfig, Context, ObjectoConfig> function;

}
