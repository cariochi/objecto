package com.cariochi.objecto;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The FieldFactory annotation is used to specify the type and field name for a factory method that generates field values.
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface FieldFactory {

    /**
     * Specifies the type of the field.
     *
     * @return the class type of the field
     */
    Class<?> type();

    /**
     * Specifies the name of the field.
     *
     * @return the name of the field
     */
    String field();

}
