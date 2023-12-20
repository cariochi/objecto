package com.cariochi.objecto.generator;

import com.cariochi.objecto.ObjectoSettings;
import com.cariochi.objecto.RandomObjectGenerator;
import java.lang.reflect.Type;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Generator {

    private final RandomObjectGenerator randomObjectGenerator;

    public abstract boolean isSupported(Type type);

    public abstract Object create(Type type, ObjectoSettings settings);

    protected <T> T createInstance(Type type) {
        return randomObjectGenerator.createInstance(type);
    }

    protected <T> T generateRandomObject(Type type, ObjectoSettings settings) {
        return randomObjectGenerator.generateRandomObject(type, settings);
    }

    protected <T> T generateFiledValue(Type objectType, Type fieldType, String fieldName, ObjectoSettings settings) {
        return randomObjectGenerator.generateFiledValue(objectType, fieldType, fieldName, settings);
    }

}
