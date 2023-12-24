package com.cariochi.objecto.utils;

import java.lang.reflect.Type;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
public class FieldKey {

    Type objectType;
    Type fieldType;
    String fieldName;

}
