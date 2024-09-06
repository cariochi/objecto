package com.cariochi.objecto.generators;

import java.util.Optional;
import org.junit.jupiter.api.Test;

import static com.cariochi.reflecto.types.Types.type;
import static org.assertj.core.api.Assertions.assertThat;

public class OptionalGeneratorTest {

    private final ObjectoGenerator generator = new ObjectoGenerator();

    @Test
    void should_generate_optional() {
        final Optional<Integer> optional = (Optional<Integer>) generator.generate(type(Optional.class, Integer.class));
        assertThat(optional)
                .isNotNull()
                .isNotEmpty();
    }

}
