package com.cariochi.objecto.instantiators;

import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.ObjectoGenerator;
import com.cariochi.reflecto.constructors.ReflectoConstructor;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

import static java.util.Comparator.comparingInt;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.joining;

@Slf4j
public class ConstructorProvider extends DefaultProvider {

    private static final Comparator<ReflectoConstructor> MODIFIERS_COMPARATOR = comparingInt(constructor -> getAccessibilityOrder(constructor.modifiers()));
    private static final Comparator<ReflectoConstructor> PARAMETERS_COMPARATOR = comparingLong(constructor -> constructor.parameters().size());

    public ConstructorProvider(ObjectoGenerator generator) {
        super(generator);
    }

    @Override
    public Object apply(Context context) {
        return context.getType().constructors().declared().stream()
                .sorted(MODIFIERS_COMPARATOR.thenComparing(PARAMETERS_COMPARATOR))
                .map(c -> newInstance(c, context))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    private Object newInstance(ReflectoConstructor constructor, Context context) {
        try {
            log.trace("Using constructor `{}`", constructor.toGenericString());
            final List<Object> args = generateRandomParameters(constructor.parameters(), context);
            log.trace("Construct parameters: `{}({})`", constructor.name(), args.stream().map(String::valueOf).collect(joining(", ")));
            return constructor.newInstance(args.toArray());
        } catch (Exception e) {
            return null;
        }
    }

}
