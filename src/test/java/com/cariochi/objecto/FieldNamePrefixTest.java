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
                .extracting(Dto::getA, Dto::getB)
                .containsExactly(1, 2);
    }

    @WithSettings(strings = @Strings(fieldNamePrefix = true))
    interface SuperFactory {

    }

    interface DtoFactory extends SuperFactory {

        @WithSettings(path = "a", integers = @IntRange(min = 1, max = 2))
        @WithSettings(path = "b", integers = @IntRange(min = 2, max = 3))
        Dto createDto();

    }

    @Data
    public static class Dto {

        private String name;
        private String value;
        private Integer a;
        private Integer b;

    }

}
