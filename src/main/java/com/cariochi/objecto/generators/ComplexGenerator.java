package com.cariochi.objecto.generators;

import com.cariochi.objecto.modifiers.ObjectoModifier;
import java.util.Map;
import java.util.function.Supplier;
import lombok.Getter;

@Getter
class ComplexGenerator extends AbstractGenerator {

    private final Class<?> ownerType;
    private final String expression;
    private final Supplier<Object> generator;

    public ComplexGenerator(ObjectoGenerator generator, Class<?> ownerType, String expression, Supplier<Object> generator1) {
        super(generator);
        this.ownerType = ownerType;
        this.expression = expression;
        this.generator = generator1;
    }

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
