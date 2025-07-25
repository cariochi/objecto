package com.cariochi.objecto;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This annotation is used to mark a type or parameter and specify what it should modify.
 */
@Target({METHOD, PARAMETER})
@Retention(RUNTIME)
public @interface Modify {

    /**
     * Specifies the field or type to which modifications should be applied.
     *
     * @return an array of modification values
     */
    String[] value();

}
