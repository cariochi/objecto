package com.cariochi.objecto.extension;

import com.cariochi.objecto.GenerateField;
import com.cariochi.objecto.Objecto;
import com.cariochi.objecto.Seed;
import com.cariochi.objecto.random.ObjectoRandom;
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
        assertThat(dto).isEqualTo(Dto.builder().string("QCCJHRDKVKOXDPPJE").seed(2024).build());
    }

    @Test
    @Disabled
    void testFail() {
        assertThat(true).isFalse();
    }

    private interface DtoFactory {

        Dto createDto();

        @GenerateField(type = Dto.class, field = "seed")
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

