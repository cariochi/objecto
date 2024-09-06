package com.cariochi.objecto.generators;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

import static com.cariochi.reflecto.types.Types.type;
import static org.assertj.core.api.Assertions.assertThat;

public class StreamGeneratorTest {

    private final ObjectoGenerator generator = new ObjectoGenerator();

    @Test
    void should_generate_optional() {
        final Stream<Integer> optional = (Stream<Integer>) generator.generate(type(Stream.class, Integer.class));
        assertThat(optional)
                .isNotNull()
                .isNotEmpty();
    }

}
