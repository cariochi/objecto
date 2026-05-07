package com.cariochi.objecto;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DatesTest {

    private final DatesFactory datesFactory = Objecto.create(DatesFactory.class);

    @Test
    void test() {
        assertThat(datesFactory.instant1()).isBetween("2024-01-01T05:00:00Z", "2024-01-02T05:00:00Z");
        assertThat(datesFactory.instant2()).isBetween("2025-01-01T05:00:00Z", "2025-01-02T05:00:00Z");
    }

    @Test
    void should_fail_with_clear_message_for_invalid_date_range() {
        assertThatThrownBy(() -> Objecto.create(InvalidDatesFactory.class).instant())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid @Generate.Dates.Range value 'not-a-date'")
                .hasMessageContaining("Supported formats");
    }

    @Generate.Dates.Range(from = "2024-01-01T00:00:00-05:00", to = "2024-01-02T00:00:00-05:00")
    interface DatesFactory {

        @PrimaryGenerator
        Instant instant1();

        @Generate.Dates.Range(from = "2025-01-01", to = "2025-01-02", timezone = "America/New_York")
        Instant instant2();
    }

    interface InvalidDatesFactory {

        @Generate.Dates.Range(from = "not-a-date", to = "2025-01-02")
        Instant instant();
    }
}
