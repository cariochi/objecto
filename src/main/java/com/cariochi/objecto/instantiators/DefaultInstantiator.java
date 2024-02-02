package com.cariochi.objecto.instantiators;

import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.ObjectoGenerator;
import com.cariochi.reflecto.types.methods.MethodParameter;
import java.lang.reflect.Modifier;
import java.util.List;

import static java.lang.reflect.Modifier.isPublic;
import static java.util.stream.Collectors.toList;

abstract class DefaultInstantiator extends AbstractInstantiator {

    public DefaultInstantiator(ObjectoGenerator generator) {
        super(generator);
    }

    protected List<Object> generateRandomParameters(List<MethodParameter> parameters, Context context) {
        return parameters.stream()
                .map(parameter -> {
                    final Context childContext = context.nextContext(parameter.getName(), parameter.getType());
                    return generate(childContext);
                })
                .collect(toList());
    }

    protected static int getAccessibilityOrder(int modifiers) {
        if (isPublic(modifiers)) {
            return 0;
        } else if (Modifier.isProtected(modifiers)) {
            return 1;
        } else if (Modifier.isPrivate(modifiers)) {
            return 2;
        } else {
            return 3;
        }
    }

}
