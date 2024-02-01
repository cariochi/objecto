package com.cariochi.objecto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SeedTest {

    private final DtoFactory factory = Objecto.create(DtoFactory.class);

    @Test
    void test() {
        assertThat(factory.createDto())
                .isEqualTo(factory.createDto());
    }

    @Seed(123)
    interface DtoFactory {

        Dto createDto();

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Dto {

        private String string;
        private Integer integer;
        private long[] longs;

        private List<Dto> children;

    }

}
