package com.cariochi.objecto.utils;

import com.cariochi.objecto.Settings;
import com.cariochi.objecto.settings.ObjectoSettings;
import com.cariochi.objecto.settings.functions.settings.ArraysSize;
import com.cariochi.objecto.settings.functions.settings.ArraysSizeRange;
import com.cariochi.objecto.settings.functions.settings.BigDecimalRange;
import com.cariochi.objecto.settings.functions.settings.BigDecimalScale;
import com.cariochi.objecto.settings.functions.settings.BytesRange;
import com.cariochi.objecto.settings.functions.settings.CharsRange;
import com.cariochi.objecto.settings.functions.settings.CollectionsSize;
import com.cariochi.objecto.settings.functions.settings.CollectionsSizeRange;
import com.cariochi.objecto.settings.functions.settings.DatafakerLocale;
import com.cariochi.objecto.settings.functions.settings.DatafakerMethod;
import com.cariochi.objecto.settings.functions.settings.DatesRange;
import com.cariochi.objecto.settings.functions.settings.DoublesRange;
import com.cariochi.objecto.settings.functions.settings.FloatsRange;
import com.cariochi.objecto.settings.functions.settings.IntegersRange;
import com.cariochi.objecto.settings.functions.settings.LongsRange;
import com.cariochi.objecto.settings.functions.settings.MapsSize;
import com.cariochi.objecto.settings.functions.settings.MapsSizeRange;
import com.cariochi.objecto.settings.functions.settings.MaxDepth;
import com.cariochi.objecto.settings.functions.settings.MaxRecursionDepth;
import com.cariochi.objecto.settings.functions.settings.Nullable;
import com.cariochi.objecto.settings.functions.settings.SettingsFunction;
import com.cariochi.objecto.settings.functions.settings.ShortsRange;
import com.cariochi.objecto.settings.functions.settings.StringsLength;
import com.cariochi.objecto.settings.functions.settings.StringsLengthRange;
import com.cariochi.objecto.settings.functions.settings.StringsParameters;
import com.cariochi.reflecto.base.ReflectoAnnotations;
import com.cariochi.reflecto.methods.ReflectoMethod;
import com.cariochi.reflecto.types.ReflectoType;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;

import static java.util.Map.entry;
import static java.util.stream.Collectors.toList;

@UtilityClass
public class SettingsUtils {

    private static final Map<Class<? extends Annotation>, SettingsFunction<? extends Annotation>> settingsFunctions = Map.ofEntries(
            entry(Settings.MaxDepth.class, new MaxDepth()),
            entry(Settings.MaxRecursionDepth.class, new MaxRecursionDepth()),
            entry(Settings.Nullable.class, new Nullable()),
            entry(Settings.Longs.Range.class, new LongsRange()),
            entry(Settings.Integers.Range.class, new IntegersRange()),
            entry(Settings.Shorts.Range.class, new ShortsRange()),
            entry(Settings.Bytes.Range.class, new BytesRange()),
            entry(Settings.Chars.Range.class, new CharsRange()),
            entry(Settings.BigDecimals.Range.class, new BigDecimalRange()),
            entry(Settings.BigDecimals.Scale.class, new BigDecimalScale()),
            entry(Settings.Doubles.Range.class, new DoublesRange()),
            entry(Settings.Floats.Range.class, new FloatsRange()),
            entry(Settings.Dates.Range.class, new DatesRange()),
            entry(Settings.Collections.Size.class, new CollectionsSize()),
            entry(Settings.Collections.Size.Range.class, new CollectionsSizeRange()),
            entry(Settings.Arrays.Size.class, new ArraysSize()),
            entry(Settings.Arrays.Size.Range.class, new ArraysSizeRange()),
            entry(Settings.Maps.Size.class, new MapsSize()),
            entry(Settings.Maps.Size.Range.class, new MapsSizeRange()),
            entry(Settings.Strings.Length.class, new StringsLength()),
            entry(Settings.Strings.Length.Range.class, new StringsLengthRange()),
            entry(Settings.Strings.Parameters.class, new StringsParameters()),
            entry(Settings.Datafaker.Locale.class, new DatafakerLocale()),
            entry(Settings.Datafaker.Method.class, new DatafakerMethod())
    );

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

        return annotations.stream()
                .filter(annotation -> settingsFunctions.containsKey(annotation.annotationType()))
                .map(annotation -> {
                    final SettingsFunction<Annotation> function = (SettingsFunction<Annotation>) settingsFunctions.get(annotation.annotationType());
                    return function.apply(annotation);
                })
                .collect(toList());

    }

}
