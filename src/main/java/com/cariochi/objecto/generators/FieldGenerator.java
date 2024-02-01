package com.cariochi.objecto.generators;

import com.cariochi.reflecto.types.TypeReflection;
import java.lang.reflect.Type;
import java.util.Optional;
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
        return fieldType.equals(context.getType().actualType())
                && context.findPreviousContext(substringBefore(expression, "=?"))
                .filter(cnxt -> {
                    final Type type = Optional.of(cnxt.getType())
                            .map(TypeReflection::getParentType)
                            .map(TypeReflection::actualType)
                            .orElse(null);
                    return ownerType.equals(type);
                })
                .isPresent();
    }

    @Override
    public Object generate(Context context) {
        return generator.get();
    }

}
