package com.cariochi.objecto.utils;

import com.cariochi.objecto.Fields;
import com.cariochi.objecto.Fields.Datafaker;
import com.cariochi.objecto.Settings;
import com.cariochi.objecto.Settings.Arrays;
import com.cariochi.objecto.Settings.BigDecimals;
import com.cariochi.objecto.Settings.Bytes;
import com.cariochi.objecto.Settings.Chars;
import com.cariochi.objecto.Settings.Collections;
import com.cariochi.objecto.Settings.Dates;
import com.cariochi.objecto.Settings.Doubles;
import com.cariochi.objecto.Settings.Floats;
import com.cariochi.objecto.Settings.Integers;
import com.cariochi.objecto.Settings.Longs;
import com.cariochi.objecto.Settings.Maps;
import com.cariochi.objecto.Settings.MaxDepth;
import com.cariochi.objecto.Settings.MaxRecursionDepth;
import com.cariochi.objecto.Settings.Shorts;
import com.cariochi.objecto.Settings.Strings;
import com.cariochi.objecto.Settings.Strings.Length;
import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.model.FieldSettings;
import com.cariochi.objecto.settings.ObjectoSettings;
import com.cariochi.objecto.settings.Range;
import com.cariochi.reflecto.base.ReflectoAnnotations;
import com.cariochi.reflecto.methods.ReflectoMethod;
import com.cariochi.reflecto.types.ReflectoType;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

@UtilityClass
public class SettingsUtils {

    public static List<UnaryOperator<ObjectoSettings>> getMethodSettings(ReflectoMethod method) {
        final ReflectoType reflectoType = method.declaringType();
        final List<Annotation> annotations = Stream.of(List.of(reflectoType), reflectoType.allInterfaces(), reflectoType.allSuperTypes())
                .flatMap(List::stream)
                .map(ReflectoType::annotations)
                .flatMap(ReflectoAnnotations::stream)
                .collect(toList());
        annotations.addAll(method.annotations().list());
        return getSettings(annotations);
    }

    private static List<UnaryOperator<ObjectoSettings>> getSettings(List<Annotation> annotations) {
        return Stream.of(
                        findAnnotation(annotations, Settings.MaxDepth.class).map(SettingsUtils::maxDepth),
                        findAnnotation(annotations, Settings.MaxRecursionDepth.class).map(SettingsUtils::maxRecursionDepth),
                        findAnnotation(annotations, Settings.Longs.Range.class).map(SettingsUtils::longsRange),
                        findAnnotation(annotations, Settings.Integers.Range.class).map(SettingsUtils::integersRange),
                        findAnnotation(annotations, Settings.Shorts.Range.class).map(SettingsUtils::shortsRange),
                        findAnnotation(annotations, Settings.Bytes.Range.class).map(SettingsUtils::bytesRange),
                        findAnnotation(annotations, Settings.Chars.Range.class).map(SettingsUtils::charsRange),
                        findAnnotation(annotations, Settings.BigDecimals.Range.class).map(SettingsUtils::bigDecimalsRange),
                        findAnnotation(annotations, Settings.BigDecimals.Scale.class).map(SettingsUtils::bigDecimalsScale),
                        findAnnotation(annotations, Settings.Doubles.Range.class).map(SettingsUtils::doublesRange),
                        findAnnotation(annotations, Settings.Floats.Range.class).map(SettingsUtils::floatsRange),
                        findAnnotation(annotations, Settings.Dates.Range.class).map(SettingsUtils::datesRange),
                        findAnnotation(annotations, Settings.Collections.Size.class).map(SettingsUtils::collectionsSize),
                        findAnnotation(annotations, Settings.Collections.Size.Range.class).map(SettingsUtils::collectionsSizeRange),
                        findAnnotation(annotations, Settings.Arrays.Size.class).map(SettingsUtils::arraysSize),
                        findAnnotation(annotations, Settings.Arrays.Size.Range.class).map(SettingsUtils::arraysSizeRange),
                        findAnnotation(annotations, Settings.Maps.Size.class).map(SettingsUtils::mapsSize),
                        findAnnotation(annotations, Settings.Maps.Size.Range.class).map(SettingsUtils::mapsSizeRange),
                        findAnnotation(annotations, Settings.Strings.Length.class).map(SettingsUtils::stringsLength),
                        findAnnotation(annotations, Settings.Strings.Length.Range.class).map(SettingsUtils::stringsLengthRange),
                        findAnnotation(annotations, Settings.Strings.Parameters.class).map(SettingsUtils::stringsParameters),
                        findAnnotation(annotations, Settings.Datafaker.Locale.class).map(SettingsUtils::datafakerLocale),
                        findAnnotation(annotations, Settings.Datafaker.Method.class).map(SettingsUtils::datafakerMethod),
                        findAnnotation(annotations, Settings.Nullable.class).map(SettingsUtils::nullable)
                )
                .flatMap(identity())
                .toList();
    }

    public static List<FieldSettings> getFieldSettings(ReflectoMethod method) {
        final List<FieldSettings> fieldSettings = new ArrayList<>();
        concat(
                method.annotations().find(Fields.Datafaker.Repeatable.class).stream().map(Fields.Datafaker.Repeatable::value).flatMap(Stream::of),
                method.annotations().find(Fields.Datafaker.class).stream()
        )
                .map(annotation -> new FieldSettings(method.returnType(), annotation.field(), fieldDatafaker(annotation)))
                .forEach(fieldSettings::add);

        concat(
                method.annotations().find(Fields.Nullable.Repeatable.class).stream().map(Fields.Nullable.Repeatable::value).flatMap(Stream::of),
                method.annotations().find(Fields.Nullable.class).stream()
        )
                .map(annotation -> new FieldSettings(method.returnType(), annotation.field(), fieldNullable(annotation)))
                .forEach(fieldSettings::add);

        concat(
                method.annotations().find(Fields.SetNull.Repeatable.class).stream().map(Fields.SetNull.Repeatable::value).flatMap(Stream::of),
                method.annotations().find(Fields.SetNull.class).stream()
        )
                .map(annotation -> new FieldSettings(method.returnType(), annotation.value(), setNull()))
                .forEach(fieldSettings::add);

        concat(
                method.annotations().find(Fields.SetValue.Repeatable.class).stream().map(Fields.SetValue.Repeatable::value).flatMap(Stream::of),
                method.annotations().find(Fields.SetValue.class).stream()
        )
                .map(annotation -> new FieldSettings(method.returnType(), annotation.field(), setValue(annotation)))
                .forEach(fieldSettings::add);

        concat(
                method.annotations().find(Fields.Size.Repeatable.class).stream().map(Fields.Size.Repeatable::value).flatMap(Stream::of),
                method.annotations().find(Fields.Size.class).stream()
        )
                .map(annotation -> new FieldSettings(method.returnType(), annotation.field(), size(annotation)))
                .forEach(fieldSettings::add);

        concat(
                method.annotations().find(Fields.Range.Repeatable.class).stream().map(Fields.Range.Repeatable::value).flatMap(Stream::of),
                method.annotations().find(Fields.Range.class).stream()
        )
                .map(annotation -> new FieldSettings(method.returnType(), annotation.field(), range(annotation)))
                .forEach(fieldSettings::add);

        return fieldSettings;
    }

    private static <A extends Annotation> Stream<A> findAnnotation(List<Annotation> annotations, Class<A> annotationClass) {
        return annotations.stream()
                .filter(annotationClass::isInstance)
                .map(annotationClass::cast);
    }


    public static UnaryOperator<ObjectoSettings> maxDepth(MaxDepth annotation) {
        return settings -> settings.withMaxDepth(annotation.value());
    }

    public static UnaryOperator<ObjectoSettings> maxRecursionDepth(MaxRecursionDepth annotation) {
        return settings -> settings.withMaxRecursionDepth(annotation.value());
    }

    public static UnaryOperator<ObjectoSettings> longsRange(Longs.Range annotation) {
        return settings -> settings.withLongs(Range.of(annotation.from(), annotation.to()));
    }

    public static UnaryOperator<ObjectoSettings> integersRange(Integers.Range annotation) {
        return settings -> settings.withIntegers(Range.of(annotation.from(), annotation.to()));
    }

    public static UnaryOperator<ObjectoSettings> shortsRange(Shorts.Range annotation) {
        return settings -> settings.withShorts(Range.of(annotation.from(), annotation.to()));
    }

    public static UnaryOperator<ObjectoSettings> bytesRange(Bytes.Range annotation) {
        return settings -> settings.withBytes(Range.of(annotation.from(), annotation.to()));
    }

    public static UnaryOperator<ObjectoSettings> charsRange(Chars.Range annotation) {
        return settings -> settings.withChars(Range.of(annotation.from(), annotation.to()));
    }

    public static UnaryOperator<ObjectoSettings> bigDecimalsRange(BigDecimals.Range annotation) {
        return settings -> settings.withBigDecimals(settings.bigDecimals().withFrom(annotation.from()).withTo(annotation.to()));
    }

    public static UnaryOperator<ObjectoSettings> bigDecimalsScale(BigDecimals.Scale annotation) {
        return settings -> settings.withBigDecimals(settings.bigDecimals().withScale(annotation.value()));
    }

    public static UnaryOperator<ObjectoSettings> doublesRange(Doubles.Range annotation) {
        return settings -> settings.withDoubles(Range.of(annotation.from(), annotation.to()));
    }

    public static UnaryOperator<ObjectoSettings> floatsRange(Floats.Range annotation) {
        return settings -> settings.withFloats(Range.of(annotation.from(), annotation.to()));
    }

    public static UnaryOperator<ObjectoSettings> datesRange(Dates.Range annotation) {
        return settings -> settings.withDates(Range.of(
                parseToInstant(annotation.from(), annotation.timezone()),
                parseToInstant(annotation.to(), annotation.timezone())
        ));
    }

    public static UnaryOperator<ObjectoSettings> collectionsSize(Collections.Size annotation) {
        return settings -> {
            final ObjectoSettings.Collections collections = settings.collections();
            return settings.withCollections(
                    collections.withSize(collections.size().withValue(annotation.value()))
            );
        };
    }

    public static UnaryOperator<ObjectoSettings> collectionsSizeRange(Collections.Size.Range annotation) {
        return settings -> {
            final ObjectoSettings.Collections collections = settings.collections();
            return settings.withCollections(
                    collections.withSize(collections.size().withRange(Range.of(annotation.from(), annotation.to())))
            );
        };
    }

    public static UnaryOperator<ObjectoSettings> arraysSize(Arrays.Size annotation) {
        return settings -> {
            final ObjectoSettings.Collections arrays = settings.arrays();
            return settings.withArrays(
                    arrays.withSize(arrays.size().withValue(annotation.value()))
            );
        };
    }

    public static UnaryOperator<ObjectoSettings> arraysSizeRange(Arrays.Size.Range annotation) {
        return settings -> {
            final ObjectoSettings.Collections arrays = settings.arrays();
            return settings.withArrays(
                    arrays.withSize(arrays.size().withRange(Range.of(annotation.from(), annotation.to())))
            );
        };
    }

    public static UnaryOperator<ObjectoSettings> mapsSize(Maps.Size annotation) {
        return settings -> {
            final ObjectoSettings.Collections maps = settings.maps();
            return settings.withMaps(
                    maps.withSize(maps.size().withValue(annotation.value()))
            );
        };
    }

    public static UnaryOperator<ObjectoSettings> mapsSizeRange(Maps.Size.Range annotation) {
        return settings -> {
            final ObjectoSettings.Collections maps = settings.maps();
            return settings.withMaps(
                    maps.withSize(maps.size().withRange(Range.of(annotation.from(), annotation.to())))
            );
        };
    }

    public static UnaryOperator<ObjectoSettings> stringsLength(Length annotation) {
        return settings -> {
            final ObjectoSettings.Strings strings = settings.strings();
            return settings.withStrings(
                    strings.withLength(strings.length().withValue(annotation.value()))
            );
        };
    }

    public static UnaryOperator<ObjectoSettings> stringsLengthRange(Length.Range annotation) {
        return settings -> {
            final ObjectoSettings.Strings strings = settings.strings();
            return settings.withStrings(strings.withLength(strings.length().withRange(Range.of(annotation.from(), annotation.to()))));
        };
    }

    public static UnaryOperator<ObjectoSettings> stringsParameters(Strings.Parameters annotation) {
        return settings -> settings.withStrings(
                settings.strings()
                        .withLetters(annotation.letters())
                        .withNumbers(annotation.digits())
                        .withUppercase(annotation.uppercase())
                        .withFieldNamePrefix(annotation.useFieldNamePrefix())
        );
    }

    public static UnaryOperator<ObjectoSettings> datafakerLocale(Settings.Datafaker.Locale annotation) {
        return settings -> settings.withDatafaker(settings.datafaker().withLocale(annotation.value()));
    }

    public static UnaryOperator<ObjectoSettings> datafakerMethod(Settings.Datafaker.Method annotation) {
        return settings -> settings.withDatafaker(settings.datafaker().withMethod(annotation.value()));
    }

    public static UnaryOperator<ObjectoSettings> nullable(Settings.Nullable annotation) {
        return settings -> settings.withNullable(annotation.value());
    }

    public static BiFunction<ObjectoSettings, Context, ObjectoSettings> fieldDatafaker(Datafaker annotation) {
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

    public static BiFunction<ObjectoSettings, Context, ObjectoSettings> fieldNullable(Fields.Nullable annotation) {
        return (settings, context) -> settings.withNullable(annotation.value());
    }

    public static BiFunction<ObjectoSettings, Context, ObjectoSettings> setNull() {
        return (settings, context) -> settings.withSetNull(true);
    }

    public static BiFunction<ObjectoSettings, Context, ObjectoSettings> setValue(Fields.SetValue annotation) {
        return (settings, context) -> settings.withSetValue(annotation.value());
    }

    public static BiFunction<ObjectoSettings, Context, ObjectoSettings> size(Fields.Size annotation) {
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

    public static BiFunction<ObjectoSettings, Context, ObjectoSettings> range(Fields.Range annotation) {
        return (settings, context) -> {
            if (context.getType().isArray()) {
                final ObjectoSettings.Collections arrays = settings.arrays();
                settings = settings.withArrays(arrays.withSize(arrays.size().withRange(Range.of((int) annotation.from(), (int) annotation.to()))));
            } else if (context.getType().is(Iterable.class)) {
                final ObjectoSettings.Collections collections = settings.collections();
                settings = settings.withCollections(
                        collections.withSize(collections.size().withRange(Range.of((int) annotation.from(), (int) annotation.to()))));
            } else if (context.getType().is(Map.class)) {
                final ObjectoSettings.Collections maps = settings.maps();
                settings = settings.withMaps(maps.withSize(maps.size().withRange(Range.of((int) annotation.from(), (int) annotation.to()))));
            } else if (context.getType().is(String.class)) {
                final ObjectoSettings.Strings strings = settings.strings();
                settings = settings.withStrings(strings.withLength(strings.length().withRange(Range.of((int) annotation.from(), (int) annotation.to()))));
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

    private static Instant parseToInstant(String dateStr, String timezone) {

        try {
            return Instant.parse(dateStr);
        } catch (Exception e) {
        }

        try {
            LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return date.atStartOfDay(ZoneId.of(timezone)).toInstant();
        } catch (Exception e) {
        }

        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return dateTime.toInstant(ZoneOffset.of(timezone));
        } catch (Exception e) {
        }

        return null;
    }
}
