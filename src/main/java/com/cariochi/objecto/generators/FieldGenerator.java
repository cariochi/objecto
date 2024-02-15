package com.cariochi.objecto.generators;


import com.cariochi.reflecto.methods.TargetMethod;
import com.cariochi.reflecto.types.ReflectoType;
import java.lang.reflect.Type;
import java.util.Optional;
import lombok.Getter;

import static org.apache.commons.lang3.StringUtils.substringBefore;

@Getter
class FieldGenerator extends AbstractCustomGenerator {

    private final Class<?> ownerType;
    private final String expression;

    public FieldGenerator(Class<?> ownerType, String expression, TargetMethod method) {
        super(method);
        this.ownerType = ownerType;
        this.expression = expression;
    }

    @Override
    public boolean isSupported(Context context) {
        return method.returnType().equals(context.getType())
               && context.findPreviousContext(substringBefore(expression, "=?"))
                       .map(Context::getPrevious)
                       .filter(cnxt -> {
                           final Type type = Optional.of(cnxt.getType())
                                   .map(ReflectoType::actualType)
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
