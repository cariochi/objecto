package com.cariochi.objecto;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The Seed annotation is used to specify a seed value for methods or types.
 */
@Target({TYPE, METHOD})
@Retention(RUNTIME)
@Inherited
public @interface Seed {

    /**
     * Specifies the seed value.
     *
     * @return the seed value
     */
    long value();

}
