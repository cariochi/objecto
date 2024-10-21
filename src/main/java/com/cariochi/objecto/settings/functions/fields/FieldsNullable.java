package com.cariochi.objecto.settings.functions.fields;

import com.cariochi.objecto.Fields;
import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.model.FieldSettings;
import com.cariochi.objecto.settings.ObjectoSettings;
import com.cariochi.reflecto.methods.ReflectoMethod;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FieldsNullable implements FieldFunction {

    @Override
    public boolean isApplicable(Annotation annotation) {
        return annotation instanceof Fields.Nullable || annotation instanceof Fields.Nullable.Repeatable;
    }

    @Override
    public List<FieldSettings> getFunctions(ReflectoMethod method, Annotation annotation) {
        if (annotation instanceof Fields.Nullable.Repeatable multiple) {
            return Stream.of(multiple.value()).flatMap(single -> getFunctions(method, single).stream()).toList();
        } else if (annotation instanceof Fields.Nullable single) {
            return List.of(new FieldSettings(method.returnType(), single.field(), apply(single)));
        }
        return null;
    }

    public static BiFunction<ObjectoSettings, Context, ObjectoSettings> apply(Fields.Nullable annotation) {
        return (settings, context) -> settings.withNullable(annotation.value());
    }
}
