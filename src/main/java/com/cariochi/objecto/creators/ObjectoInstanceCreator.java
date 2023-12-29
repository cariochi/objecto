package com.cariochi.objecto.creators;

import com.cariochi.objecto.generators.GenerationContext;
import com.cariochi.objecto.generators.ObjectoGenerator;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectoInstanceCreator {

    private final Map<String, List<Creator>> creators = new LinkedHashMap<>();

    public ObjectoInstanceCreator(ObjectoGenerator objectoGenerator) {
        creators.put("custom", new ArrayList<>());
        creators.put("default", List.of(
                new InterfaceCreator(),
                new StaticMethodCreator(objectoGenerator),
                new ConstructorCreator(objectoGenerator)
        ));
    }

    public Object createInstance(Type type, GenerationContext context) {
        return creators.values().stream().flatMap(List::stream)
                .map(creator -> creator.apply(type, context))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    public void addCustomCreator(Type type, Supplier<Object> creator) {
        creators.get("custom").add((actualType, context) -> type.equals(actualType) ? creator.get() : null);
    }

}
