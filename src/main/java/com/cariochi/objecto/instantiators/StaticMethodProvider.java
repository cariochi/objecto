package com.cariochi.objecto.instantiators;

import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.ObjectoGenerator;
import com.cariochi.reflecto.methods.ReflectoMethod;
import com.cariochi.reflecto.methods.TargetMethod;
import com.cariochi.reflecto.types.ReflectoType;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

import static java.util.Comparator.comparingInt;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.joining;

@Slf4j
public class StaticMethodProvider extends DefaultProvider {

    private static final Comparator<TargetMethod> MODIFIERS_COMPARATOR = comparingInt(method -> getAccessibilityOrder(method.modifiers()));
    private static final Comparator<TargetMethod> PARAMETERS_COMPARATOR = comparingLong(method -> method.parameters().size());

    public StaticMethodProvider(ObjectoGenerator generator) {
        super(generator);
    }

    @Override
    public Object apply(Context context) {
        final ReflectoType type = context.getType();
        return type.methods().stream()
                .filter(method -> method.modifiers().isPublic())
                .filter(method -> method.modifiers().isStatic())
                .filter(method -> type.isAssignableFrom(method.returnType().actualType()))
                .map(ReflectoMethod::asStatic)
                .sorted(MODIFIERS_COMPARATOR.thenComparing(PARAMETERS_COMPARATOR))
                .map(method -> newInstance(method, context))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    private Object newInstance(TargetMethod staticConstructor, Context context) {
        try {
            log.trace("Using static method `{}`", staticConstructor.toGenericString());
            final List<Object> args = generateRandomParameters(staticConstructor.parameters(), context);
            log.trace("Static method parameters: `{}({})`", staticConstructor.name(), args.stream().map(String::valueOf).collect(joining(", ")));
            return staticConstructor.invoke(args.toArray());
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return null;
        }
    }

}
