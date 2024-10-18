package com.cariochi.objecto.generators;

import com.cariochi.objecto.ObjectoRandom;
import com.cariochi.objecto.generators.model.FieldSettings;
import com.cariochi.objecto.settings.ObjectoSettings;
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

import static com.cariochi.objecto.settings.ObjectoSettings.DEFAULT_SETTINGS;
import static java.util.Arrays.asList;
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

    @Builder.Default private final ObjectoSettings settings = DEFAULT_SETTINGS;
    @Builder.Default private final List<FieldSettings> fieldSettings = new ArrayList<>();

    private final boolean dirty;

    @Setter private Object instance;

    public Context nextContext(String fieldName, ReflectoType type) {
        return withPrevious(this)
                .withFieldName(fieldName)
                .withType(type)
                .withInstance(null)
                .withDirty(false);
    }

    public ObjectoSettings getSettings() {
        ObjectoSettings settings = this.settings;
        for (BiFunction<ObjectoSettings, Context, ObjectoSettings> setting : getCurrentFieldSettings()) {
            settings = setting.apply(settings, this);
        }
        return settings;
    }

    public List<BiFunction<ObjectoSettings, Context, ObjectoSettings>> getCurrentFieldSettings() {
        return fieldSettings.stream()
                .filter(setting -> {
                    final Context parentContext = findPreviousContext(setting.getField()).map(Context::getPrevious).orElse(null);
                    return parentContext != null && setting.getType().equals(parentContext.getType());
                })
                .map(FieldSettings::getSettings)
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
