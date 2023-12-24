package com.cariochi.objecto.generator;

import java.lang.reflect.Type;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
abstract class Generator {

    private final ObjectoGenerator objectoGenerator;

    public abstract boolean isSupported(Type type, GenerationContext context);

    public abstract Object create(Type type, GenerationContext context);

    protected Object createInstance(Type type, GenerationContext context) {
        return objectoGenerator.createInstance(type, context);
    }

    protected Object generateRandomObject(Type type, GenerationContext context) {
        return objectoGenerator.generateInstance(type, context);
    }

}
