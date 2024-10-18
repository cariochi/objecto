package com.cariochi.objecto;

import com.cariochi.objecto.Settings.Collections;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import org.junit.jupiter.api.Test;

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
                .extracting(Dto::getInteger, Dto::getEnumValue, Dto::getLocalDate)
                .containsOnly(tuple(123, Enum.B, LocalDate.of(2024, 1, 1)));
    }

    @Collections.Size.Range(from = 10, to = 21)
    private interface ObjectFactory {

        @TypeFactory
        List<Dto> dtoList1();

        @Settings.Nullable(true)
        List<Dto> dtoList2();

        @Fields.Nullable(field = "[*].integer", value = true)
        List<Dto> dtoList3();

        @Fields.SetNull("[*].integer")
        List<Dto> dtoList4();

        @Fields.SetValue(field = "[*].integer", value = "123")
        @Fields.SetValue(field = "[*].enumValue", value = "B")
        @Fields.SetValue(field = "[*].localDate", value = "2024-01-01")
        List<Dto> dtoList5();

    }

    @Data
    private static class Dto {

        private Integer integer;
        private int intValue;
        private Enum enumValue;
        private LocalDate localDate;
    }

    enum Enum {
        A, B, C
    }
}
