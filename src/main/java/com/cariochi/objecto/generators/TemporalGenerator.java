package com.cariochi.objecto.generators;

import com.cariochi.objecto.utils.Random;
import java.lang.reflect.Type;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.Date;

import static java.time.ZoneOffset.UTC;

class TemporalGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        final Class<?> rawClass = context.getRawClass();
        return Temporal.class.isAssignableFrom(rawClass) || Date.class.isAssignableFrom(rawClass);
    }

    @Override
    public Object generate(Context context) {
        final Type type = context.getType();
        final Instant instant = generateInstant(context);
        final ZonedDateTime zonedDateTime = instant.atZone(UTC);
        if (type.equals(Date.class)) {
            return Date.from(instant);
        } else if (type.equals(Instant.class)) {
            return instant;
        } else if (type.equals(LocalDate.class)) {
            return zonedDateTime.toLocalDate();
        } else if (type.equals(LocalTime.class)) {
            return zonedDateTime.toLocalTime();
        } else if (type.equals(LocalDateTime.class)) {
            return zonedDateTime.toLocalDateTime();
        } else if (type.equals(ZonedDateTime.class)) {
            return zonedDateTime;
        } else if (type.equals(OffsetDateTime.class)) {
            return instant.atOffset(UTC);
        } else if (type.equals(Year.class)) {
            return Year.from(zonedDateTime);
        } else if (type.equals(Month.class)) {
            return Month.from(zonedDateTime);
        } else if (type.equals(MonthDay.class)) {
            return MonthDay.from(zonedDateTime);
        } else if (type.equals(DayOfWeek.class)) {
            return DayOfWeek.from(zonedDateTime);
        } else if (type.equals(YearMonth.class)) {
            return YearMonth.from(zonedDateTime);
        } else if (type.equals(ZoneOffset.class)) {
            return ZoneOffset.from(zonedDateTime);
        } else if (type.equals(OffsetTime.class)) {
            return OffsetTime.from(zonedDateTime);
        } else {
            return null;
        }
    }

    private Instant generateInstant(Context context) {
        long yearInSeconds = Duration.ofDays(365).getSeconds();
        long randomSeconds = (long) (Random.nextDouble(context.getSettings().years()) * yearInSeconds);
        return Instant.now().plusSeconds(randomSeconds);
    }

}
