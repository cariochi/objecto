package com.cariochi.objecto.generators;

import java.lang.reflect.Type;
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
import java.util.Date;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class TemporalGeneratorTest {

    private final ObjectoGenerator objectoGenerator = new ObjectoGenerator();

    private static Stream<Arguments> types() {
        return Stream.of(
                arguments(Date.class),
                arguments(Instant.class),
                arguments(LocalDate.class),
                arguments(LocalTime.class),
                arguments(LocalDateTime.class),
                arguments(ZonedDateTime.class),
                arguments(OffsetDateTime.class),
                arguments(Year.class),
                arguments(Month.class),
                arguments(MonthDay.class),
                arguments(DayOfWeek.class),
                arguments(YearMonth.class),
                arguments(ZoneOffset.class),
                arguments(OffsetTime.class)
        );
    }

    @ParameterizedTest
    @MethodSource("types")
    void should_generate_temporal(Type type) {
        final Object instance = objectoGenerator.generateInstance(type);
        assertThat(instance).isNotNull();
    }

}
