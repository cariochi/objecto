package com.cariochi.objecto.utils;

import com.cariochi.reflecto.Reflection;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import static com.cariochi.reflecto.Reflecto.reflect;
import static java.util.Collections.emptyMap;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBefore;

@Slf4j
@UtilityClass
public class ObjectUtils {

    public static <T> T modifyObject(T object, Map<String, Object[]> fieldValues) {
        if (object == null) {
            return null;
        }
        final Reflection reflection = reflect(object);

        final Map<String, Object[]> values;
        if (isCollection(object) || isArray(object)) {
            values = new HashMap<>();
            fieldValues.forEach((key, value) -> values.put("[*]." + key, value));
        } else {
            values = fieldValues;
        }

        values.entrySet().stream()
                .map(e -> splitName(e.getKey(), e.getValue(), reflection))
                .flatMap(map -> map.entrySet().stream())
                .forEach(e -> {
                    final String path = e.getKey();
                    try {
                        final String reflectoPath = path.endsWith(")") || path.endsWith("?") ? path : path + "=?";
                        reflection.invoke(reflectoPath, (Object[]) e.getValue());
                    } catch (Exception ex) {
                        log.info(
                                "Invalid @Modifier value '{}'. Please ensure that the specified parameter corresponds to a valid field in the {} class.",
                                path, object.getClass().getName()
                        );
                    }
                });

        return object;
    }

    private static Map<String, Object> splitName(String name, Object value, Reflection reflection) {
        if (name.contains("[*]")) {

            final String prefix = substringBefore(name, "[*]");
            final String suffix = substringAfter(name, "[*]");

            final Reflection javaField = isEmpty(prefix) ? reflection : reflection.get(prefix);

            try {

                final Object fieldValue = javaField.getValue();
                int size = getSize(fieldValue);
                final Map<String, Object> resultMap = new LinkedHashMap<>();
                for (int i = 0; i < size; i++) {
                    final String key = prefix + "[" + i + "]" + suffix;
                    final Map<String, Object> split = splitName(key, value, reflection);
                    resultMap.putAll(split);
                }
                return resultMap;

            } catch (Exception e) {
                log.info(
                        "Invalid @Modifier value '{}'. Please ensure that the specified parameter corresponds to a valid field in the {} class.",
                        name, reflection.getValue().getClass().getName()
                );
                return emptyMap();
            }
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put(name, value);
            return map;
        }
    }

    private static int getSize(Object o) {
        if (isArray(o)) {
            return Array.getLength(o);
        } else if (isCollection(o)) {
            return ((Collection<?>) o).size();
        } else {
            return -1;
        }
    }

    private static boolean isArray(Object o) {
        return o != null && o.getClass().isArray();
    }

    private static boolean isCollection(Object o) {
        return o != null && Collection.class.isAssignableFrom(o.getClass());
    }

}
