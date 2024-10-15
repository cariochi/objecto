package com.cariochi.objecto;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The Settings annotation is used to define various constraints and configurations for fields and methods in a class. It contains several nested annotations to specify
 * ranges, sizes, and other properties for different data types.
 */
public @interface Settings {

    /**
     * The MaxDepth annotation is used to specify the maximum depth for nested objects.
     */
    @Target({TYPE, METHOD})
    @Retention(RUNTIME)
    @Inherited
    @interface MaxDepth {
        int value();
    }

    /**
     * The MaxRecursionDepth annotation is used to specify the maximum recursion depth.
     */
    @Target({TYPE, METHOD})
    @Retention(RUNTIME)
    @Inherited
    @interface MaxRecursionDepth {
        int value();
    }

    /**
     * The Longs annotation contains nested annotations for configuring long values.
     */
    @interface Longs {

        /**
         * The Range annotation is used to specify the minimum and maximum values for long fields.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            long min();

            long max();
        }
    }

    /**
     * The Integers annotation contains nested annotations for configuring integer values.
     */
    @interface Integers {

        /**
         * The Range annotation is used to specify the minimum and maximum values for integer fields.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            int min();

            int max();
        }
    }

    /**
     * The Short annotation contains nested annotations for configuring short values.
     */
    @interface Shorts {

        /**
         * The Range annotation is used to specify the minimum and maximum values for short fields.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            short min();

            short max();
        }
    }

    /**
     * The Bytes annotation contains nested annotations for configuring byte values.
     */
    @interface Bytes {

        /**
         * The Range annotation is used to specify the minimum and maximum values for byte fields.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            byte min();

            byte max();
        }
    }

    /**
     * The Chars annotation contains nested annotations for configuring char values.
     */
    @interface Chars {

        /**
         * The Range annotation is used to specify the minimum and maximum values for char fields.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            char min();

            char max();
        }
    }

    /**
     * The BigDecimals annotation contains nested annotations for configuring BigDecimal values.
     */
    @interface BigDecimals {

        /**
         * The Range annotation is used to specify the minimum and maximum values for BigDecimal fields.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            double min();

            double max();
        }

        /**
         * The Scale annotation is used to specify the scale (number of decimal places) for BigDecimal fields.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Scale {
            int value();
        }
    }

    /**
     * The Doubles annotation contains nested annotations for configuring double values.
     */
    @interface Doubles {

        /**
         * The Range annotation is used to specify the minimum and maximum values for double fields.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            double min();

            double max();
        }
    }

    /**
     * The Floats annotation contains nested annotations for configuring float values.
     */
    @interface Floats {

        /**
         * The Range annotation is used to specify the minimum and maximum values for float fields.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            float min();

            float max();
        }
    }

    /**
     * The Dates annotation contains nested annotations for configuring date values.
     */
    @interface Dates {

        /**
         * The Range annotation is used to specify the minimum and maximum values for date fields. The values should be in ISO-8601 format.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            String min();

            String max();

            String timezone() default "UTC";
        }
    }

    /**
     * The Collections annotation contains nested annotations for configuring collection sizes.
     */
    @interface Collections {

        /**
         * The Size annotation is used to specify the minimum and maximum sizes for collections.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Size {
            int min();

            int max();
        }
    }

    /**
     * The Arrays annotation contains nested annotations for configuring array sizes.
     */
    @interface Arrays {

        /**
         * The Size annotation is used to specify the minimum and maximum sizes for arrays.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Size {
            int min();

            int max();
        }
    }

    /**
     * The Maps annotation contains nested annotations for configuring map sizes.
     */
    @interface Maps {

        /**
         * The Size annotation is used to specify the minimum and maximum sizes for maps.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Size {
            int min();

            int max();
        }
    }

    /**
     * The Strings annotation contains nested annotations for configuring string properties.
     */
    @interface Strings {

        /**
         * The Size annotation is used to specify the minimum and maximum lengths for strings.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Size {
            int min();

            int max();
        }

        /**
         * The Parameters annotation is used to specify various properties for string generation.
         */
        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Parameters {
            boolean letters();

            boolean numbers();

            boolean uppercase();

            boolean useFieldNamePrefix();
        }
    }

    @interface Datafaker {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Locale {
            String value();
        }

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Method {
            String value();
        }
    }

    @Target({TYPE, METHOD})
    @Retention(RUNTIME)
    @Inherited
    @interface Nullable {
        boolean value();
    }

}
