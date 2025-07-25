package com.cariochi.objecto.repeatable;

import com.cariochi.objecto.Spec;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface Configs {

    @Target({TYPE, METHOD})
    @Retention(RUNTIME)
    @Inherited
    @interface MaxDepth {

        Spec.MaxDepth[] value();

    }

    @Target({TYPE, METHOD})
    @Retention(RUNTIME)
    @Inherited
    @interface MaxRecursionDepth {
        Spec.MaxRecursionDepth[] value();
    }

    @Target({TYPE, METHOD})
    @Retention(RUNTIME)
    @Inherited
    @interface Nullable {
        Spec.Nullable[] value();
    }

    @interface Longs {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            Spec.Longs.Range[] value();
        }
    }

    @interface Integers {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            Spec.Integers.Range[] value();
        }
    }

    @interface Shorts {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            Spec.Shorts.Range[] value();
        }
    }

    @interface Bytes {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            Spec.Bytes.Range[] value();
        }
    }

    @interface Chars {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            Spec.Chars.Range[] value();
        }
    }

    @interface BigDecimals {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            Spec.BigDecimals.Range[] value();
        }

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Scale {
            Spec.BigDecimals.Scale[] value();
        }
    }

    @interface Doubles {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            Spec.Doubles.Range[] value();
        }
    }

    @interface Floats {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            Spec.Floats.Range[] value();
        }
    }

    @interface Dates {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            Spec.Dates.Range[] value();
        }
    }

    @interface Collections {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Size {

            Spec.Collections.Size[] value();

            @Target({TYPE, METHOD})
            @Retention(RUNTIME)
            @Inherited
            @interface Range {
                Spec.Collections.Size.Range[] value();
            }
        }
    }

    @interface Arrays {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Size {

            Spec.Arrays.Size[] value();

            @Target({TYPE, METHOD})
            @Retention(RUNTIME)
            @Inherited
            @interface Range {
                Spec.Arrays.Size.Range[] value();
            }
        }
    }

    @interface Maps {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Size {

            Spec.Maps.Size[] value();

            @Target({TYPE, METHOD})
            @Retention(RUNTIME)
            @Inherited
            @interface Range {
                Spec.Maps.Size.Range[] value();
            }
        }
    }

    @interface Strings {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Length {

            Spec.Strings.Length[] value();

            @Target({TYPE, METHOD})
            @Retention(RUNTIME)
            @Inherited
            @interface Range {
                Spec.Strings.Length.Range[] value();
            }
        }

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Parameters {
            Spec.Strings.Parameters[] value();
        }

    }

    @Target({TYPE, METHOD})
    @Retention(RUNTIME)
    @Inherited
    @interface SetNull {
        Spec.SetNull[] value();
    }

    @Target({TYPE, METHOD})
    @Retention(RUNTIME)
    @Inherited
    @interface SetValue {
        Spec.SetValue[] value();
    }
}
