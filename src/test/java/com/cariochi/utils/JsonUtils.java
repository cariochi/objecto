package com.cariochi.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import static com.cariochi.reflecto.Reflecto.reflect;
import static org.assertj.core.api.Assertions.assertThat;

@UtilityClass
public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ObjectWriter WRITER = MAPPER.writerWithDefaultPrettyPrinter();

    public static void assertJson(Object object, String file) {
        assertThat(object)
                .isEqualTo(fromJson(object, file));
    }

    @SneakyThrows
    private static <T> T fromJson(Object object, String resourcePath) {
        try (InputStream inputStream = JsonUtils.class.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                Path filePath = Paths.get("src/test/resources" + resourcePath);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, toJson(object));
            }
            return MAPPER.readValue(inputStream, MAPPER.constructType(reflect(object).type().actualType()));
        }
    }

    @SneakyThrows
    private static byte[] toJson(Object object) {
        return WRITER.writeValueAsBytes(object);
    }


}
