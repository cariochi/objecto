package com.cariochi.objecto.generator;

import com.cariochi.objecto.RandomObjectGenerator;
import java.lang.reflect.Type;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Generator {

    private final RandomObjectGenerator randomObjectGenerator;

    public abstract boolean isSupported(Type type);

    public abstract Object create(Type type, int depth);

    protected <T> T createInstance(Type type) {
        return randomObjectGenerator.createInstance(type);
    }

    protected <T> T generateRandomObject(Type type, int depth) {
        return randomObjectGenerator.generateRandomObject(type, depth);
    }

    protected <T> T generateFiledValue(Type objectType, Type fieldType, String fieldName, int depth) {
        return randomObjectGenerator.generateFiledValue(objectType, fieldType, fieldName, depth);
    }

}
