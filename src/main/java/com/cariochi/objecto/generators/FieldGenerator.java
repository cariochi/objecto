package com.cariochi.objecto.generators;


import com.cariochi.reflecto.methods.TargetMethod;
import com.cariochi.reflecto.types.ReflectoType;
import lombok.Getter;

import static org.apache.commons.lang3.StringUtils.substringBefore;

@Getter
class FieldGenerator extends AbstractCustomGenerator {

    private final ReflectoType ownerType;
    private final String expression;

    public FieldGenerator(ReflectoType ownerType, String expression, TargetMethod method) {
        super(method);
        this.ownerType = ownerType;
        this.expression = expression;
    }

    @Override
    public boolean isSupported(Context context) {
        return !context.isDirty()
               && context.getType().is(method.returnType().actualType())
               && context.findPreviousContext(substringBefore(expression, "=?"))
                       .map(Context::getPrevious)
                       .map(Context::getType)
                       .filter(type -> type.is(ownerType.actualType()))
                       .isPresent();
    }

    @Override
    public Object generate(Context context) {
        return invokeGenerationMethod(context);
    }

}
