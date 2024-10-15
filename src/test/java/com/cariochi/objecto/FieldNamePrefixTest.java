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

    @Settings.Strings.Parameters(uppercase = true, letters = true, numbers = false, useFieldNamePrefix = true)
    private interface SuperFactory {

    }

    private interface DtoFactory extends SuperFactory {

        Dto createDto();

        @FieldFactory(type = Dto.class, field = "month")
        @Settings.Integers.Range(min = 1, max = 1)
        Integer month();

        @FieldFactory(type = Dto.class, field = "year")
        @Settings.Integers.Range(min = 2024, max = 2024)
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
