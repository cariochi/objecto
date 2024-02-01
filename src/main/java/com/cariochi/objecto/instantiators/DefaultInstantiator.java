package com.cariochi.objecto.instantiators;

import com.cariochi.objecto.generators.Context;
import com.cariochi.reflecto.types.methods.MethodParameter;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;

import static java.lang.reflect.Modifier.isPublic;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
abstract class DefaultInstantiator implements Function<Context, Object> {

    protected List<Object> generateRandomParameters(List<MethodParameter> parameters, Context context) {
        return parameters.stream()
                .map(parameter -> {
                    final Context childContext = context.nextContext(parameter.getName(), parameter.getType(), null);
                    return childContext.generate();
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
