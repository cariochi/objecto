package com.cariochi.objecto;

import java.util.UUID;
import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UUIDGeneratorTest {

    private static final ObjectFactory objects = Objecto.create(ObjectFactory.class);

    @Test
    void test() {
        final Dto dto = objects.dto();
        assertThat(dto.getUuid()).isNotNull();
    }

    interface ObjectFactory {
        Dto dto();
    }

    @Data
    static class Dto {
        private UUID uuid;
    }
}
