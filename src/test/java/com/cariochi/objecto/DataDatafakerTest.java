package com.cariochi.objecto;

import com.cariochi.objecto.DatafakerMethod.Lorem;
import java.time.Instant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static com.cariochi.utils.JsonUtils.assertJson;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class DataDatafakerTest {

    private final DtoFactory factory = Objecto.create(DtoFactory.class);

    @Test
    void test() {
        final Dto dto = factory.createDto();
        assertThat(dto.getPast()).isBefore(Instant.now());
        assertThat(dto.getFuture()).isAfter(Instant.now());
        dto.setPast(null);
        dto.setFuture(null);
        assertJson(dto, "/dataDatafakerTest.json");
    }

    @Seed(1)
    @Settings.Datafaker.Locale("fr")
    @Settings.Datafaker.Method(Lorem.Paragraph)
    private interface DtoFactory {

        @Settings.Datafaker.Locale("pl")
        @Fields.Datafaker(field = "fullName", method = DatafakerMethod.Name.FullName)
        @Fields.Datafaker(field = "city", method = DatafakerMethod.Address.City, locale = "fr")
        @Fields.Datafaker(field = "country", method = DatafakerMethod.Address.Country, locale = "en")
        @Fields.Datafaker(field = "address", method = DatafakerMethod.Address.FullAddress, locale = "en")
        @Fields.Datafaker(field = "image", method = DatafakerMethod.Avatar.Image)
        @Fields.Datafaker(field = "yodaQuote", method = DatafakerMethod.Yoda.Quote)
        @Fields.Datafaker(field = "past", method = DatafakerMethod.TimeAndDate.Past)
        @Fields.Datafaker(field = "future", method = DatafakerMethod.TimeAndDate.Future)
        Dto createDto();

    }

    @Data
    private static class Dto {

        private String fullName;
        private String city;
        private String country;
        private String address;
        private String image;
        private String yodaQuote;
        private String loremParagraph;
        private Instant past;
        private Instant future;

    }
}
