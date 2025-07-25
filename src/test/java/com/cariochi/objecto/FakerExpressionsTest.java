package com.cariochi.objecto;

import com.cariochi.objecto.random.ObjectoRandom;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;

import static com.cariochi.reflecto.Reflecto.reflect;
import static com.cariochi.utils.JsonUtils.assertJson;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toMap;

public class FakerExpressionsTest {

    private static final List<String> exceptions = List.of(
            "Country.CURRENCY",
            "Country.CURRENCY_CODE",
            "Money.CURRENCY",
            "Money.CURRENCY_CODE",
            "Money.CURRENCY_NUMERIC_CODE",
            "Money.CURRENCY_SYMBOL",
            "Sip.BODY_BYTES",
            "TimeAndDate.FUTURE",
            "TimeAndDate.PAST",
            "Image.BASE64_BMP",
            "Image.BASE64_GIF",
            "Image.BASE64_JPEG",
            "Image.BASE64_JPG",
            "Image.BASE64_PNG",
            "Image.BASE64_SVG",
            "Image.BASE64_TIFF"
    );

    @Test
    void test() {
        ObjectoRandom random = new ObjectoRandom();
        random.setSeed(1);
        Map<String, String> map = reflect(Faker.class).types().stream()
                .flatMap(provider -> provider.types().stream())
                .flatMap(type -> type.fields().stream())
                .sorted(comparing(field -> field.declaringType().actualClass().getSimpleName() + "." + field.name()))
                .collect(toMap(
                        field -> field.declaringType().actualClass().getSimpleName() + "." + field.name(),
                        field -> random.strings().faker().nextString(field.asStatic().getValue()),
                        (a, b) -> a,
                        TreeMap::new
                ));
        exceptions.forEach(map::remove);
        assertJson(map, "/datafaker-expressions.json");
    }

}
