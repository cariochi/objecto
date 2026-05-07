package com.cariochi.objecto;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Sets a deterministic seed for Objecto generation.
 * <p>
 * The annotation can be placed on a factory type, factory method, or JUnit test method when
 * {@code ObjectoExtension} is used.
 *
 * <pre>{@code
 * @Seed(42)
 * interface IssueFactory {
 *     Issue createIssue();
 * }
 * }</pre>
 *
 * <p>
 * Method-level seeds override type-level seeds for that generation call.
 */
@Target({TYPE, METHOD})
@Retention(RUNTIME)
@Inherited
public @interface Seed {

    /**
     * Seed value used to initialize Objecto-managed random generators.
     *
     * @return deterministic seed value
     */
    long value();

}
