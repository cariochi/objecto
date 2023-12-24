package com.cariochi.objecto.generator;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static com.cariochi.reflecto.types.Types.type;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ArrayGeneratorTest {

    private final ObjectoGenerator objectoGenerator = new ObjectoGenerator();

    private static Stream<Arguments> types() {
        return Stream.of(
                arguments(type(int[].class)),
                arguments(type(int[][].class)),
                arguments(type(List[].class, String.class)),
                arguments(type(List[][].class, String.class)),
                arguments(type(Map[].class, String.class, Integer.class)),
                arguments(type(Map[][].class, String.class, Integer.class))
        );
    }

    @ParameterizedTest
    @MethodSource("types")
    void should_generate_array(Type type) {
        final Object instance = objectoGenerator.generateInstance(type);
        assertThat(instance).isNotNull();
    }

}
