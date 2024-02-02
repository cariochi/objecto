package com.cariochi.objecto.generators;

import java.lang.reflect.Type;
import java.util.function.Supplier;
import lombok.Getter;

@Getter
class TypeGenerator extends AbstractGenerator {

    private final Type fieldType;
    private final Supplier<Object> generator;

    public TypeGenerator(ObjectoGenerator generator, Type fieldType, Supplier<Object> supplier) {
        super(generator);
        this.fieldType = fieldType;
        this.generator = supplier;
    }

    @Override
    public boolean isSupported(Context context) {
        return fieldType.equals(context.getType().actualType());
    }

    @Override
    public Object generate(Context context) {
        return generator.get();
    }

}
