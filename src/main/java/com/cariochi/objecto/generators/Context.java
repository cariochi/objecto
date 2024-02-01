package com.cariochi.objecto.generators;

import com.cariochi.objecto.settings.Settings;
import com.cariochi.objecto.utils.Random;
import com.cariochi.reflecto.types.TypeReflection;
import java.lang.reflect.Type;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;
import lombok.Getter;
import lombok.With;

import static com.cariochi.reflecto.Reflecto.reflectType;
import static java.util.Arrays.asList;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.replace;
import static org.apache.commons.lang3.StringUtils.split;

@Getter
@With(PRIVATE)
public class Context {

    private final Random random;
    private final String fieldName;
    private final TypeReflection type;
    private final ObjectoGenerator generator;
    private final Settings settings;
    private final Context previous;
    private Object instance;

    public Context(Type type, Settings settings, ObjectoGenerator generator, long seed) {
        this.random = new Random(seed);
        this.fieldName = "";
        this.type = reflectType(type);
        this.previous = null;
        this.settings = settings;
        this.generator = generator;
    }

    private Context(Random random, String fieldName, TypeReflection type, ObjectoGenerator generator, Settings settings, Context previous, Object instance) {
        this.random = random;
        this.fieldName = fieldName;
        this.generator = generator;
        this.settings = settings;
        this.previous = previous;
        this.instance = instance;

        this.type = type.isTypeVariable() && instance != null
                ? new TypeReflection(instance.getClass(), type.getParentType())
                : type;
    }

    public Context nextContext(String fieldName, TypeReflection type, Object instance) {
        return withPrevious(this)
                .withFieldName(fieldName)
                .withType(type)
                .withInstance(instance);
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

    public int getRecursionDepth(Type type) {
        final Set<Object> instances = java.util.Collections.newSetFromMap(new IdentityHashMap<>());
        collectInstances(type, instances);
        return instances.size();
    }

    private void collectInstances(Type type, Set<Object> instances) {
        if (getType().actualType().equals(type) && instance != null) {
            instances.add(instance);
        }
        if (previous != null) {
            previous.collectInstances(type, instances);
        }
    }

    public Object generate() {
        return generator.generate(this);
    }

    public Object newInstance() {
        instance = generator.newInstance(this);
        return instance;
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

    public Random getRandom() {
        return random;
    }

}
