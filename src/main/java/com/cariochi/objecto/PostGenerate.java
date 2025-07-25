package com.cariochi.objecto;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The PostGenerate annotation is used to mark a type as a post-processing type.
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface PostGenerate {

}
