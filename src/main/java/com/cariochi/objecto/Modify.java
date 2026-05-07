package com.cariochi.objecto;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Applies values to generated objects after generation.
 * <p>
 * {@code @Modify} can be placed on a factory method or on individual method parameters. Expressions may
 * be simple field paths, nested paths, collection paths, or method-call expressions.
 *
 * <pre>{@code
 * interface IssueFactory {
 *
 *     Issue createIssue(@Modify("type") Issue.Type type);
 *
 *     @Modify("setStatus(?)")
 *     IssueFactory withStatus(Issue.Status status);
 * }
 * }</pre>
 *
 * <p>
 * A plain path such as {@code "type"} is treated as field assignment. Use {@code "setType(?)"} or
 * {@code "dependencies.put(?, ?)"} when you want to call a method explicitly.
 */
@Target({METHOD, PARAMETER})
@Retention(RUNTIME)
public @interface Modify {

    /**
     * Modification expressions to apply.
     *
     * @return field paths or method-call expressions
     */
    String[] value();

}
