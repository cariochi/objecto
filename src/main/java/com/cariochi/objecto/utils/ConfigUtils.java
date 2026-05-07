package com.cariochi.objecto.utils;

import com.cariochi.objecto.Datafaker;
import com.cariochi.objecto.Generate;
import com.cariochi.objecto.config.functions.*;
import com.cariochi.objecto.generators.model.ConfigFunction;
import com.cariochi.objecto.repeatable.Repeatable;
import com.cariochi.reflecto.base.ReflectoAnnotations;
import com.cariochi.reflecto.methods.ReflectoMethod;
import com.cariochi.reflecto.types.ReflectoType;
import lombok.experimental.UtilityClass;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Map.entry;
import static java.util.stream.Collectors.toList;

@UtilityClass
public class ConfigUtils {

    private static final Map<Class<? extends Annotation>, ConfigFunctionFactory<? extends Annotation>> configFactories = Map.ofEntries(
            entry(Generate.MaxDepth.class, new MaxDepth()),
            entry(Generate.MaxRecursionDepth.class, new MaxRecursionDepth()),
            entry(Generate.Nullable.class, new Nullable()),
            entry(Generate.Longs.Range.class, new LongsRange()),
            entry(Generate.Integers.Range.class, new IntegersRange()),
            entry(Generate.Shorts.Range.class, new ShortsRange()),
            entry(Generate.Bytes.Range.class, new BytesRange()),
            entry(Generate.Chars.Range.class, new CharsRange()),
            entry(Generate.BigDecimals.Range.class, new BigDecimalRange()),
            entry(Generate.BigDecimals.Scale.class, new BigDecimalScale()),
            entry(Generate.Doubles.Range.class, new DoublesRange()),
            entry(Generate.Floats.Range.class, new FloatsRange()),
            entry(Generate.Dates.Range.class, new DatesRange()),
            entry(Generate.Collections.Size.class, new CollectionsSize()),
            entry(Generate.Collections.Size.Range.class, new CollectionsSizeRange()),
            entry(Generate.Arrays.Size.class, new ArraysSize()),
            entry(Generate.Arrays.Size.Range.class, new ArraysSizeRange()),
            entry(Generate.Maps.Size.class, new MapsSize()),
            entry(Generate.Maps.Size.Range.class, new MapsSizeRange()),
            entry(Generate.Strings.Length.class, new StringsLength()),
            entry(Generate.Strings.Length.Range.class, new StringsLengthRange()),
            entry(Generate.Strings.Characters.class, new StringsParameters()),
            entry(Datafaker.class, new Faker()),
            entry(Generate.SetNull.class, new SetNull()),
            entry(Generate.SetValue.class, new SetValue())
    );

    private static final Map<Class<? extends Annotation>, Function<Annotation, Annotation[]>> repeteableFunctions = Map.ofEntries(
            entry(Repeatable.MaxDepth.class, annotation -> ((Repeatable.MaxDepth) annotation).value()),
            entry(Repeatable.MaxRecursionDepth.class, annotation -> ((Repeatable.MaxRecursionDepth) annotation).value()),
            entry(Repeatable.Nullable.class, annotation -> ((Repeatable.Nullable) annotation).value()),
            entry(Repeatable.Longs.Range.class, annotation -> ((Repeatable.Longs.Range) annotation).value()),
            entry(Repeatable.Integers.Range.class, annotation -> ((Repeatable.Integers.Range) annotation).value()),
            entry(Repeatable.Shorts.Range.class, annotation -> ((Repeatable.Shorts.Range) annotation).value()),
            entry(Repeatable.Bytes.Range.class, annotation -> ((Repeatable.Bytes.Range) annotation).value()),
            entry(Repeatable.Chars.Range.class, annotation -> ((Repeatable.Chars.Range) annotation).value()),
            entry(Repeatable.BigDecimals.Range.class, annotation -> ((Repeatable.BigDecimals.Range) annotation).value()),
            entry(Repeatable.BigDecimals.Scale.class, annotation -> ((Repeatable.BigDecimals.Scale) annotation).value()),
            entry(Repeatable.Doubles.Range.class, annotation -> ((Repeatable.Doubles.Range) annotation).value()),
            entry(Repeatable.Floats.Range.class, annotation -> ((Repeatable.Floats.Range) annotation).value()),
            entry(Repeatable.Dates.Range.class, annotation -> ((Repeatable.Dates.Range) annotation).value()),
            entry(Repeatable.Collections.Size.class, annotation -> ((Repeatable.Collections.Size) annotation).value()),
            entry(Repeatable.Collections.Size.Range.class, annotation -> ((Repeatable.Collections.Size.Range) annotation).value()),
            entry(Repeatable.Arrays.Size.class, annotation -> ((Repeatable.Arrays.Size) annotation).value()),
            entry(Repeatable.Arrays.Size.Range.class, annotation -> ((Repeatable.Arrays.Size.Range) annotation).value()),
            entry(Repeatable.Maps.Size.class, annotation -> ((Repeatable.Maps.Size) annotation).value()),
            entry(Repeatable.Maps.Size.Range.class, annotation -> ((Repeatable.Maps.Size.Range) annotation).value()),
            entry(Repeatable.Strings.Length.class, annotation -> ((Repeatable.Strings.Length) annotation).value()),
            entry(Repeatable.Strings.Length.Range.class, annotation -> ((Repeatable.Strings.Length.Range) annotation).value()),
            entry(Repeatable.Strings.Characters.class, annotation -> ((Repeatable.Strings.Characters) annotation).value()),
            entry(Repeatable.Datafakers.class, annotation -> ((Repeatable.Datafakers) annotation).value()),
            entry(Repeatable.SetNull.class, annotation -> ((Repeatable.SetNull) annotation).value()),
            entry(Repeatable.SetValue.class, annotation -> ((Repeatable.SetValue) annotation).value())
    );

    public static List<ConfigFunction> getMethodSettings(ReflectoMethod method) {
        final ReflectoType reflectoType = method.declaringType();
        final List<Annotation> annotations = Stream.of(List.of(reflectoType), reflectoType.allInterfaces(), reflectoType.allSuperTypes())
                .flatMap(List::stream)
                .map(ReflectoType::annotations)
                .flatMap(ReflectoAnnotations::stream)
                .collect(toList());
        annotations.addAll(method.annotations().list());
        return getSettings(annotations, method);
    }

    private static List<ConfigFunction> getSettings(List<Annotation> annotations, ReflectoMethod method) {
        return annotations.stream()
                .flatMap(annotation -> {
                    if (configFactories.containsKey(annotation.annotationType())) {
                        return Stream.of(annotation);
                    } else if (repeteableFunctions.containsKey(annotation.annotationType())) {
                        return Stream.of(repeteableFunctions.get(annotation.annotationType()).apply(annotation));
                    }
                    return Stream.empty();
                })
                .map(annotation -> {
                    final ConfigFunctionFactory<Annotation> function = (ConfigFunctionFactory<Annotation>) configFactories.get(annotation.annotationType());
                    return function.createSettings(annotation, method);
                })
                .collect(toList());

    }


}
