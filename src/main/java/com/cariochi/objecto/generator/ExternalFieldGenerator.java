package com.cariochi.objecto.generator;

import java.lang.reflect.Type;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExternalFieldGenerator {

    private final Type objectType;
    private final Type fieldType;
    private final String fieldName;
    private final Supplier<Object> generator;

    public boolean isSupported(Type objectType, Type fieldType, String fieldName) {
        return this.objectType.equals(objectType) && this.fieldType.equals(fieldType) && this.fieldName.equals(fieldName);
    }

    public Object create() {
        return generator.get();
    }

}
