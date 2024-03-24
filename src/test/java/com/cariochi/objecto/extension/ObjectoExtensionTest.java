package com.cariochi.objecto.extension;

import com.cariochi.objecto.Generator;
import com.cariochi.objecto.Objecto;
import com.cariochi.objecto.ObjectoRandom;
import com.cariochi.objecto.Seed;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ObjectoExtension.class)
class ObjectoExtensionTest {

    private final DtoFactory factory = Objecto.create(DtoFactory.class);

    @Test
    @Seed(2024)
    void testMethodLevelSeed() {
        final Dto dto = factory.createDto();
        assertThat(dto).isEqualTo(Dto.builder().string("HIXSPWVGVOKI").seed(2024).build());
    }

    @Test
    @Disabled
    void testFail() {
        throw new RuntimeException();
    }

    private interface DtoFactory {

        Dto createDto();

        @Generator(type = Dto.class, expression = "seed")
        private long generateSeed(ObjectoRandom random) {
            return random.getSeed();
        }

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Dto {

        private long seed;
        private String string;

    }

}

