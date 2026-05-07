package com.cariochi.objecto;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Marks a method that post-processes generated objects.
 * <p>
 * The first parameter determines the generated type to post-process. The method must return
 * {@code void}; it may optionally accept {@code ObjectoRandom} or {@code java.util.Random} as a second
 * parameter.
 *
 * <pre>{@code
 * interface UserFactory {
 *
 *     @PostProcess
 *     private void normalize(User user, ObjectoRandom random) {
 *         user.setEmail(user.getUsername() + "@example.test");
 *     }
 * }
 * }</pre>
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface PostProcess {

}
