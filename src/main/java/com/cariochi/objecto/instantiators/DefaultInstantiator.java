package com.cariochi.objecto.instantiators;

import com.cariochi.objecto.generators.Context;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;

import static java.lang.reflect.Modifier.isPublic;

@RequiredArgsConstructor
abstract class DefaultInstantiator implements Function<Context, Object> {

    protected Object[] generateRandomParameters(Parameter[] parameters, Context context) {
        return Arrays.stream(parameters)
                .map(parameter -> {
                    final Context childContext = context.nextContext(parameter.getName(), parameter.getParameterizedType(), context.getType());
                    return childContext.generate();
                })
                .toArray();
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
