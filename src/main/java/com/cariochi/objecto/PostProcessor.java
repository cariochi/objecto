package com.cariochi.objecto;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The PostProcessor annotation is used to mark a method as a post-processing method.
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface PostProcessor {

}
