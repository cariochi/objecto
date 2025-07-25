package com.cariochi.objecto;

import java.util.List;
import java.util.Map;
import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FieldsSizeTest {

    private final ObjectFactory objectFactory = Objecto.create(ObjectFactory.class);

    @Test
    void test() {
        final Dto dto = objectFactory.createDto();
        assertThat(dto.getArray()).hasSize(10);
        assertThat(dto.getList()).hasSize(11);
        assertThat(dto.getMap()).hasSize(12);
        assertThat(dto.getString()).hasSize(13);
    }

    interface ObjectFactory {

        @Spec.Arrays.Size(field = "array", value = 10)
        @Spec.Collections.Size(field = "list", value = 11)
        @Spec.Maps.Size(field = "map", value = 12)
        @Spec.Strings.Length(field = "string", value = 13)
        Dto createDto();
    }

    @Data
    public static class Dto {
        int[] array;
        List<Integer> list;
        Map<String, Integer> map;
        String string;
    }
}
