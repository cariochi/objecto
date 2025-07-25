package com.cariochi.objecto;

import com.cariochi.objecto.Faker.Base;
import com.cariochi.objecto.Faker.Base.Address;
import com.cariochi.objecto.Faker.Base.Name;
import com.cariochi.objecto.Faker.Base.Number;
import com.cariochi.objecto.Faker.Base.TimeAndDate;
import com.cariochi.objecto.Faker.Base.Yoda;
import com.cariochi.objecto.Faker.Entertainment.Avatar;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static com.cariochi.utils.JsonUtils.assertJson;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class FakerTest {

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
    @Faker(expression = Base.Lorem.PARAGRAPH, locale = "fr")
    private interface DtoFactory {

        @Faker(locale = "pl")
        @Faker(field = "fullName", expression = Name.FULL_NAME)
        @Faker(field = "city", expression = Address.CITY, locale = "fr")
        @Faker(field = "country", expression = Address.COUNTRY, locale = "en")
        @Faker(field = "address", expression = Address.FULL_ADDRESS, locale = "en")
        @Faker(field = "image", expression = Avatar.IMAGE)
        @Faker(field = "yodaQuote", expression = Yoda.QUOTE)
        @Faker(field = "past", expression = TimeAndDate.PAST)
        @Faker(field = "future", expression = TimeAndDate.FUTURE)
        @Faker(field = "positive", expression = Number.POSITIVE)
        @Faker(field = "negative", expression = Number.NEGATIVE)
        @Faker(field = "bigDecimal", expression = Number.NEGATIVE)
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
