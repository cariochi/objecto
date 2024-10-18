package com.cariochi.objecto;

import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The Fields annotation is used to define various constraints and configurations for fields in a class. It contains several nested annotations to specify data
 * generation, nullability, and other properties for different fields.
 */
public @interface Fields {

    /**
     * The Datafaker annotation is used to specify the field and method for data generation using the Datafaker library.
     */
    @Target(METHOD)
    @Retention(RUNTIME)
    @Repeatable(Datafaker.Repeatable.class)
    @Inherited
    @interface Datafaker {

        /**
         * Specifies the field name for which data is to be generated.
         *
         * @return the field name
         */
        String field();

        /**
         * Specifies the method to be used for data generation. Defaults to an empty string.
         *
         * @return the method name
         */
        String method() default "";

        /**
         * Specifies the locale to be used for data generation. Defaults to an empty string.
         *
         * @return the locale
         */
        String locale() default "";

        /**
         * The Repeatable annotation is used to allow multiple Datafaker annotations on the same method.
         */
        @Target(METHOD)
        @Retention(RUNTIME)
        @Inherited
        @interface Repeatable {

            /**
             * An array of Datafaker annotations.
             *
             * @return an array of Datafaker annotations
             */
            Datafaker[] value();

        }
    }

    /**
     * The Nullable annotation is used to specify whether a field can be null.
     */
    @Target(METHOD)
    @Retention(RUNTIME)
    @Repeatable(Nullable.Repeatable.class)
    @Inherited
    @interface Nullable {

        /**
         * Specifies the field name.
         *
         * @return the field name
         */
        String field();

        /**
         * Specifies whether the field can be null.
         *
         * @return true if the field can be null, false otherwise
         */
        boolean value();

        /**
         * The Repeatable annotation is used to allow multiple Nullable annotations on the same method.
         */
        @Target(METHOD)
        @Retention(RUNTIME)
        @Inherited
        @interface Repeatable {

            /**
             * An array of Nullable annotations.
             *
             * @return an array of Nullable annotations
             */
            Nullable[] value();

        }
    }

    /**
     * The SetNull annotation is used to specify that a field should be set to null.
     */
    @Target(METHOD)
    @Retention(RUNTIME)
    @Repeatable(SetNull.Repeatable.class)
    @Inherited
    @interface SetNull {

        /**
         * Specifies the field name to be set to null.
         *
         * @return the field name
         */
        String value();

        /**
         * The Repeatable annotation is used to allow multiple SetNull annotations on the same method.
         */
        @Target(METHOD)
        @Retention(RUNTIME)
        @Inherited
        @interface Repeatable {

            /**
             * An array of SetNull annotations.
             *
             * @return an array of SetNull annotations
             */
            SetNull[] value();

        }
    }

    /**
     * The SetValue annotation is used to specify a value for a field.
     */
    @Target(METHOD)
    @Retention(RUNTIME)
    @Repeatable(SetValue.Repeatable.class)
    @Inherited
    @interface SetValue {

        /**
         * Specifies the field name.
         *
         * @return the field name
         */
        String field();

        /**
         * Specifies the value to be set for the field.
         *
         * @return the value to be set
         */
        String value();

        /**
         * The Repeatable annotation is used to allow multiple SetValue annotations on the same method.
         */
        @Target(METHOD)
        @Retention(RUNTIME)
        @Inherited
        @interface Repeatable {

            /**
             * An array of SetValue annotations.
             *
             * @return an array of SetValue annotations
             */
            SetValue[] value();

        }
    }

    /**
     * The Range annotation is used to specify the size of a field. It can be applied to arrays, collections, maps, and strings to define their size.
     */
    @Target(METHOD)
    @Retention(RUNTIME)
    @Repeatable(Size.Repeatable.class)
    @Inherited
    @interface Size {

        /**
         * Specifies the field name.
         *
         * @return the field name
         */
        String field();

        /**
         * Specifies the size of the field.
         *
         * @return the size of the field
         */
        int value();

        /**
         * The Repeatable annotation is used to allow multiple Range annotations on the same method.
         */
        @Target(METHOD)
        @Retention(RUNTIME)
        @Inherited
        @interface Repeatable {

            /**
             * An array of Range annotations.
             *
             * @return an array of Range annotations
             */
            Size[] value();

        }
    }

    /**
     * The Range annotation is used to specify the range of values for a field. It can be applied to arrays, collections, maps, strings, and various numeric types. In the
     * case of collections, arrays, maps, and strings, it will set the range for their size. For numeric values, it will set the range for the values themselves.
     * <p>
     * Example usage:
     * <pre>
     * &#64;Fields.Range(field = "array", from = 10, to = 20)
     * &#64;Fields.Range(field = "anInt", from = 1, to = 100)
     * &#64;Fields.Range(field = "aDouble", from = 0.1, to = 9.9)
     * </pre>
     */
    @Target(METHOD)
    @Retention(RUNTIME)
    @Repeatable(Range.Repeatable.class)
    @Inherited
    @interface Range {

        /**
         * Specifies the field name.
         *
         * @return the field name
         */
        String field();

        /**
         * Specifies the minimum value for the field (inclusive).
         *
         * @return the minimum value
         */
        double from();

        /**
         * Specifies the maximum value for the field (exclusive).
         *
         * @return the maximum value
         */
        double to();

        /**
         * The Repeatable annotation is used to allow multiple Range annotations on the same method.
         */
        @Target(METHOD)
        @Retention(RUNTIME)
        @Inherited
        @interface Repeatable {

            /**
             * An array of Range annotations.
             *
             * @return an array of Range annotations
             */
            Range[] value();

        }
    }
}
