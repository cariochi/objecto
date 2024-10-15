package com.cariochi.objecto.generators.model;

import com.cariochi.objecto.settings.ObjectoSettings;
import com.cariochi.reflecto.types.ReflectoType;
import java.util.function.UnaryOperator;
import lombok.Value;

@Value
public class FieldSettings {

    ReflectoType type;
    String field;
    UnaryOperator<ObjectoSettings> settings;

}
