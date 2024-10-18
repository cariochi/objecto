package com.cariochi.objecto;

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

    @Settings.Strings.Parameters(uppercase = true, letters = true, digits = false, useFieldNamePrefix = true)
    private interface SuperFactory {

    }

    private interface DtoFactory extends SuperFactory {

        Dto createDto();

        @FieldFactory(type = Dto.class, field = "month")
        @Settings.Integers.Range(from = 1, to = 2)
        Integer month();

        @FieldFactory(type = Dto.class, field = "year")
        @Settings.Integers.Range(from = 2024, to = 2025)
        Integer year();
    }

    @Data
    private static class Dto {

        private String name;
        private String value;
        private Integer month;
        private Integer year;

    }

}
