package com.cariochi.objecto.generators;

import com.cariochi.objecto.ObjectoSettings;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;
import lombok.experimental.Accessors;

import static com.cariochi.objecto.utils.GenericTypeUtils.getRawClass;

@Getter
@Accessors(fluent = true)
@With
@AllArgsConstructor
@RequiredArgsConstructor
public class GenerationContext {

    private final ObjectoSettings settings;

    private Type ownerType;
    private String fieldName;
    private Object instance;
    private String path = "<ROOT>";
    private Map<Type, Integer> types = new HashMap<>();

    public GenerationContext withField(String field) {

        final StringBuilder newPath = new StringBuilder(path);
        if (!field.startsWith("[")) {
            newPath.append(".");
        }
        newPath.append(field);

        return withFieldName(field).withPath(newPath.toString());
    }

    public GenerationContext withType(Type type) {
        final Class<?> rawClass = getRawClass(type, null);
        if (rawClass.isArray() || Iterable.class.isAssignableFrom(rawClass) || Map.class.isAssignableFrom(rawClass)) {
            return this;
        } else {
            final Map<Type, Integer> newTypes = new HashMap<>(types);
            newTypes.compute(type, (t, c) -> c == null ? 1 : c + 1);
            return withTypes(newTypes);
        }
    }

    public boolean isLimitReached(Type type) {
        return types.getOrDefault(type, 0) >= settings.depth();
    }

}
