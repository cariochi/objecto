package com.cariochi.objecto.settings.functions.fields;

import com.cariochi.objecto.Fields;
import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.model.FieldSettings;
import com.cariochi.objecto.settings.ObjectoSettings;
import com.cariochi.reflecto.methods.ReflectoMethod;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FieldsSize implements FieldFunction {

    @Override
    public boolean isApplicable(Annotation annotation) {
        return annotation instanceof Fields.Size || annotation instanceof Fields.Size.Repeatable;
    }

    @Override
    public List<FieldSettings> getFunctions(ReflectoMethod method, Annotation annotation) {
        if (annotation instanceof Fields.Size.Repeatable multiple) {
            return Stream.of(multiple.value()).flatMap(single -> getFunctions(method, single).stream()).toList();
        } else if (annotation instanceof Fields.Size single) {
            return List.of(new FieldSettings(method.returnType(), single.field(), apply(single)));
        }
        return null;
    }

    public static BiFunction<ObjectoSettings, Context, ObjectoSettings> apply(Fields.Size annotation) {
        return (settings, context) -> {
            if (context.getType().isArray()) {
                final ObjectoSettings.Collections arrays = settings.arrays();
                settings = settings.withArrays(arrays.withSize(arrays.size().withValue(annotation.value())));
            } else if (context.getType().is(Iterable.class)) {
                final ObjectoSettings.Collections collections = settings.collections();
                settings = settings.withCollections(collections.withSize(collections.size().withValue(annotation.value())));
            } else if (context.getType().is(Map.class)) {
                final ObjectoSettings.Collections maps = settings.maps();
                settings = settings.withMaps(maps.withSize(maps.size().withValue(annotation.value())));
            } else if (context.getType().is(String.class)) {
                final ObjectoSettings.Strings strings = settings.strings();
                settings = settings.withStrings(strings.withLength(strings.length().withValue(annotation.value())));
            }
            return settings;
        };
    }
}
