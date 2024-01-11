package com.cariochi.objecto.generators;

import java.lang.reflect.Type;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static org.apache.commons.lang3.StringUtils.substringBefore;

@Getter
@RequiredArgsConstructor
class FieldGenerator implements Generator {

    private final Class<?> ownerType;
    private final Type fieldType;
    private final String expression;
    private final Supplier<Object> generator;

    @Override
    public boolean isSupported(Context context) {
        return fieldType.equals(context.getType())
                && context.findPreviousContext(substringBefore(expression, "=?"))
                .filter(cnxt -> ownerType.equals(cnxt.getOwnerType()))
                .isPresent();
    }

    @Override
    public Object generate(Context context) {
        return generator.get();
    }

}
