package com.cariochi.objecto;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Reuses generators, providers, references, field generators, and post-processors from other factories.
 *
 * <pre>{@code
 * @ImportFactory({UserFactory.class, DatesFactory.class})
 * interface IssueFactory {
 *     Issue createIssue();
 * }
 * }</pre>
 *
 * <p>
 * This annotation is applied to factory interfaces or abstract classes.
 */
@Target({TYPE})
@Retention(RUNTIME)
@Inherited
public @interface ImportFactory {

    /**
     * Factory classes whose Objecto configuration should be imported.
     *
     * @return factory classes to reuse
     */
    Class<?>[] value();

}
