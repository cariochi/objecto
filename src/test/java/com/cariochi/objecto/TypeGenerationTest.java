package com.cariochi.objecto;

import java.util.List;
import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

public class TypeGenerationTest {

    private final ObjectFactory objectFactory = Objecto.create(ObjectFactory.class);

    @Test
    void test() {
        assertThat(objectFactory.list1())
                .extracting(Dto::getA, Dto::getB)
                .containsOnly(tuple(1, 22));

        assertThat(objectFactory.list2())
                .extracting(Dto::getA, Dto::getB)
                .containsOnly(tuple(11, 22));
    }

    interface ObjectFactory {

        @Spec.SetValue(field = "a", value = "1")
        Dto dto();

        @DefaultGenerator
        @Spec.SetValue(field = "[*].b", value = "22")
        List<Dto> list1();

        @Spec.SetValue(field = "[*].a", value = "11")
        @Spec.SetValue(field = "[*].b", value = "22")
        List<Dto> list2();

    }

    @Data
    static class Dto {

        private int a;
        private int b;

    }
}
