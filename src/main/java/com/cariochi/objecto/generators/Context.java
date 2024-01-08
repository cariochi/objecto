package com.cariochi.objecto.generators;

import com.cariochi.objecto.settings.Settings;
import com.cariochi.objecto.utils.GenericTypeUtils;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

import static com.cariochi.objecto.utils.GenericTypeUtils.getRawType;
import static java.util.Arrays.asList;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.replace;
import static org.apache.commons.lang3.StringUtils.split;

@Getter
@With(PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class Context {

    private final ObjectoGenerator generator;
    private final Settings settings;
    private final String fieldName;
    private final Type type;
    private final Type ownerType;
    private final Context previous;
    private Object instance;

    Context(Type type, Settings settings, ObjectoGenerator generator) {
        this.fieldName = "";
        this.type = type;
        this.ownerType = null;
        this.previous = null;
        this.settings = settings;
        this.generator = generator;
    }

    public Context nextContext(String fieldName, Type fieldType, Type ownerType) {
        return nextContext(fieldName, fieldType, ownerType, null);
    }

    public Context nextContext(String fieldName, Type fieldType, Type ownerType, Object instance) {
        return withPrevious(this)
                .withFieldName(fieldName)
                .withType(fieldType)
                .withOwnerType(ownerType)
                .withInstance(instance);
    }

    public Type getType() {
        return Optional.ofNullable(getRawType(type, ownerType))
                .or(() -> Optional.ofNullable(instance).map(Object::getClass))
                .orElse(null);
    }

    public Class<?> getRawClass() {
        return GenericTypeUtils.getRawClass(getType(), ownerType);
    }

    public int getDepth() {
        if (previous == null) {
            return 1;
        } else if (fieldName.startsWith("[")) {
            return previous.getDepth();
        } else {
            return previous.getDepth() + 1;
        }
    }

    public int getTypeDepth(Type type) {
        final HashSet<Object> instances = new HashSet<>();
        collectInstances(type, instances);
        return instances.size();
    }

    private void collectInstances(Type type, Set<Object> instances) {
        if (getType().equals(type) && instance != null) {
            instances.add(instance);
        }
        if (previous != null) {
            previous.collectInstances(type, instances);
        }
    }

    public Object findInstance(Type type) {
        if (getType().equals(type) && instance != null) {
            return instance;
        }
        if (previous != null) {
            return previous.findInstance(type);
        } else {
            return null;
        }
    }

    public Object generate() {
        return generator.generate(this);
    }

    public Object newInstance() {
        instance = generator.newInstance(this);
        return instance;
    }

    public boolean isCollection() {
        return Collection.class.isAssignableFrom(getRawClass());
    }

    public boolean isMap() {
        return Map.class.isAssignableFrom(getRawClass());
    }

    public String getPath() {
        final String path = previous == null ? "" : previous.getPath();
        if (path.isEmpty()) {
            return fieldName;
        } else if (fieldName.contains("[")) {
            return path + fieldName;
        } else {
            return path + "." + fieldName;
        }
    }

    public Optional<Context> stepsBack(int steps) {
        if (steps == 0) {
            return Optional.of(this);
        } else if (previous != null) {
            return previous.stepsBack(steps - 1);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Context> findPreviousContext(String path) {
        final Deque<String> fields = new LinkedList<>(asList(split(replace(path, "[", ".["), ".")));
        return findPreviousContext(fields);
    }

    private Optional<Context> findPreviousContext(Deque<String> fields) {
        final String field = fields.removeLast();
        if (fieldName.equals(field) || (field.equals("[*]") && fieldName.startsWith("[") && fieldName.endsWith("]"))) {
            if (fields.isEmpty()) {
                return Optional.of(this);
            } else if (previous == null) {
                return Optional.empty();
            } else {
                return previous.findPreviousContext(fields);
            }
        } else {
            return Optional.empty();
        }
    }

}
