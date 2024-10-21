package com.cariochi.objecto.settings.functions.settings;

import com.cariochi.objecto.Settings;
import com.cariochi.objecto.settings.ObjectoSettings;
import com.cariochi.objecto.settings.Range;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.function.UnaryOperator;

public class DatesRange implements SettingsFunction<Settings.Dates.Range> {

    @Override
    public UnaryOperator<ObjectoSettings> apply(Settings.Dates.Range annotation) {
        return settings -> settings.withDates(Range.of(
                parseToInstant(annotation.from(), annotation.timezone()),
                parseToInstant(annotation.to(), annotation.timezone())
        ));
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
