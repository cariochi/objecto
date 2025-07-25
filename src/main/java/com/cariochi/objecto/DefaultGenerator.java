package com.cariochi.objecto;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The \@DefaultGenerator annotation is used to mark a type as a factory type for creating randomized instances of a specific type, which will be used whenever an
 * instance of the specified type needs to be generated.
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface DefaultGenerator {

}
