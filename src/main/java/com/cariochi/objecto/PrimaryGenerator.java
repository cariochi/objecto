package com.cariochi.objecto;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Marks the primary generator method for its return type.
 * <p>
 * Objecto can use factory methods as type generators when it needs the same type inside another
 * generated object. If a factory exposes more than one generator method for the same return type, mark
 * the intended default with this annotation.
 *
 * <pre>{@code
 * interface UserFactory {
 *
 *     @PrimaryGenerator
 *     @Datafaker(field = "fullName", expression = Datafaker.Base.Name.FULL_NAME)
 *     User createUser();
 * }
 * }</pre>
 *
 * <p>
 * Custom implementations may have no parameters, one {@code ObjectoRandom} parameter, or one
 * {@code java.util.Random} parameter.
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface PrimaryGenerator {

}
