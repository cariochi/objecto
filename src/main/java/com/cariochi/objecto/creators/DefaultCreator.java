package com.cariochi.objecto.creators;

import com.cariochi.objecto.generators.GenerationContext;
import com.cariochi.objecto.generators.ObjectoGenerator;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;

import static java.lang.reflect.Modifier.isPublic;

@RequiredArgsConstructor
abstract class DefaultCreator implements Creator {

    private final ObjectoGenerator objectoGenerator;

    protected Object[] generateRandomParameters(Parameter[] parameters, Type ownerType, GenerationContext context) {
        return Arrays.stream(parameters)
                .map(parameter -> {
                    final GenerationContext childContext = context.next()
                            .withOwnerType(ownerType)
                            .withFieldName(parameter.getName());
                    return objectoGenerator.generateInstance(parameter.getParameterizedType(), childContext);
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
