package com.cariochi.objecto;

import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ByteArrayTest {


    @Test
    void should_generate_byte_array_field() {
        final FileFactory factory = Objecto.create(FileFactory.class);
        final Arrays arrays = factory.createFile();
        assertThat(arrays)
                .extracting(Arrays::getBytes, Arrays::getChars, Arrays::getStrings, Arrays::getIntegers)
                .doesNotContainNull();
    }

    @Data
    public static class Arrays {

        private byte[] bytes;
        private int[] integers;
        private String[] strings;
        private char[] chars;

    }

    public interface FileFactory {

        Arrays createFile();

    }

}
