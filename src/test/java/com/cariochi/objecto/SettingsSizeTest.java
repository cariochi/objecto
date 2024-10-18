package com.cariochi.objecto;

import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SettingsSizeTest {

    private final ObjectFactory objectFactory = Objecto.create(ObjectFactory.class);

    @Test
    void test() {
        assertThat(objectFactory.string1()).hasSize(3);
        assertThat(objectFactory.string2()).hasSizeBetween(6, 8);
        assertThat(objectFactory.array1()).hasSize(3);
        assertThat(objectFactory.array2()).hasSizeBetween(6, 8);
        assertThat(objectFactory.collection1()).hasSize(3);
        assertThat(objectFactory.collection2()).hasSizeBetween(6, 8);
        assertThat(objectFactory.map1()).hasSize(3);
        assertThat(objectFactory.map2()).hasSizeBetween(6, 8);
    }

    @Settings.Collections.Size.Range(from = 0, to = 1)
    @Settings.Arrays.Size.Range(from = 0, to = 1)
    @Settings.Maps.Size.Range(from = 0, to = 1)
    @Settings.Strings.Length.Range(from = 0, to = 1)
    interface ObjectFactory {

        @Settings.Strings.Length(3)
        String string1();

        @Settings.Strings.Length.Range(from = 6, to = 8)
        String string2();

        @Settings.Arrays.Size(3)
        int[] array1();

        @Settings.Arrays.Size.Range(from = 6, to = 8)
        int[] array2();

        @Settings.Collections.Size(3)
        Set<Integer> collection1();

        @Settings.Collections.Size.Range(from = 6, to = 8)
        Set<Integer> collection2();

        @Settings.Maps.Size(3)
        Map<Integer, Integer> map1();

        @Settings.Maps.Size.Range(from = 6, to = 8)
        Map<Integer, Integer> map2();

    }
}
