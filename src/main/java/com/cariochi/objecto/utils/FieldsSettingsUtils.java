package com.cariochi.objecto.utils;

import com.cariochi.objecto.generators.model.FieldSettings;
import com.cariochi.objecto.settings.functions.fields.FieldFunction;
import com.cariochi.objecto.settings.functions.fields.FieldsDatafaker;
import com.cariochi.objecto.settings.functions.fields.FieldsNullable;
import com.cariochi.objecto.settings.functions.fields.FieldsRange;
import com.cariochi.objecto.settings.functions.fields.FieldsSetNull;
import com.cariochi.objecto.settings.functions.fields.FieldsSetValue;
import com.cariochi.objecto.settings.functions.fields.FieldsSize;
import com.cariochi.reflecto.methods.ReflectoMethod;
import java.lang.annotation.Annotation;
import java.util.List;
import lombok.experimental.UtilityClass;

import static java.util.stream.Collectors.toList;

@UtilityClass
public class FieldsSettingsUtils {

    private static final List<FieldFunction> fieldsFunctions = List.of(
            new FieldsSize(),
            new FieldsRange(),
            new FieldsSetValue(),
            new FieldsSetNull(),
            new FieldsNullable(),
            new FieldsDatafaker()
    );

    public static List<FieldSettings> getFieldSettings(ReflectoMethod method) {
        return method.annotations().stream()
                .flatMap(a -> getFieldsFunctions(method, a).stream())
                .collect(toList());
    }

    private static List<FieldSettings> getFieldsFunctions(ReflectoMethod method, Annotation annotation) {
        return fieldsFunctions.stream()
                .filter(f -> f.isApplicable(annotation))
                .flatMap(f -> f.getFunctions(method, annotation).stream())
                .toList();
    }
}
