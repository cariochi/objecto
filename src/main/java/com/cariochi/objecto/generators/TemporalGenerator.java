package com.cariochi.objecto.generators;

import com.cariochi.reflecto.types.ReflectoType;
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
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import static java.time.ZoneOffset.UTC;

class TemporalGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return context.getType().is(TemporalAccessor.class) || context.getType().is(Date.class);
    }

    @Override
    public Object generate(Context context) {
        final ReflectoType typeReflection = context.getType();
        final Instant instant = generateInstant(context);
        final ZonedDateTime zonedDateTime = instant.atZone(UTC);
        if (typeReflection.is(Date.class)) {
            return Date.from(instant);
        } else if (typeReflection.is(Instant.class)) {
            return instant;
        } else if (typeReflection.is(LocalDate.class)) {
            return zonedDateTime.toLocalDate();
        } else if (typeReflection.is(LocalTime.class)) {
            return zonedDateTime.toLocalTime();
        } else if (typeReflection.is(LocalDateTime.class)) {
            return zonedDateTime.toLocalDateTime();
        } else if (typeReflection.is(ZonedDateTime.class)) {
            return zonedDateTime;
        } else if (typeReflection.is(OffsetDateTime.class)) {
            return instant.atOffset(UTC);
        } else if (typeReflection.is(Year.class)) {
            return Year.from(zonedDateTime);
        } else if (typeReflection.is(Month.class)) {
            return Month.from(zonedDateTime);
        } else if (typeReflection.is(MonthDay.class)) {
            return MonthDay.from(zonedDateTime);
        } else if (typeReflection.is(DayOfWeek.class)) {
            return DayOfWeek.from(zonedDateTime);
        } else if (typeReflection.is(YearMonth.class)) {
            return YearMonth.from(zonedDateTime);
        } else if (typeReflection.is(ZoneOffset.class)) {
            return ZoneOffset.from(zonedDateTime);
        } else if (typeReflection.is(OffsetTime.class)) {
            return OffsetTime.from(zonedDateTime);
        } else {
            return null;
        }
    }

    private Instant generateInstant(Context context) {
        long yearInSeconds = Duration.ofDays(365).getSeconds();
        long randomSeconds = (long) (context.getRandom().nextDouble(context.getSettings().years()) * yearInSeconds);
        return Instant.now().plusSeconds(randomSeconds);
    }

}
