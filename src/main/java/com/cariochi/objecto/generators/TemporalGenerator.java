package com.cariochi.objecto.generators;

import com.cariochi.objecto.config.ObjectoConfig;
import com.cariochi.objecto.config.ObjectoConfig.FakerConfig;
import com.cariochi.objecto.config.Range;
import com.cariochi.objecto.random.ObjectoRandom;
import com.cariochi.reflecto.types.ReflectoType;
import java.time.DayOfWeek;
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
import static org.apache.commons.lang3.StringUtils.isEmpty;

class TemporalGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return context.getType().is(TemporalAccessor.class) || context.getType().is(Date.class);
    }

    @Override
    public Object generate(Context context) {

        final ObjectoRandom random = context.getRandom();

        final ReflectoType typeReflection = context.getType();
        final ObjectoConfig config = context.getConfig();
        final Range<Instant> datesSettings = config.dates();
        final FakerConfig fakerConfig = config.faker();

        final Instant instant = isEmpty(fakerConfig.expression())
                ? random.nextInstant(datesSettings.from(), datesSettings.to())
                : Instant.parse(random.strings().faker(fakerConfig.locale()).nextString(fakerConfig.expression()));


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

}
