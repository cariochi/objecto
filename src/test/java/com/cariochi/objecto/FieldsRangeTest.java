package com.cariochi.objecto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FieldsRangeTest {

    private final ObjectFactory objectFactory = Objecto.create(ObjectFactory.class);

    @Test
    void test() {
        final Dto dto = objectFactory.createDto();
        assertThat(dto.getArray()).hasSizeBetween(10, 11);
        assertThat(dto.getList()).hasSizeBetween(12, 13);
        assertThat(dto.getMap()).hasSizeBetween(14, 15);
        assertThat(dto.getString()).hasSizeBetween(16, 17);

        assertThat(dto.getAnInt()).isBetween(100, 109);
        assertThat(dto.getALong()).isBetween(110L, 119L);
        assertThat(dto.getAShort()).isBetween((short) 120, (short) 129);
        assertThat(dto.getAByte()).isBetween((byte) 130, (byte) 139);
        assertThat(dto.getAChar()).isBetween((char) 140, (char) 149);
        assertThat(dto.getBigDecimal().doubleValue()).isBetween(150D, 159D);
        assertThat(dto.getADouble()).isBetween(160D, 169D);
        assertThat(dto.getAFloat()).isBetween(170F, 179F);
    }

    interface ObjectFactory {

        @Fields.Range(field = "array", from = 10, to = 11)
        @Fields.Range(field = "list", from = 12, to = 13)
        @Fields.Range(field = "map", from = 14, to = 15)
        @Fields.Range(field = "string", from = 16, to = 17)
        @Fields.Range(field = "anInt", from = 100, to = 109)
        @Fields.Range(field = "aLong", from = 110, to = 119)
        @Fields.Range(field = "aShort", from = 120, to = 129)
        @Fields.Range(field = "aByte", from = 130, to = 139)
        @Fields.Range(field = "aChar", from = 140, to = 149)
        @Fields.Range(field = "bigDecimal", from = 150, to = 159)
        @Fields.Range(field = "aDouble", from = 160, to = 169)
        @Fields.Range(field = "aFloat", from = 170, to = 179)
        Dto createDto();
    }

    @Data
    public static class Dto {
        int[] array;
        List<Integer> list;
        Map<String, Integer> map;
        String string;

        int anInt;
        long aLong;
        short aShort;
        byte aByte;
        char aChar;
        BigDecimal bigDecimal;
        double aDouble;
        float aFloat;
    }
}
