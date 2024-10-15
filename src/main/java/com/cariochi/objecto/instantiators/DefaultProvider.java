package com.cariochi.objecto.instantiators;

import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.ObjectoGenerator;
import com.cariochi.reflecto.base.ReflectoModifiers;
import com.cariochi.reflecto.parameters.ReflectoParameters;
import java.util.List;

import static java.util.stream.Collectors.toList;

abstract class DefaultProvider extends AbstractProvider {

    protected DefaultProvider(ObjectoGenerator generator) {
        super(generator);
    }

    protected List<Object> generateRandomParameters(ReflectoParameters parameters, Context context) {
        return parameters.stream()
                .map(parameter -> {
                    final Context childContext = context.nextContext(parameter.name(), parameter.type());
                    return generate(childContext);
                })
                .collect(toList());
    }

    protected static int getAccessibilityOrder(ReflectoModifiers modifiers) {
        if (modifiers.isPublic()) {
            return 0;
        } else if (modifiers.isProtected()) {
            return 1;
        } else if (modifiers.isPrivate()) {
            return 2;
        } else {
            return 3;
        }
    }

}
