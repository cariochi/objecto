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
public class FieldsDatafaker implements FieldFunction {

    @Override
    public boolean isApplicable(Annotation annotation) {
        return annotation instanceof Fields.Datafaker || annotation instanceof Fields.Datafaker.Repeatable;
    }

    @Override
    public List<FieldSettings> getFunctions(ReflectoMethod method, Annotation annotation) {
        if (annotation instanceof Fields.Datafaker.Repeatable multiple) {
            return Stream.of(multiple.value()).flatMap(single -> getFunctions(method, single).stream()).toList();
        } else if (annotation instanceof Fields.Datafaker single) {
            return List.of(new FieldSettings(method.returnType(), single.field(), apply(single)));
        }
        return null;
    }

    public static BiFunction<ObjectoSettings, Context, ObjectoSettings> apply(Fields.Datafaker annotation) {
        return (settings, context) -> {
            if (!annotation.method().isEmpty()) {
                settings = settings.withDatafaker(settings.datafaker().withMethod(annotation.method()));
            }
            if (!annotation.locale().isEmpty()) {
                settings = settings.withDatafaker(settings.datafaker().withLocale(annotation.locale()));
            }
            return settings;
        };
    }
}
