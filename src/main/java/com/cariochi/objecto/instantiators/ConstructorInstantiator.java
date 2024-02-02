package com.cariochi.objecto.instantiators;

import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.ObjectoGenerator;
import com.cariochi.reflecto.types.methods.TypeConstructor;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.joining;

@Slf4j
public class ConstructorInstantiator extends DefaultInstantiator {

    public ConstructorInstantiator(ObjectoGenerator generator) {
        super(generator);
    }

    @Override
    public Object apply(Context context) {
        return context.getType().constructors().all().stream()
                .sorted(comparingInt((TypeConstructor constructor) -> getAccessibilityOrder(constructor.getModifiers())).thenComparingInt(c -> c.getParameters().size()))
                .map(c -> newInstance(c, context))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    private Object newInstance(TypeConstructor constructor, Context context) {
        try {
            log.trace("Using constructor `{}`", constructor.toGenericString());
            final List<Object> args = generateRandomParameters(constructor.getParameters(), context);
            log.trace("Constructor parameters: `{}({})`", constructor.getName(), args.stream().map(String::valueOf).collect(joining(", ")));
            return constructor.newInstance(args.toArray());
        } catch (Exception e) {
            return null;
        }
    }

}
