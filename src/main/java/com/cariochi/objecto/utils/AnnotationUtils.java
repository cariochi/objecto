package com.cariochi.objecto.utils;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ClassUtils.getAllInterfaces;
import static org.apache.commons.lang3.ClassUtils.getAllSuperclasses;

@UtilityClass
public class AnnotationUtils {

    public static <T extends Annotation> List<T> findAllAnnotations(Class<?> targetClass, Class<T> annotationClass) {
        return Stream.of(List.of(targetClass), getAllInterfaces(targetClass), getAllSuperclasses(targetClass))
                .flatMap(List::stream)
                .map(i -> i.getAnnotation(annotationClass))
                .filter(Objects::nonNull)
                .collect(toList());
    }

}
