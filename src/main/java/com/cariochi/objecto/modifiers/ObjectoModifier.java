package com.cariochi.objecto.modifiers;

import com.cariochi.reflecto.invocations.model.Reflection;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import static com.cariochi.reflecto.Reflecto.reflect;
import static java.util.stream.Collectors.toMap;

@Slf4j
@UtilityClass
public class ObjectoModifier {

    public static <T> T modifyObject(T object, Map<String, Object[]> fieldValues) {
        if (object == null) {
            return null;
        }
        final Reflection reflection = reflect(object);

        final Map<String, Object[]> values = isCollection(object) || isArray(object)
                ? fieldValues.entrySet().stream().collect(toMap(e -> "[*]." + e.getKey(), Entry::getValue))
                : fieldValues;

        values.forEach((expression, value) -> {
            final String reflectoPath = expression.endsWith(")") || expression.endsWith("?") ? expression : expression + "=?";
            try {
                reflection.perform(reflectoPath, value);
            } catch (Exception ex) {
                log.debug(
                        "Cannot apply @Modify expression '{}' to {}. Tried Reflecto path '{}' with {} argument(s). Cause: {}",
                        expression,
                        object.getClass().getName(),
                        reflectoPath,
                        value == null ? 0 : value.length,
                        ex.getMessage()
                );
            }
        });

        return object;
    }

    private static boolean isArray(Object o) {
        return o != null && o.getClass().isArray();
    }

    private static boolean isCollection(Object o) {
        return o != null && Collection.class.isAssignableFrom(o.getClass());
    }

}
