package com.cariochi.objecto.instantiators;

import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.ObjectoGenerator;
import com.cariochi.reflecto.types.TypeReflection;
import com.cariochi.reflecto.types.methods.StaticMethod;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.joining;

@Slf4j
public class StaticMethodInstantiator extends DefaultInstantiator {

    public StaticMethodInstantiator(ObjectoGenerator generator) {
        super(generator);
    }

    @Override
    public Object apply(Context context) {
        final TypeReflection type = context.getType();
        return type.methods().staticMethods().stream()
                .filter(StaticMethod::isPublic)
                .filter(m -> type.isAssignableFrom(m.getReturnType().actualType()))
                .sorted(comparingInt((StaticMethod m) -> getAccessibilityOrder(m.getModifiers())).thenComparingInt(m -> m.getParameters().size()))
                .map(method -> newInstance(method, context))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    private Object newInstance(StaticMethod staticConstructor, Context context) {
        try {
            log.trace("Using static method `{}`", staticConstructor.toGenericString());
            final List<Object> args = generateRandomParameters(staticConstructor.getParameters(), context);
            log.trace("Static method parameters: `{}({})`", staticConstructor.getName(), args.stream().map(String::valueOf).collect(joining(", ")));
            return staticConstructor.invoke(args.toArray());
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return null;
        }
    }

}
