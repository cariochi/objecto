package com.cariochi.objecto.generators;

import com.cariochi.objecto.config.ObjectoConfig;
import com.cariochi.objecto.generators.model.ConfigFunction;
import com.cariochi.objecto.random.ObjectoRandom;
import com.cariochi.reflecto.types.ReflectoType;
import java.util.ArrayList;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.With;

import static com.cariochi.objecto.config.ObjectoConfig.DEFAULT_SETTINGS;
import static org.apache.commons.lang3.StringUtils.replace;
import static org.apache.commons.lang3.StringUtils.split;

@Getter
@With
@Builder
@AllArgsConstructor
public class Context {

    @Builder.Default private final ObjectoRandom random = new ObjectoRandom();
    @Builder.Default private final String fieldName = "";
    private final ReflectoType type;
    private final Context previous;

    @Builder.Default private final ObjectoConfig config = DEFAULT_SETTINGS;
    @Builder.Default private final List<ConfigFunction> fieldConfigs = new ArrayList<>();

    private final boolean dirty;

    @Setter private Object instance;

    public Context nextContext(String fieldName, ReflectoType type) {
        return withPrevious(this)
                .withFieldName(fieldName)
                .withType(type)
                .withInstance(null)
                .withDirty(false);
    }

    public ObjectoConfig getConfig() {
        ObjectoConfig config = this.config;
        for (BiFunction<ObjectoConfig, Context, ObjectoConfig> func : getCurrentFieldConfigs()) {
            config = func.apply(config, this);
        }
        return config;
    }

    public List<BiFunction<ObjectoConfig, Context, ObjectoConfig>> getCurrentFieldConfigs() {
        return fieldConfigs.stream()
                .filter(configFunction -> {
                    final Context parentContext = findPreviousContext(configFunction.getField()).map(Context::getPrevious).orElse(null);
                    return parentContext != null && configFunction.getType().equals(parentContext.getType());
                })
                .map(ConfigFunction::getFunction)
                .toList();
    }

    public ReflectoType getType() {
        return type.actualClass() == null && instance != null
                ? type.reflect(instance.getClass())
                : type;
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
        final Deque<String> fields = new LinkedList<>(List.of(split(replace(path, "[", ".["), ".")));
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
