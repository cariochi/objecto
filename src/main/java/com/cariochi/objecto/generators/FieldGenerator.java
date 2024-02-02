package com.cariochi.objecto.generators;

import com.cariochi.reflecto.objects.methods.ObjectMethod;
import com.cariochi.reflecto.types.TypeReflection;
import java.lang.reflect.Type;
import java.util.Optional;
import lombok.Getter;

import static org.apache.commons.lang3.StringUtils.substringBefore;

@Getter
class FieldGenerator extends AbstractCustomGenerator {

    private final Class<?> ownerType;
    private final String expression;

    public FieldGenerator(ObjectoGenerator generator, Class<?> ownerType, String expression, ObjectMethod method) {
        super(method);
        this.ownerType = ownerType;
        this.expression = expression;
    }

    @Override
    public boolean isSupported(Context context) {
        return method.getReturnType().actualType().equals(context.getType().actualType())
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
        return invokeGenerationMethod(context);
    }

}
