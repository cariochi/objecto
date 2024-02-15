package com.cariochi.objecto;

import com.cariochi.objecto.extension.ObjectoExtension;
import com.cariochi.objecto.utils.ObjectoRandom;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ObjectoExtension.class)
class SeedTest {

    private static final long INTERFACE_LEVEL_SEED = 123;
    private static final long INSTANCE_LEVEL_SEED = 100;
    private static final long METHOD_LEVEL_SEED = 320;
    private static final long SEED_TO_IGNORE = 1000;

    private final DtoFactory factory = Objecto.create(DtoFactory.class);
    private final DtoFactory factoryWithSeed = Objecto.create(DtoFactory.class, INSTANCE_LEVEL_SEED);

    @Test
    @Seed(SEED_TO_IGNORE)
    void testFactoryInterfaceLevelSeed() {
        final Dto dto = factory.createDto();
        assertThat(dto)
                .isEqualTo(Dto.builder()
                        .string("ZTNUVNEANPAWQ")
                        .seed(INTERFACE_LEVEL_SEED)
                        .number(60228)
                        .children(List.of(
                                Dto.builder().string("XWNSEJOXE").seed(INTERFACE_LEVEL_SEED).number(39650).children(emptyList()).build(),
                                Dto.builder().string("BNPFOGAKBAYXEZQ").seed(INTERFACE_LEVEL_SEED).number(41636).children(emptyList()).build(),
                                Dto.builder().string("STAIJSDYNVDTM").seed(INTERFACE_LEVEL_SEED).number(51421).children(emptyList()).build()
                        ))
                        .build());
    }

    @Test
    void testFactoryMethodLevelSeed() {
        final Dto dto = factory.createDtoWithCustomSeed();
        assertThat(dto)
                .isEqualTo(Dto.builder()
                        .string("DFLJBOYNORQHL")
                        .seed(METHOD_LEVEL_SEED)
                        .number(83516)
                        .children(List.of(
                                Dto.builder().string("LFVPQPHNF").seed(METHOD_LEVEL_SEED).number(38733).children(emptyList()).build(),
                                Dto.builder().string("XTRAJDFQ").seed(METHOD_LEVEL_SEED).number(68764).children(emptyList()).build()
                        ))
                        .build());
    }

    @Test
    void testFactoryInstanceLevelSeed() {
        final Dto dto = factoryWithSeed.createDto();
        assertThat(dto)
                .isEqualTo(Dto.builder()
                        .string("GKWWBRGRUTRMJ")
                        .seed(INSTANCE_LEVEL_SEED)
                        .number(99098)
                        .children(List.of(
                                Dto.builder().string("ZGMVREZEJIYSNZP").seed(INSTANCE_LEVEL_SEED).number(68707).children(emptyList()).build(),
                                Dto.builder().string("TOGJCMCGYIT").seed(INSTANCE_LEVEL_SEED).number(91389).children(emptyList()).build(),
                                Dto.builder().string("WADKLQBNOS").seed(INSTANCE_LEVEL_SEED).number(71739).children(emptyList()).build()
                        ))
                        .build());
    }

    @Seed(INTERFACE_LEVEL_SEED)
    private interface DtoFactory {

        Dto createDto();

        @Seed(METHOD_LEVEL_SEED)
        Dto createDtoWithCustomSeed();

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

        private String string;
        private long seed;
        private int number;

        private List<Dto> children;

    }

}
