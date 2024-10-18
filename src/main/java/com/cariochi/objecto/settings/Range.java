package com.cariochi.objecto.settings;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.With;
import lombok.experimental.Accessors;

import static lombok.AccessLevel.PRIVATE;

@Value
@With
@Accessors(fluent = true)
@RequiredArgsConstructor(access = PRIVATE)
public class Range<T> {

    T from;
    T to;

    public static <T> Range<T> of(T from, T to) {
        return new Range<>(from, to);
    }

}
