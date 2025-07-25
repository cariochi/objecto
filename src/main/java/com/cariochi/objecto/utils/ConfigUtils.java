package com.cariochi.objecto.utils;

import com.cariochi.objecto.Spec;
import com.cariochi.objecto.config.functions.ArraysSize;
import com.cariochi.objecto.config.functions.ArraysSizeRange;
import com.cariochi.objecto.config.functions.BigDecimalRange;
import com.cariochi.objecto.config.functions.BigDecimalScale;
import com.cariochi.objecto.config.functions.BytesRange;
import com.cariochi.objecto.config.functions.CharsRange;
import com.cariochi.objecto.config.functions.CollectionsSize;
import com.cariochi.objecto.config.functions.CollectionsSizeRange;
import com.cariochi.objecto.config.functions.ConfigFunctionFactory;
import com.cariochi.objecto.config.functions.DatesRange;
import com.cariochi.objecto.config.functions.DoublesRange;
import com.cariochi.objecto.config.functions.Faker;
import com.cariochi.objecto.config.functions.FloatsRange;
import com.cariochi.objecto.config.functions.IntegersRange;
import com.cariochi.objecto.config.functions.LongsRange;
import com.cariochi.objecto.config.functions.MapsSize;
import com.cariochi.objecto.config.functions.MapsSizeRange;
import com.cariochi.objecto.config.functions.MaxDepth;
import com.cariochi.objecto.config.functions.MaxRecursionDepth;
import com.cariochi.objecto.config.functions.Nullable;
import com.cariochi.objecto.config.functions.SetNull;
import com.cariochi.objecto.config.functions.SetValue;
import com.cariochi.objecto.config.functions.ShortsRange;
import com.cariochi.objecto.config.functions.StringsLength;
import com.cariochi.objecto.config.functions.StringsLengthRange;
import com.cariochi.objecto.config.functions.StringsParameters;
import com.cariochi.objecto.generators.model.ConfigFunction;
import com.cariochi.objecto.repeatable.Configs;
import com.cariochi.objecto.repeatable.Fakers;
import com.cariochi.reflecto.base.ReflectoAnnotations;
import com.cariochi.reflecto.methods.ReflectoMethod;
import com.cariochi.reflecto.types.ReflectoType;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;

import static java.util.Map.entry;
import static java.util.stream.Collectors.toList;

@UtilityClass
public class ConfigUtils {

    private static final Map<Class<? extends Annotation>, ConfigFunctionFactory<? extends Annotation>> configFactories = Map.ofEntries(
            entry(Spec.MaxDepth.class, new MaxDepth()),
            entry(Spec.MaxRecursionDepth.class, new MaxRecursionDepth()),
            entry(Spec.Nullable.class, new Nullable()),
            entry(Spec.Longs.Range.class, new LongsRange()),
            entry(Spec.Integers.Range.class, new IntegersRange()),
            entry(Spec.Shorts.Range.class, new ShortsRange()),
            entry(Spec.Bytes.Range.class, new BytesRange()),
            entry(Spec.Chars.Range.class, new CharsRange()),
            entry(Spec.BigDecimals.Range.class, new BigDecimalRange()),
            entry(Spec.BigDecimals.Scale.class, new BigDecimalScale()),
            entry(Spec.Doubles.Range.class, new DoublesRange()),
            entry(Spec.Floats.Range.class, new FloatsRange()),
            entry(Spec.Dates.Range.class, new DatesRange()),
            entry(Spec.Collections.Size.class, new CollectionsSize()),
            entry(Spec.Collections.Size.Range.class, new CollectionsSizeRange()),
            entry(Spec.Arrays.Size.class, new ArraysSize()),
            entry(Spec.Arrays.Size.Range.class, new ArraysSizeRange()),
            entry(Spec.Maps.Size.class, new MapsSize()),
            entry(Spec.Maps.Size.Range.class, new MapsSizeRange()),
            entry(Spec.Strings.Length.class, new StringsLength()),
            entry(Spec.Strings.Length.Range.class, new StringsLengthRange()),
            entry(Spec.Strings.Parameters.class, new StringsParameters()),
            entry(com.cariochi.objecto.Faker.class, new Faker()),
            entry(Spec.SetNull.class, new SetNull()),
            entry(Spec.SetValue.class, new SetValue())
    );

    private static final Map<Class<? extends Annotation>, Function<Annotation, Annotation[]>> repeteableFunctions = Map.ofEntries(
            entry(Configs.MaxDepth.class, annotation -> ((Configs.MaxDepth) annotation).value()),
            entry(Configs.MaxRecursionDepth.class, annotation -> ((Configs.MaxRecursionDepth) annotation).value()),
            entry(Configs.Nullable.class, annotation -> ((Configs.Nullable) annotation).value()),
            entry(Configs.Longs.Range.class, annotation -> ((Configs.Longs.Range) annotation).value()),
            entry(Configs.Integers.Range.class, annotation -> ((Configs.Integers.Range) annotation).value()),
            entry(Configs.Shorts.Range.class, annotation -> ((Configs.Shorts.Range) annotation).value()),
            entry(Configs.Bytes.Range.class, annotation -> ((Configs.Bytes.Range) annotation).value()),
            entry(Configs.Chars.Range.class, annotation -> ((Configs.Chars.Range) annotation).value()),
            entry(Configs.BigDecimals.Range.class, annotation -> ((Configs.BigDecimals.Range) annotation).value()),
            entry(Configs.BigDecimals.Scale.class, annotation -> ((Configs.BigDecimals.Scale) annotation).value()),
            entry(Configs.Doubles.Range.class, annotation -> ((Configs.Doubles.Range) annotation).value()),
            entry(Configs.Floats.Range.class, annotation -> ((Configs.Floats.Range) annotation).value()),
            entry(Configs.Dates.Range.class, annotation -> ((Configs.Dates.Range) annotation).value()),
            entry(Configs.Collections.Size.class, annotation -> ((Configs.Collections.Size) annotation).value()),
            entry(Configs.Collections.Size.Range.class, annotation -> ((Configs.Collections.Size.Range) annotation).value()),
            entry(Configs.Arrays.Size.class, annotation -> ((Configs.Arrays.Size) annotation).value()),
            entry(Configs.Arrays.Size.Range.class, annotation -> ((Configs.Arrays.Size.Range) annotation).value()),
            entry(Configs.Maps.Size.class, annotation -> ((Configs.Maps.Size) annotation).value()),
            entry(Configs.Maps.Size.Range.class, annotation -> ((Configs.Maps.Size.Range) annotation).value()),
            entry(Configs.Strings.Length.class, annotation -> ((Configs.Strings.Length) annotation).value()),
            entry(Configs.Strings.Length.Range.class, annotation -> ((Configs.Strings.Length.Range) annotation).value()),
            entry(Configs.Strings.Parameters.class, annotation -> ((Configs.Strings.Parameters) annotation).value()),
            entry(Fakers.class, annotation -> ((Fakers) annotation).value()),
            entry(Configs.SetNull.class, annotation -> ((Configs.SetNull) annotation).value()),
            entry(Configs.SetValue.class, annotation -> ((Configs.SetValue) annotation).value())
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
