package com.cariochi.objecto;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The @Reference annotation is used for ensuring the proper establishment of bidirectional links and managing complex inter-entity connections during random object
 * generation.
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface Reference {

    /**
     * Specifies the references for the annotated type.
     *
     * @return an array of reference values
     */
    String[] value();

}
