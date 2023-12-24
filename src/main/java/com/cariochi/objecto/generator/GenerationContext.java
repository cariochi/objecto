package com.cariochi.objecto.generator;

import com.cariochi.objecto.ObjectoSettings;
import java.lang.reflect.Type;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.With;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import static java.util.stream.Collectors.joining;

@Getter
@Accessors(fluent = true)
@Builder
@With
@AllArgsConstructor
public class GenerationContext {

    private int depth;
    private Type ownerType;
    private String fieldName;
    private Object instance;
    private ObjectoSettings settings;

    @Builder.Default
    private String path = "";

    public GenerationContext next() {
        return this.withDepth(depth - 1);
    }

    public GenerationContext withField(String field) {
        final String newPath = Stream.of(path, field).filter(StringUtils::isNoneBlank).collect(joining("."));
        return this.withFieldName(field).withPath(newPath.replace(".[", "[").replace(".(", "("));
    }

}
