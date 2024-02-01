package com.cariochi.objecto.generators;

import com.cariochi.objecto.modifiers.ObjectoModifier;
import java.util.Map;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
class ComplexGenerator implements Generator {

    private final Class<?> ownerType;
    private final String expression;
    private final Supplier<Object> generator;

    @Override
    public boolean isSupported(Context context) {
        return ownerType.equals(context.getType().asClass());
    }

    @Override
    public Object generate(Context context) {
        final Object value = generator.get();
        final Object instance = context.getInstance();
        ObjectoModifier.modifyObject(instance, Map.of(expression, new Object[]{value}));
        return instance;
    }

}
