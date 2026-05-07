package com.cariochi.objecto;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Connects generated objects back to the object currently being generated.
 * <p>
 * Use references for bidirectional relationships and nested object graphs.
 *
 * <pre>{@code
 * interface IssueFactory {
 *
 *     @Reference("subtasks[*].parent")
 *     Issue createIssue();
 * }
 * }</pre>
 *
 * <p>
 * In this example every generated subtask receives the generated issue as its {@code parent}.
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface Reference {

    /**
     * Reference paths to populate.
     *
     * @return field paths that should point to the current generated object
     */
    String[] value();

}
