package com.cariochi.objecto;

import com.cariochi.objecto.Generate.Collections;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class NullableTest {

    private final ObjectFactory factory = Objecto.create(ObjectFactory.class);

    @Test
    void testNullable() {

        assertThat(factory.dtoList1())
                .extracting(Dto::getInteger)
                .doesNotContainNull();

        assertThat(factory.dtoList2())
                .extracting(Dto::getInteger)
                .containsNull();

        assertThat(factory.dtoList3())
                .extracting(Dto::getInteger)
                .containsNull();
    }

    @Test
    void testSetNull() {
        assertThat(factory.dtoList4())
                .extracting(Dto::getInteger)
                .containsOnlyNulls();
    }

    @Test
    void testSetValue() {
        assertThat(factory.dtoList5())
                .extracting(Dto::getInteger, Dto::getEnumValue, Dto::getLocalDate, Dto::getString)
                .containsOnly(tuple(123, Enum.B, LocalDate.of(2024, 1, 1), "string value"));
    }

    @Collections.Size.Range(from = 10, to = 21)
    private interface ObjectFactory {

        @PrimaryGenerator
        List<Dto> dtoList1();

        @Generate.Nullable(true)
        List<Dto> dtoList2();

        @Generate.Nullable(field = "[*].integer", value = true)
        List<Dto> dtoList3();

        @Generate.SetNull(field = "[*].integer")
        List<Dto> dtoList4();

        @Generate.SetValue(field = "[*].integer", value = "123")
        @Generate.SetValue(field = "[*].enumValue", value = "B")
        @Generate.SetValue(field = "[*].localDate", value = "2024-01-01")
        @Generate.SetValue(field = "[*].string", value = "string value")
        List<Dto> dtoList5();

    }

    @Data
    private static class Dto {

        private Integer integer;
        private int intValue;
        private Enum enumValue;
        private LocalDate localDate;
        private String string;
    }

    enum Enum {
        A, B, C
    }
}
