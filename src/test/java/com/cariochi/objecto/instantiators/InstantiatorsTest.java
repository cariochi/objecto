package com.cariochi.objecto.instantiators;

import com.cariochi.objecto.generators.ObjectoGenerator;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static com.cariochi.reflecto.types.Types.type;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class InstantiatorsTest {

    private final ObjectoInstantiator instantiator = new ObjectoInstantiator(new ObjectoGenerator());

    private static Stream<Arguments> types() {
        return Stream.of(
                arguments(Instant.class),
                arguments(LocalDate.class),
                arguments(ZonedDateTime.class),
                arguments(type(Collection.class, String.class)),
                arguments(type(Collection.class, type(Collection.class, String.class))),
                arguments(type(List.class, String.class)),
                arguments(type(Set.class, String.class)),
                arguments(type(Queue.class, String.class)),
                arguments(type(Deque.class, String.class)),
                arguments(type(ArrayList.class, String.class)),
                arguments(type(LinkedList.class, String.class)),
                arguments(type(HashSet.class, String.class)),
                arguments(type(TreeSet.class, String.class)),
                arguments(type(Map.class, String.class, String.class)),
                arguments(type(HashMap.class, String.class, String.class)),
                arguments(type(TreeMap.class, String.class, String.class)),
                arguments(type(LinkedHashMap.class, String.class, String.class)),
                arguments(type(Map.class, String.class, type(Map.class, String.class, String.class)))
        );
    }

    @ParameterizedTest
    @MethodSource("types")
    void should_create_instance(Type type) {
        final Object instance = instantiator.newInstance(type);
        assertThat(instance).isNotNull();
    }

}
