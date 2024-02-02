package com.cariochi.objecto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

class SeedTest {

    private final DtoFactory factory = Objecto.create(DtoFactory.class);
    private final DtoFactory factoryWithSeed = Objecto.create(DtoFactory.class, 100L);

    @Test
    void testFactoryInterfaceLevelSeed() {
        final Dto dto = factory.createDto();
        assertThat(dto)
                .isEqualTo(Dto.builder()
                        .string("ZTNUVNEANPAWQ")
                        .integer(60228)
                        .longs(new long[]{78260, 82620, 44139, 46767})
                        .children(List.of(
                                Dto.builder().string("JOXEROBNPFO").integer(47688).longs(new long[]{8202, 53912}).children(emptyList()).build(),
                                Dto.builder().string("AYXEZQHGPSTAIJ").integer(18540).longs(new long[]{61173, 46276, 5335, 35687}).children(emptyList()).build(),
                                Dto.builder().string("PYZBSHETWNDXXQ").integer(21944).longs(new long[]{1509, 11878, 80116, 65433}).children(emptyList()).build(),
                                Dto.builder().string("KEJIUYNGBI").integer(37987).longs(new long[]{65484, 84702, 10887, 26435}).children(emptyList()).build()
                        ))
                        .build());
    }

    @Test
    void testFactoryMethodLevelSeed() {
        final Dto dto = factory.createDtoWithCustomSeed();
        assertThat(dto)
                .isEqualTo(Dto.builder()
                        .string("DFLJBOYNORQHL")
                        .integer(83516)
                        .longs(new long[]{23305, 90592})
                        .children(List.of(
                                Dto.builder().string("PQPHNFHBXTRAJ").integer(16116).longs(new long[]{18975, 76565, 44361, 39654}).children(emptyList()).build(),
                                Dto.builder().string("RRZLKIRBPRCRVIY").integer(535).longs(new long[]{74803, 76021}).children(emptyList()).build(),
                                Dto.builder().string("LSZZVDIB").integer(49391).longs(new long[]{93173, 62280, 92135, 87597}).children(emptyList()).build()
                        ))
                        .build());
    }

    @Test
    void testFactoryInstanceLevelSeed() {
        final Dto dto = factoryWithSeed.createDto();
        assertThat(dto)
                .isEqualTo(Dto.builder()
                        .string("GKWWBRGRUTRMJ")
                        .integer(99098)
                        .longs(new long[]{32613, 18505})
                        .children(List.of(
                                Dto.builder().string("MVREZEJI").integer(88662).longs(new long[]{61685, 511, 20267}).children(emptyList()).build(),
                                Dto.builder().string("PNVATOGJCMCGY").integer(4906).longs(new long[]{73309, 13739, 95448}).children(emptyList()).build()
                        ))
                        .build());
    }

    @Seed(123)
    interface DtoFactory {

        Dto createDto();

        @Seed(320)
        Dto createDtoWithCustomSeed();

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
