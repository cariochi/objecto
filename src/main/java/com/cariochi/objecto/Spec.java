package com.cariochi.objecto;

import com.cariochi.objecto.repeatable.Configs;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The Spec annotation is used to define various constraints and configurations for fields and methods in a class. It contains several nested annotations to specify
 * ranges, sizes, and other properties for different data types.
 */
public @interface Spec {

    /**
     * The MaxDepth annotation is used to specify the maximum depth for nested objects.
     */
    @Target({TYPE, METHOD})
    @Retention(RUNTIME)
    @Repeatable(Configs.MaxDepth.class)
    @Inherited
    @interface MaxDepth {
        String field() default "";
        int value();
    }

    /**
     * The MaxRecursionDepth annotation is used to specify the maximum recursion depth.
     */
    @Target({TYPE, METHOD})
    @Retention(RUNTIME)
    @Repeatable(Configs.MaxRecursionDepth.class)
    @Inherited
    @interface MaxRecursionDepth {
        String field() default "";
        int value();
    }

    /**
     * The Nullable annotation is used to specify whether a field ortype can be null.
     */
    @Target({TYPE, METHOD})
    @Retention(RUNTIME)
    @Repeatable(Configs.Nullable.class)
    @Inherited
    @interface Nullable {
        String field() default "";
        boolean value();
    }

    /**
     * The Longs annotation contains nested annotations for configuring long values.
     */
    @interface Longs {

        /**
         * The Range annotation is used to specify the minimum (inclusive) and maximum (exclusive) values for long fields.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Repeatable(Configs.Longs.Range.class)
        @Inherited
        @interface Range {
            String field() default "";
            long from();
            long to();
        }
    }

    /**
     * The Integers annotation contains nested annotations for configuring integer values.
     */
    @interface Integers {

        /**
         * The Range annotation is used to specify minimum (inclusive) and maximum (exclusive) values for integer fields.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Repeatable(Configs.Integers.Range.class)
        @Inherited
        @interface Range {
            String field() default "";
            int from();
            int to();
        }
    }

    /**
     * The Short annotation contains nested annotations for configuring short values.
     */
    @interface Shorts {

        /**
         * The Range annotation is used to specify minimum (inclusive) and maximum (exclusive) values for short fields.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Repeatable(Configs.Shorts.Range.class)
        @Inherited
        @interface Range {
            String field() default "";
            short from();
            short to();
        }
    }

    /**
     * The Bytes annotation contains nested annotations for configuring byte values.
     */
    @interface Bytes {

        /**
         * The Range annotation is used to specify minimum (inclusive) and maximum (exclusive) values for byte fields.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Repeatable(Configs.Bytes.Range.class)
        @Inherited
        @interface Range {
            String field() default "";
            byte from();
            byte to();
        }
    }

    /**
     * The Chars annotation contains nested annotations for configuring char values.
     */
    @interface Chars {

        /**
         * The Range annotation is used to specify minimum (inclusive) and maximum (exclusive) values for char fields.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Repeatable(Configs.Chars.Range.class)
        @Inherited
        @interface Range {
            String field() default "";
            char from();
            char to();
        }
    }

    /**
     * The BigDecimals annotation contains nested annotations for configuring BigDecimal values.
     */
    @interface BigDecimals {

        /**
         * The Range annotation is used to specify minimum (inclusive) and maximum (exclusive) values for BigDecimal fields.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Repeatable(Configs.BigDecimals.Range.class)
        @Inherited
        @interface Range {
            String field() default "";
            double from();
            double to();
        }

        /**
         * The Scale annotation is used to specify the scale (number of decimal places) for BigDecimal fields.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Repeatable(Configs.BigDecimals.Scale.class)
        @Inherited
        @interface Scale {
            String field() default "";
            int value();
        }
    }

    /**
     * The Doubles annotation contains nested annotations for configuring double values.
     */
    @interface Doubles {

        /**
         * The Range annotation is used to specify minimum (inclusive) and maximum (exclusive) values for double fields.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Repeatable(Configs.Doubles.Range.class)
        @Inherited
        @interface Range {
            String field() default "";
            double from();
            double to();
        }
    }

    /**
     * The Floats annotation contains nested annotations for configuring float values.
     */
    @interface Floats {

        /**
         * The Range annotation is used to specify minimum (inclusive) and maximum (exclusive) values for float fields.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Repeatable(Configs.Floats.Range.class)
        @Inherited
        @interface Range {
            String field() default "";
            float from();
            float to();
        }
    }

    /**
     * The Dates annotation contains nested annotations for configuring date values.
     */
    @interface Dates {

        /**
         * The Range annotation is used to specify minimum (inclusive) and maximum (exclusive) values for date fields. The values should be in ISO-8601 format.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Repeatable(Configs.Dates.Range.class)
        @Inherited
        @interface Range {
            String field() default "";
            String from();
            String to();
            String timezone() default "UTC";
        }
    }

    /**
     * The Collections annotation contains nested annotations for configuring collection sizes.
     */
    @interface Collections {

        /**
         * The Size annotation is used to specify the size of collections.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Repeatable(Configs.Collections.Size.class)
        @Inherited
        @interface Size {

            String field() default "";
            int value();

            /**
             * The Range annotation is used to specify the minimum (inclusive) and maximum (exclusive) sizes for collections.
             */
            @Target({TYPE, METHOD})
            @Retention(RUNTIME)
            @Repeatable(Configs.Collections.Size.Range.class)
            @Inherited
            @interface Range {
                String field() default "";
                int from();
                int to();
            }
        }
    }

    /**
     * The Arrays annotation contains nested annotations for configuring array sizes.
     */
    @interface Arrays {

        /**
         * The Size annotation is used to specify the size of arrays.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Repeatable(Configs.Arrays.Size.class)
        @Inherited
        @interface Size {

            String field() default "";
            int value();

            /**
             * The Range annotation is used to specify the minimum (inclusive) and maximum (exclusive) sizes for arrays.
             */
            @Target({TYPE, METHOD})
            @Retention(RUNTIME)
            @Repeatable(Configs.Arrays.Size.Range.class)
            @Inherited
            @interface Range {
                String field() default "";
                int from();
                int to();
            }
        }
    }

    /**
     * The Maps annotation contains nested annotations for configuring map sizes.
     */
    @interface Maps {

        /**
         * The Size annotation is used to specify the size of maps.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Repeatable(Configs.Maps.Size.class)
        @Inherited
        @interface Size {

            String field() default "";
            int value();

            /**
             * The Range annotation is used to specify the minimum (inclusive) and maximum (exclusive) sizes for maps.
             */
            @Target({TYPE, METHOD})
            @Retention(RUNTIME)
            @Repeatable(Configs.Maps.Size.Range.class)
            @Inherited
            @interface Range {
                String field() default "";
                int from();
                int to();
            }
        }
    }

    /**
     * The Strings annotation contains nested annotations for configuring string properties.
     */
    @interface Strings {

        /**
         * The Length annotation is used to specify the length of strings.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Repeatable(Configs.Strings.Length.class)
        @Inherited
        @interface Length {

            String field() default "";
            int value();

            /**
             * The Range annotation is used to specify the minimum (inclusive) and maximum (exclusive) lengths for strings.
             */
            @Target({TYPE, METHOD})
            @Retention(RUNTIME)
            @Repeatable(Configs.Strings.Length.Range.class)
            @Inherited
            @interface Range {
                String field() default "";
                int from();
                int to();
            }
        }

        /**
         * The Parameters annotation is used to specify various properties for string generation.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Repeatable(Configs.Strings.Parameters.class)
        @Inherited
        @interface Parameters {
            String field() default "";
            String chars() default "";
            char from() default 0;
            char to() default 0;
            boolean useFieldNamePrefix();
        }

    }

    /**
     * The SetNull annotation is used to specify that a field should be set to null.
     */
    @Target({TYPE, METHOD})
    @Retention(RUNTIME)
    @Repeatable(Configs.SetNull.class)
    @Inherited
    @interface SetNull {
        String field();
    }

    /**
     * The SetValue annotation is used to specify a value for a field.
     */
    @Target({TYPE, METHOD})
    @Retention(RUNTIME)
    @Repeatable(Configs.SetValue.class)
    @Inherited
    @interface SetValue {
        String field();
        String value();
    }
}
