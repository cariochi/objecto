package com.cariochi.objecto.factories;

import com.cariochi.objecto.TypeGenerator;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import net.datafaker.Faker;

import static java.time.ZoneOffset.UTC;

public interface BaseFactory {

    @TypeGenerator
    private Instant instantGenerator() {
        return LocalDateTime.of(1978, Month.FEBRUARY, 20, 12, 0).atZone(UTC).toInstant();
    }

    @TypeGenerator
    private String stringGenerator() {
        return new Faker().lorem().sentence();
    }
}
