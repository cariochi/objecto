package com.cariochi.objecto.generator;

import com.cariochi.objecto.ObjectoSettings;
import com.cariochi.objecto.utils.Random;
import com.cariochi.objecto.utils.Range;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.Date;

import static java.time.ZoneOffset.UTC;

public class TemporalGenerator extends Generator {

    public TemporalGenerator(RandomObjectGenerator randomObjectGenerator) {
        super(randomObjectGenerator);
    }

    @Override
    public boolean isSupported(Type type) {
        return type instanceof Class && (Temporal.class.isAssignableFrom((Class<?>) type) || Date.class.isAssignableFrom((Class<?>) type));
    }

    @Override
    public Object create(Type type, Type ownerType, ObjectoSettings settings) {
        if (type.equals(Date.class)) {
            return Date.from(generateInstant());
        } else if (type.equals(Instant.class)) {
            return generateInstant();
        } else if (type.equals(LocalDate.class)) {
            return generateInstant().atZone(UTC).toLocalDate();
        } else if (type.equals(LocalTime.class)) {
            return generateInstant().atZone(UTC).toLocalTime();
        } else if (type.equals(LocalDateTime.class)) {
            return generateInstant().atZone(UTC).toLocalDateTime();
        } else if (type.equals(ZonedDateTime.class)) {
            return generateInstant().atZone(UTC);
        } else if (type.equals(OffsetDateTime.class)) {
            return generateInstant().atOffset(UTC);
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
