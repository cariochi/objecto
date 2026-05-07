package com.cariochi.objecto.repeatable;

import com.cariochi.objecto.Datafaker;
import com.cariochi.objecto.Generate;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface Repeatable {

    @Target({TYPE, METHOD})
    @Retention(RUNTIME)
    @Inherited
    @interface MaxDepth {

        Generate.MaxDepth[] value();

    }

    @Target({TYPE, METHOD})
    @Retention(RUNTIME)
    @Inherited
    @interface MaxRecursionDepth {
        Generate.MaxRecursionDepth[] value();
    }

    @Target({TYPE, METHOD})
    @Retention(RUNTIME)
    @Inherited
    @interface Nullable {
        Generate.Nullable[] value();
    }

    @interface Longs {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            Generate.Longs.Range[] value();
        }
    }

    @interface Integers {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            Generate.Integers.Range[] value();
        }
    }

    @interface Shorts {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            Generate.Shorts.Range[] value();
        }
    }

    @interface Bytes {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            Generate.Bytes.Range[] value();
        }
    }

    @interface Chars {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            Generate.Chars.Range[] value();
        }
    }

    @interface BigDecimals {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            Generate.BigDecimals.Range[] value();
        }

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Scale {
            Generate.BigDecimals.Scale[] value();
        }
    }

    @interface Doubles {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            Generate.Doubles.Range[] value();
        }
    }

    @interface Floats {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            Generate.Floats.Range[] value();
        }
    }

    @interface Dates {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Range {
            Generate.Dates.Range[] value();
        }
    }

    @interface Collections {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Size {

            Generate.Collections.Size[] value();

            @Target({TYPE, METHOD})
            @Retention(RUNTIME)
            @Inherited
            @interface Range {
                Generate.Collections.Size.Range[] value();
            }
        }
    }

    @interface Arrays {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Size {

            Generate.Arrays.Size[] value();

            @Target({TYPE, METHOD})
            @Retention(RUNTIME)
            @Inherited
            @interface Range {
                Generate.Arrays.Size.Range[] value();
            }
        }
    }

    @interface Maps {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Size {

            Generate.Maps.Size[] value();

            @Target({TYPE, METHOD})
            @Retention(RUNTIME)
            @Inherited
            @interface Range {
                Generate.Maps.Size.Range[] value();
            }
        }
    }

    @interface Strings {

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Length {

            Generate.Strings.Length[] value();

            @Target({TYPE, METHOD})
            @Retention(RUNTIME)
            @Inherited
            @interface Range {
                Generate.Strings.Length.Range[] value();
            }
        }

        @Target({TYPE, METHOD})
        @Retention(RUNTIME)
        @Inherited
        @interface Characters {
            Generate.Strings.Characters[] value();
        }

    }

    @Target({TYPE, METHOD})
    @Retention(RUNTIME)
    @Inherited
    @interface SetNull {
        Generate.SetNull[] value();
    }

    @Target({TYPE, METHOD})
    @Retention(RUNTIME)
    @Inherited
    @interface SetValue {
        Generate.SetValue[] value();
    }

    @Target({TYPE, METHOD})
    @Retention(RUNTIME)
    @Inherited
    @interface Datafakers {
        Datafaker[] value();
    }
}
