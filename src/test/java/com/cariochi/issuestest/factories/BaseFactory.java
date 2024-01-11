package com.cariochi.issuestest.factories;

import com.cariochi.objecto.Generator;
import java.time.Instant;
import java.time.LocalDateTime;
import net.datafaker.Faker;

import static java.time.Month.FEBRUARY;
import static java.time.ZoneOffset.UTC;

public interface BaseFactory {

    @Generator
    private Instant instantGenerator() {
        return LocalDateTime.of(1978, FEBRUARY, 20, 12, 0).atZone(UTC).toInstant();
    }

    @Generator
    private String stringGenerator() {
        return new Faker().lorem().sentence();
    }

}
