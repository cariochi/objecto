package com.cariochi.objecto;

import java.util.List;
import java.util.Map;
import lombok.Data;
import org.junit.jupiter.api.Test;

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

    @Spec.Collections.Size.Range(from = 10, to = 11)
    @Spec.Arrays.Size(10)
    @Spec.Maps.Size(10)
    private interface ObjectFactory {

        List<Double> doublesList();

        @Spec.Collections.Size.Range(from = 1, to = 2)
        List<Integer> integersList();

        @Spec.Arrays.Size(1)
        Integer[] integersArray();


        @Spec.Maps.Size(1)
        Map<Integer, Integer> integersMap();


        @Spec.Collections.Size.Range(from = 3, to = 4)
        List<Dto> dtos();

        @GenerateField(type = Dto.class, field = "integers")
        @Spec.Collections.Size.Range(from = 2, to = 3)
        List<Integer> integers();

    }

    @Data
    private static class Dto {
        private List<Integer> integers;
    }
}
