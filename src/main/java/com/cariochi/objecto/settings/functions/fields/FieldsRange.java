package com.cariochi.objecto.settings.functions.fields;

import com.cariochi.objecto.Fields;
import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.model.FieldSettings;
import com.cariochi.objecto.settings.ObjectoSettings;
import com.cariochi.objecto.settings.Range;
import com.cariochi.reflecto.methods.ReflectoMethod;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FieldsRange implements FieldFunction {

    @Override
    public boolean isApplicable(Annotation annotation) {
        return annotation instanceof Fields.Range || annotation instanceof Fields.Range.Repeatable;
    }

    @Override
    public List<FieldSettings> getFunctions(ReflectoMethod method, Annotation annotation) {
        if (annotation instanceof Fields.Range.Repeatable multiple) {
            return Stream.of(multiple.value()).flatMap(single -> getFunctions(method, single).stream()).toList();
        } else if (annotation instanceof Fields.Range single) {
            return List.of(new FieldSettings(method.returnType(), single.field(), apply(single)));
        }
        return null;
    }

    public static BiFunction<ObjectoSettings, Context, ObjectoSettings> apply(Fields.Range annotation) {
        return (settings, context) -> {
            if (context.getType().isArray()) {
                final ObjectoSettings.Collections arrays = settings.arrays();
                settings = settings.withArrays(
                        arrays.withSize(arrays.size().withRange(Range.of((int) annotation.from(), (int) annotation.to()))));
            } else if (context.getType().is(Iterable.class)) {
                final ObjectoSettings.Collections collections = settings.collections();
                settings = settings.withCollections(
                        collections.withSize(collections.size().withRange(Range.of((int) annotation.from(), (int) annotation.to()))));
            } else if (context.getType().is(Map.class)) {
                final ObjectoSettings.Collections maps = settings.maps();
                settings = settings.withMaps(
                        maps.withSize(maps.size().withRange(Range.of((int) annotation.from(), (int) annotation.to()))));
            } else if (context.getType().is(String.class)) {
                final ObjectoSettings.Strings strings = settings.strings();
                settings = settings.withStrings(
                        strings.withLength(strings.length().withRange(Range.of((int) annotation.from(), (int) annotation.to()))));
            } else if (context.getType().is(Long.class)) {
                settings = settings.withLongs(Range.of((long) annotation.from(), (long) annotation.to()));
            } else if (context.getType().is(Integer.class)) {
                settings = settings.withIntegers(Range.of((int) annotation.from(), (int) annotation.to()));
            } else if (context.getType().is(Short.class)) {
                settings = settings.withShorts(Range.of((short) annotation.from(), (short) annotation.to()));
            } else if (context.getType().is(Byte.class)) {
                settings = settings.withBytes(Range.of((byte) annotation.from(), (byte) annotation.to()));
            } else if (context.getType().is(Character.class)) {
                settings = settings.withChars(Range.of((char) annotation.from(), (char) annotation.to()));
            } else if (context.getType().is(BigDecimal.class)) {
                settings = settings.withBigDecimals(settings.bigDecimals().withFrom(annotation.from()).withTo(annotation.to()));
            } else if (context.getType().is(Double.class)) {
                settings = settings.withDoubles(Range.of(annotation.from(), annotation.to()));
            } else if (context.getType().is(Float.class)) {
                settings = settings.withFloats(Range.of((float) annotation.from(), (float) annotation.to()));
            }
            return settings;
        };
    }
}
