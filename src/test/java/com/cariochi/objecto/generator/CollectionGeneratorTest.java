package com.cariochi.objecto.generator;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static com.cariochi.reflecto.types.Types.type;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CollectionGeneratorTest {

    private final ObjectoGenerator objectoGenerator = new ObjectoGenerator();

    private static Stream<Arguments> types() {
        return Stream.of(
                arguments(type(Iterable.class, String.class)),
                arguments(type(Collection.class, String.class)),
                arguments(type(Collection.class, type(Collection.class, String.class))),
                arguments(type(List.class, String.class)),
                arguments(type(Set.class, String.class)),
                arguments(type(Queue.class, String.class)),
                arguments(type(Deque.class, String.class)),
                arguments(type(ArrayList.class, String.class)),
                arguments(type(LinkedList.class, String.class)),
                arguments(type(HashSet.class, String.class)),
                arguments(type(TreeSet.class, String.class))
        );
    }

    @ParameterizedTest
    @MethodSource("types")
    void should_generate_collection(Type type) {
        final Object instance = objectoGenerator.generateInstance(type);
        assertThat(instance).isNotNull();
    }

}
