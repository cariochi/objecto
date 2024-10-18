package com.cariochi.objecto;

import com.cariochi.objecto.extension.ObjectoExtension;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.cariochi.utils.JsonUtils.assertJson;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ObjectoExtension.class)
class SeedTest {

    private static final long INTERFACE_LEVEL_SEED = 123;
    private static final long INSTANCE_LEVEL_SEED = 100;
    private static final long METHOD_LEVEL_SEED = 320;
    private static final long TEST_METHOD_SEED = 1000;

    private final DtoFactory factory = Objecto.create(DtoFactory.class);
    private final DtoFactory factoryWithSeed = Objecto.create(DtoFactory.class, INSTANCE_LEVEL_SEED);
    private final DtoFactoryWithoutSeed factory2 = Objecto.create(DtoFactoryWithoutSeed.class);

    @Test
    @Seed(TEST_METHOD_SEED)
    void testTestMethodLevelSeed() {
        final Dto dto = factory2.createDto();
        assertThat(dto.getSeed()).isEqualTo(TEST_METHOD_SEED);
        assertThat(dto.getChildren()).extracting(Dto::getSeed).containsOnly(TEST_METHOD_SEED);
        assertJson(dto, "/testTestMethodLevelSeed.json");
    }

    @Test
    @Seed(TEST_METHOD_SEED)
    void testFactoryInterfaceLevelSeed() {
        final Dto dto = factory.createDto();
        assertThat(dto.getSeed()).isEqualTo(INTERFACE_LEVEL_SEED);
        assertThat(dto.getChildren()).extracting(Dto::getSeed).containsOnly(INTERFACE_LEVEL_SEED);
        assertJson(dto, "/testFactoryInterfaceLevelSeed.json");
    }

    @Test
    @Seed(TEST_METHOD_SEED)
    void testFactoryMethodLevelSeed() {
        final Dto dto = factory.createDtoWithCustomSeed();
        assertThat(dto.getSeed()).isEqualTo(METHOD_LEVEL_SEED);
        assertThat(dto.getChildren()).extracting(Dto::getSeed).containsOnly(METHOD_LEVEL_SEED);
        assertJson(dto, "/testFactoryMethodLevelSeed.json");
    }

    @Test
    @Seed(TEST_METHOD_SEED)
    void testFactoryInstanceLevelSeed() {
        final Dto dto = factoryWithSeed.createDto();
        assertThat(dto.getSeed()).isEqualTo(INSTANCE_LEVEL_SEED);
        assertThat(dto.getChildren()).extracting(Dto::getSeed).containsOnly(INSTANCE_LEVEL_SEED);
        assertJson(dto, "/testFactoryInstanceLevelSeed.json");
    }

    @Seed(INTERFACE_LEVEL_SEED)
    private interface DtoFactory extends ParentDtoFactory {

        @TypeFactory
        Dto createDto();

        @Seed(METHOD_LEVEL_SEED)
        Dto createDtoWithCustomSeed();

    }

    private interface DtoFactoryWithoutSeed extends ParentDtoFactory {
        Dto createDto();
    }

    private interface ParentDtoFactory {

        @FieldFactory(type = Dto.class, field = "seed")
        private long seed(ObjectoRandom random) {
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
