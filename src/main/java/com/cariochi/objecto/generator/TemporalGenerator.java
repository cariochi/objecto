package com.cariochi.objecto.generator;

import com.cariochi.objecto.utils.Random;
import com.cariochi.objecto.utils.Range;
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

import static com.cariochi.objecto.utils.GenericTypeUtils.getRawClass;
import static java.time.ZoneOffset.UTC;

class TemporalGenerator extends Generator {

    public TemporalGenerator(ObjectoGenerator objectoGenerator) {
        super(objectoGenerator);
    }

    @Override
    public boolean isSupported(Type type, GenerationContext context) {
        final Class<?> rawType = getRawClass(type, context.ownerType());
        return rawType != null && (Temporal.class.isAssignableFrom(rawType) || Date.class.isAssignableFrom(rawType));
    }

    @Override
    public Object create(Type type, GenerationContext context) {
        final Instant instant = generateInstant();
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

    private Instant generateInstant() {
        long fiveYearsInSeconds = Duration.ofDays(365).getSeconds();
        long randomSeconds = (long) ((Random.nextDouble(Range.of(-5.0, 1.0))) * fiveYearsInSeconds);
        return Instant.now().plusSeconds(randomSeconds);
    }

}
