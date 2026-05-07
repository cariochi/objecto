package com.cariochi.objecto;

import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.List;

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

        @Generate.SetValue(field = "a", value = "1")
        Dto dto();

        @PrimaryGenerator
        @Generate.SetValue(field = "[*].b", value = "22")
        List<Dto> list1();

        @Generate.SetValue(field = "[*].a", value = "11")
        @Generate.SetValue(field = "[*].b", value = "22")
        List<Dto> list2();

    }

    @Data
    static class Dto {

        private int a;
        private int b;

    }
}
