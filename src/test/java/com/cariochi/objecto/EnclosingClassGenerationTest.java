package com.cariochi.objecto;

import com.cariochi.objecto.EnclosingClassGenerationTest.Dto.InnerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EnclosingClassGenerationTest {

    private final DtoFactory factory = Objecto.create(DtoFactory.class);

    @Test
    void test() {
        final InnerDto innerDto = factory.innerDto();

        final Dto dto = new Dto("ZJDOIKKRIMC");
        dto.getInnerDto().setValue("XATKTPWNY");
        assertThat(innerDto).isEqualTo(dto.getInnerDto());
    }

    @Seed(1)
    private interface DtoFactory {

        @References("this$0.innerDto")
        InnerDto innerDto();

    }

    @Data
    @AllArgsConstructor
    public static class Dto {

        private String name;
        private final InnerDto innerDto = new InnerDto();

        @Data
        public class InnerDto {

            private String value;

        }

    }

}
