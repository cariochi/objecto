package com.cariochi.objecto;

import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DatesTest {

    private final DatesFactory datesFactory = Objecto.create(DatesFactory.class);

    @Test
    void test() {
        assertThat(datesFactory.instant1()).isBetween("2024-01-01T05:00:00Z", "2024-01-02T05:00:00Z");
        assertThat(datesFactory.instant2()).isBetween("2025-01-01T05:00:00Z", "2025-01-02T05:00:00Z");
    }

    @Spec.Dates.Range(from = "2024-01-01T00:00:00-05:00", to = "2024-01-02T00:00:00-05:00")
    interface DatesFactory {

        @DefaultGenerator
        Instant instant1();

        @Spec.Dates.Range(from = "2025-01-01", to = "2025-01-02", timezone = "America/New_York")
        Instant instant2();
    }
}
