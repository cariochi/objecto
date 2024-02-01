package com.cariochi.objecto.generators;

import java.lang.reflect.Type;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
class TypeGenerator implements Generator {

    private final Type fieldType;
    private final Supplier<Object> generator;

    @Override
    public boolean isSupported(Context context) {
        return fieldType.equals(context.getType().actualType());
    }

    @Override
    public Object generate(Context context) {
        return generator.get();
    }

}
