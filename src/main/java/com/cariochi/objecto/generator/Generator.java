package com.cariochi.objecto.generator;

import com.cariochi.objecto.ObjectoSettings;
import java.lang.reflect.Type;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Generator {

    private final RandomObjectGenerator randomObjectGenerator;

    public abstract boolean isSupported(Type type);

    public abstract Object create(Type type, Type ownerType, ObjectoSettings settings);

    protected Object createInstance(Type type, ObjectoSettings settings) {
        return randomObjectGenerator.createInstance(type, settings);
    }

    protected Object generateRandomObject(Type type, Type ownerType, String fieldName, ObjectoSettings settings) {
        return randomObjectGenerator.generateRandomObject(type, ownerType, fieldName, settings);
    }

}
