package com.cariochi.objecto;

import com.cariochi.objecto.Datafaker.Base;
import com.cariochi.objecto.Datafaker.Base.*;
import com.cariochi.objecto.Datafaker.Base.Number;
import com.cariochi.objecto.Datafaker.Entertainment.Avatar;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static com.cariochi.utils.JsonUtils.assertJson;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class DatafakerTest {

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
    @Datafaker(expression = Base.Lorem.PARAGRAPH, locale = "fr")
    private interface DtoFactory {

        @Datafaker(locale = "pl")
        @Datafaker(field = "fullName", expression = Name.FULL_NAME)
        @Datafaker(field = "city", expression = Address.CITY, locale = "fr")
        @Datafaker(field = "country", expression = Address.COUNTRY, locale = "en")
        @Datafaker(field = "address", expression = Address.FULL_ADDRESS, locale = "en")
        @Datafaker(field = "image", expression = Avatar.IMAGE)
        @Datafaker(field = "yodaQuote", expression = Yoda.QUOTE)
        @Datafaker(field = "past", expression = TimeAndDate.PAST)
        @Datafaker(field = "future", expression = TimeAndDate.FUTURE)
        @Datafaker(field = "positive", expression = Number.POSITIVE)
        @Datafaker(field = "negative", expression = Number.NEGATIVE)
        @Datafaker(field = "bigDecimal", expression = Number.NEGATIVE)
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
        private int positive;
        private int negative;
        private BigDecimal bigDecimal;

    }
}
