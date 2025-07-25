package com.cariochi.objecto;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The \@Construct annotation is used to designate a type for creating instances of a specific type.
 * <p>
 * These methods are necessary when Objecto cannot create an object, for example, if there is no public constructor, no static factory type, or if the type is an abstract
 * class or an interface.
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface Construct {

}
