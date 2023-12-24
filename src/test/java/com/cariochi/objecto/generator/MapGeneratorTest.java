package com.cariochi.objecto.generator;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static com.cariochi.reflecto.types.Types.type;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MapGeneratorTest {

    private final ObjectoGenerator objectoGenerator = new ObjectoGenerator();

    private static Stream<Arguments> types() {
        return Stream.of(
                arguments(type(Map.class, String.class, String.class)),
                arguments(type(HashMap.class, String.class, String.class)),
                arguments(type(TreeMap.class, String.class, String.class)),
                arguments(type(LinkedHashMap.class, String.class, String.class)),
                arguments(type(Map.class, String.class, type(Map.class, String.class, String.class)))
        );
    }

    @ParameterizedTest
    @MethodSource("types")
    void should_generate_map(Type type) {
        final Object instance = objectoGenerator.generateInstance(type);
        assertThat(instance).isNotNull();
    }

}
