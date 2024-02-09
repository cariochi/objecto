package com.cariochi.objecto;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionGenerationTest {

    private final MyCollectionFactory factory = Objecto.create(MyCollectionFactory.class);

    @Test
    void test() {
        final MyCollection myCollection = factory.createMyCollection();
        assertThat(myCollection)
                .isNotEmpty();

        final MyMap<Long> myMap = factory.createMyMap();
        assertThat(myMap)
                .isNotEmpty();
    }

    private interface MyCollectionFactory {

        MyCollection createMyCollection();

        MyMap<Long> createMyMap();

    }

    public static class MyCollection extends ArrayList<String> {
    }

    public static class MyMap<T> extends HashMap<String, T> {
    }


}
