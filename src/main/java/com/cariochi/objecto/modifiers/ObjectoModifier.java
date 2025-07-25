package com.cariochi.objecto.modifiers;

import com.cariochi.reflecto.invocations.model.Reflection;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

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
            try {
                final String reflectoPath = expression.endsWith(")") || expression.endsWith("?") ? expression : expression + "=?";
                reflection.perform(reflectoPath, value);
            } catch (Exception ex) {
                log.warn(
                        "Invalid @Modify type '{}'. Please ensure that the specified parameter corresponds to a valid field in the {} class.",
                        expression, object.getClass().getName()
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
