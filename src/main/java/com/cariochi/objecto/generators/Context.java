package com.cariochi.objecto.generators;

import com.cariochi.objecto.Objecto;
import com.cariochi.objecto.ObjectoRandom;
import com.cariochi.objecto.settings.Settings;
import com.cariochi.reflecto.types.ReflectoType;
import java.lang.reflect.Type;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.With;

import static com.cariochi.reflecto.Reflecto.reflect;
import static java.util.Arrays.asList;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.replace;
import static org.apache.commons.lang3.StringUtils.split;

@Getter
@With(PRIVATE)
public class Context {

    private final ObjectoRandom random;
    private final String fieldName;
    private final ReflectoType type;
    private final Settings settings;
    private final Context previous;
    @Setter private Object instance;

    public Context(Type type) {
        this(type, Objecto.defaultSettings());
    }

    public Context(Type type, Settings settings) {
        this(type, settings, new ObjectoRandom());
    }

    public Context(Type type, Settings settings, ObjectoRandom random) {
        this(random, "", reflect(type), settings, null, null);
    }

    private Context(ObjectoRandom random, String fieldName, ReflectoType type, Settings settings, Context previous, Object instance) {
        this.random = random;
        this.fieldName = fieldName;
        this.settings = settings;
        this.previous = previous;
        this.instance = instance;

        this.type = type.actualClass() == null && instance != null
                ? type.reflect(instance.getClass())
                : type;
    }

    public Context nextContext(String fieldName, ReflectoType type) {
        return withPrevious(this)
                .withFieldName(fieldName)
                .withType(type)
                .withInstance(null);
    }

    public Context withFieldSettings(Settings settings) {
        return withSettings(settings);
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

    public int getRecursionDepth(ReflectoType type) {
        final Set<Object> instances = java.util.Collections.newSetFromMap(new IdentityHashMap<>());
        collectInstances(type, instances);
        return instances.size();
    }

    private void collectInstances(ReflectoType type, Set<Object> instances) {
        if (getType().equals(type) && instance != null) {
            instances.add(instance);
        }
        if (previous != null) {
            previous.collectInstances(type, instances);
        }
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

    public Optional<Context> findPreviousContext(String path) {
        final Deque<String> fields = new LinkedList<>(asList(split(replace(path, "[", ".["), ".")));
        return findPreviousContext(fields);
    }

    private Optional<Context> findPreviousContext(Deque<String> fields) {
        final String field = fields.removeLast();
        if (!fieldName.equals(field) && (!field.equals("[*]") || !fieldName.startsWith("[") || !fieldName.endsWith("]"))) {
            return Optional.empty();
        } else if (fields.isEmpty()) {
            return Optional.of(this);
        } else if (previous == null) {
            return Optional.empty();
        } else {
            return previous.findPreviousContext(fields);
        }
    }

}
