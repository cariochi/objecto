package com.cariochi.objecto;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        assertThat(dto.getIntArray()).hasSizeBetween(10, 11);
        assertThat(dto.getIntegerList()).hasSizeBetween(12, 13);
        assertThat(dto.getStringIntegerMap()).hasSizeBetween(14, 15);
        assertThat(dto.getString()).hasSizeBetween(16, 17);

        assertThat(dto.getIntPrimitive()).isBetween(20, 29);
        assertThat(dto.getLongPrimitive()).isBetween(30L, 39L);
        assertThat(dto.getShortPrimitive()).isBetween((short) 40, (short) 49);
        assertThat(dto.getBytePrimitive()).isBetween((byte) 50, (byte) 59);
        assertThat(dto.getCharPrimitive()).isBetween('a', 'c');
        assertThat(dto.getBigDecimal().doubleValue()).isBetween(70D, 80D);
        assertThat(dto.getDoublePrimitive()).isBetween(80D, 90D);
        assertThat(dto.getFloatPrimitive()).isBetween(90F, 100F);

        assertThat(dto.getLocalDate()).isBetween(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 30));
    }

    interface ObjectFactory {

        @Spec.Arrays.Size.Range(from = 1, to = 2)
        @Spec.Arrays.Size.Range(field = "intArray", from = 10, to = 11)
        @Spec.Collections.Size.Range(field = "integerList", from = 12, to = 13)
        @Spec.Maps.Size.Range(field = "stringIntegerMap", from = 14, to = 15)
        @Spec.Strings.Length.Range(field = "string", from = 16, to = 17)
        @Spec.Integers.Range(field = "intPrimitive", from = 20, to = 30)
        @Spec.Longs.Range(field = "longPrimitive", from = 30, to = 40)
        @Spec.Shorts.Range(field = "shortPrimitive", from = 40, to = 50)
        @Spec.Bytes.Range(field = "bytePrimitive", from = 50, to = 60)
        @Spec.Chars.Range(field = "charPrimitive", from = 'a', to = 'd')
        @Spec.BigDecimals.Range(field = "bigDecimal", from = 70, to = 80)
        @Spec.Doubles.Range(field = "doublePrimitive", from = 80.0, to = 90.0)
        @Spec.Floats.Range(field = "floatPrimitive", from = 90, to = 100)
        @Spec.Dates.Range(field = "localDate", from = "2025-01-01", to = "2025-01-31")
        Dto createDto();
    }

    @Data
    public static class Dto {

        int[] intArray;
        List<Integer> integerList;
        Map<String, Integer> stringIntegerMap;
        String string;

        int intPrimitive;
        long longPrimitive;
        short shortPrimitive;
        byte bytePrimitive;
        char charPrimitive;
        BigDecimal bigDecimal;
        double doublePrimitive;
        float floatPrimitive;

        LocalDate localDate;
    }
}
