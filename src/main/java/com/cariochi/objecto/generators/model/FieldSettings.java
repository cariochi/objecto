package com.cariochi.objecto.generators.model;

import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.settings.ObjectoSettings;
import com.cariochi.reflecto.types.ReflectoType;
import java.util.function.BiFunction;
import lombok.Value;

@Value
public class FieldSettings {

    ReflectoType type;
    String field;
    BiFunction<ObjectoSettings, Context, ObjectoSettings> settings;

}
