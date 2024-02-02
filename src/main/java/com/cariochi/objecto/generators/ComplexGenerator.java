package com.cariochi.objecto.generators;

import com.cariochi.objecto.modifiers.ObjectoModifier;
import com.cariochi.reflecto.objects.methods.ObjectMethod;
import java.util.Map;
import lombok.Getter;

@Getter
class ComplexGenerator extends AbstractCustomGenerator {

    private final Class<?> ownerType;
    private final String expression;

    public ComplexGenerator(ObjectoGenerator generator, Class<?> ownerType, String expression, ObjectMethod method) {
        super(method);
        this.ownerType = ownerType;
        this.expression = expression;
    }

    @Override
    public boolean isSupported(Context context) {
        return ownerType.equals(context.getType().asClass());
    }

    @Override
    public Object generate(Context context) {
        final Object value = invokeGenerationMethod(context);
        final Object instance = context.getInstance();
        ObjectoModifier.modifyObject(instance, Map.of(expression, new Object[]{value}));
        return instance;
    }

}
