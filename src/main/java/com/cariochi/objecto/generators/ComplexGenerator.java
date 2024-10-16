package com.cariochi.objecto.generators;

import com.cariochi.objecto.modifiers.ObjectoModifier;
import com.cariochi.reflecto.methods.TargetMethod;
import com.cariochi.reflecto.types.ReflectoType;
import java.util.Map;
import lombok.Getter;

@Getter
class ComplexGenerator extends AbstractCustomGenerator {

    private final ReflectoType ownerType;
    private final String expression;

    public ComplexGenerator(ReflectoType ownerType, String expression, TargetMethod method) {
        super(method);
        this.ownerType = ownerType;
        this.expression = expression;
    }

    @Override
    public boolean isSupported(Context context) {
        return !context.isDirty() && ownerType.is(context.getType().actualClass());
    }

    @Override
    public Object generate(Context context) {
        final Object value = invokeGenerationMethod(context);
        final Object instance = context.getInstance();
        ObjectoModifier.modifyObject(instance, Map.of(expression, new Object[]{value}));
        return instance;
    }

}
