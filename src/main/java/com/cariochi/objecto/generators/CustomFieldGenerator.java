package com.cariochi.objecto.generators;

import java.lang.reflect.Type;
import java.util.function.Supplier;

class CustomFieldGenerator extends Generator {

    private final Type objectType;
    private final Type fieldType;
    private final String fieldName;
    private final Supplier<Object> generator;

    public CustomFieldGenerator(ObjectoGenerator objectoGenerator, Type objectType, Type fieldType, String fieldName, Supplier<Object> generator) {
        super(objectoGenerator);
        this.objectType = objectType;
        this.fieldType = fieldType;
        this.fieldName = fieldName;
        this.generator = generator;
    }

    @Override
    public boolean isSupported(Type actualType, GenerationContext context) {
        return objectType.equals(context.ownerType())
                && fieldType.equals(actualType)
                && fieldName.equals(context.fieldName());
    }

    @Override
    public Object generate(Type type, GenerationContext context) {
        return generator.get();
    }

}
