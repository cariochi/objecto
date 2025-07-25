package com.cariochi.objecto;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The \@UseFactory annotation is used to specify an array of factory classes that should be used.
 * This annotation can be applied to types (classes or interfaces) and is inherited by subclasses.
 */
@Target({TYPE})
@Retention(RUNTIME)
@Inherited
public @interface UseFactory {

    /**
     * Specifies the array of factory classes to be used.
     *
     * @return an array of Class objects representing the factory classes
     */
    Class<?>[] value();

}
