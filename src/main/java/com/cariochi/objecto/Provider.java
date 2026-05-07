package com.cariochi.objecto;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Provides custom construction logic for a generated type.
 * <p>
 * Use this annotation when Objecto cannot instantiate a type through its constructors or static factory
 * methods, or when tests need a specific baseline instance before fields are generated.
 *
 * <pre>{@code
 * interface AttachmentFactory {
 *
 *     @Provider
 *     private Attachment<?> newAttachment() {
 *         return Attachment.builder()
 *                 .fileContent(new byte[0])
 *                 .build();
 *     }
 * }
 * }</pre>
 *
 * <p>
 * The annotated method must return the type it constructs. It may be a private interface method.
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface Provider {

}
