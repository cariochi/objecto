package com.cariochi.objecto.generators;

import com.cariochi.reflecto.types.ReflectoType;
import java.util.Optional;

public class OptionalGenerator extends AbstractObjectsGenerator {

    public OptionalGenerator(ObjectoGenerator generator) {
        super(generator);
    }

    @Override
    public boolean isSupported(Context context) {
        return context.getType().is(Optional.class);
    }

    @Override
    public Object generate(Context context) {
        final ReflectoType elementType = context.getType().arguments().get(0);
        final Context valueContext = context.nextContext("[type]", elementType);
        final Object object = generator.generate(valueContext);
        return Optional.of(object);
    }
}
