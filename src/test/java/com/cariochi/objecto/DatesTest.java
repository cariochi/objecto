package com.cariochi.objecto;

import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DatesTest {

    private final DatesFactory datesFactory = Objecto.create(DatesFactory.class);

    @Test
    void test() {
        assertThat(datesFactory.instant1()).hasToString("2025-01-01T05:00:00Z");
        assertThat(datesFactory.instant2()).hasToString("2025-01-02T05:00:00Z");
    }

    @Settings.Dates.Range(min = "2025-01-01T00:00:00-05:00", max = "2025-01-01T00:00:00-05:00")
    interface DatesFactory {

        @TypeFactory
        Instant instant1();

        @Settings.Dates.Range(min = "2025-01-02", max = "2025-01-02", timezone = "America/New_York")
        Instant instant2();
    }
}
