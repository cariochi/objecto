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
        assertJson(dto, "/testFactoryInterfaceLevelSeed.json");
    }

    @Test
    void testFactoryMethodLevelSeed() {
        final Dto dto = factory.createDtoWithCustomSeed();
        assertJson(dto, "/testFactoryMethodLevelSeed.json");
    }

    @Test
    void testFactoryInstanceLevelSeed() {
        final Dto dto = factoryWithSeed.createDto();
        assertJson(dto, "/testFactoryInstanceLevelSeed.json");
    }

    @Seed(INTERFACE_LEVEL_SEED)
    private interface DtoFactory {

        @TypeFactory
        Dto createDto();

        @Seed(METHOD_LEVEL_SEED)
        Dto createDtoWithCustomSeed();

        @FieldFactory(type = Dto.class, field = "seed")
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
