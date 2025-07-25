package com.cariochi.objecto.config.functions;

import com.cariochi.objecto.Spec.Dates;
import com.cariochi.objecto.config.ObjectoConfig;
import com.cariochi.objecto.config.Range;
import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.model.ConfigFunction;
import com.cariochi.reflecto.methods.ReflectoMethod;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.function.BiFunction;

public class DatesRange implements ConfigFunctionFactory<Dates.Range> {

    @Override
    public ConfigFunction createSettings(Dates.Range annotation, ReflectoMethod method) {
        final BiFunction<ObjectoConfig, Context, ObjectoConfig> function = (config, context) -> config.withDates(Range.of(
                parseToInstant(annotation.from(), annotation.timezone()),
                parseToInstant(annotation.to(), annotation.timezone())
        ));
        return new ConfigFunction(method.returnType(), annotation.field(), function);
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
