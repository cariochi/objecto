package com.cariochi.objecto;

import com.cariochi.objecto.WithSettings.IntRange;
import com.cariochi.objecto.WithSettings.Strings;
import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FieldNamePrefixTest {

    private final DtoFactory factory = Objecto.create(DtoFactory.class);

    @Test
    void test() {
        final Dto dto = factory.createDto();
        assertThat(dto.getName()).startsWith("name");
        assertThat(dto.getValue()).startsWith("value");
        assertThat(dto)
                .extracting(Dto::getMonth, Dto::getYear)
                .containsExactly(1, 2024);
    }

    @WithSettings(strings = @Strings(fieldNamePrefix = true))
    interface SuperFactory {

    }

    interface DtoFactory extends SuperFactory{

        @WithSettings(path = "month", integers = @IntRange(min = 1, max = 2))
        @WithSettings(path = "year", integers = @IntRange(min = 2024, max = 2025))
        Dto createDto();

    }

    @Data
    public static class Dto {

        private String name;
        private String value;
        private Integer month;
        private Integer year;

    }

}
