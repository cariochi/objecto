package com.cariochi.objecto.generators;

import java.lang.reflect.Type;
import java.util.function.Supplier;

class CustomTypeGenerator extends Generator {

    private final Type type;
    private final Supplier<Object> generator;

    public CustomTypeGenerator(ObjectoGenerator objectoGenerator, Type type, Supplier<Object> generator) {
        super(objectoGenerator);
        this.type = type;
        this.generator = generator;
    }

    @Override
    public boolean isSupported(Type actualType, GenerationContext context) {
        return type.equals(actualType);
    }

    @Override
    public Object generate(Type type, GenerationContext context) {
        return generator.get();
    }

}
