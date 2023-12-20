package com.cariochi.objecto.utils;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

import static lombok.AccessLevel.PRIVATE;

@Value
@Accessors(fluent = true)
@RequiredArgsConstructor(access = PRIVATE)
public class Range<T> {

    T min;
    T max;

    public static <T> Range<T> of(T min, T max) {
        return new Range<>(min, max);
    }

}
