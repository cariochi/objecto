package com.cariochi.objecto;

import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ListGenerationTest {

    private final ObjectFactory factory = Objecto.create(ObjectFactory.class);

    @Test
    void test() {
        assertThat(factory.doublesList()).hasSize(10);
        assertThat(factory.integersList()).hasSize(1);
        assertThat(factory.integersArray()).hasSize(1);
        assertThat(factory.integersMap()).hasSize(1);
        final List<Dto> dtos = factory.dtos();
        assertThat(dtos).hasSize(3)
                .extracting(Dto::getIntegers)
                .extracting(List::size)
                .containsOnly(2);
    }

    @Generate.Collections.Size.Range(from = 10, to = 11)
    @Generate.Arrays.Size(10)
    @Generate.Maps.Size(10)
    private interface ObjectFactory {

        List<Double> doublesList();

        @Generate.Collections.Size.Range(from = 1, to = 2)
        List<Integer> integersList();

        @Generate.Arrays.Size(1)
        Integer[] integersArray();


        @Generate.Maps.Size(1)
        Map<Integer, Integer> integersMap();


        @Generate.Collections.Size.Range(from = 3, to = 4)
        List<Dto> dtos();

        @FieldGenerator(type = Dto.class, field = "integers")
        @Generate.Collections.Size.Range(from = 2, to = 3)
        List<Integer> integers();

    }

    @Data
    private static class Dto {
        private List<Integer> integers;
    }
}
