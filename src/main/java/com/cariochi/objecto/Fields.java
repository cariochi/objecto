package com.cariochi.objecto;

import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface Fields {

    @Target(METHOD)
    @Retention(RUNTIME)
    @Repeatable(Datafaker.Repeatable.class)
    @Inherited
    @interface Datafaker {

        String field();

        String method() default "";

        String locale() default "";

        @Target(METHOD)
        @Retention(RUNTIME)
        @Inherited
        @interface Repeatable {

            Datafaker[] value();

        }
    }

    @Target(METHOD)
    @Retention(RUNTIME)
    @Repeatable(Nullable.Repeatable.class)
    @Inherited
    @interface Nullable {

        String field();

        boolean value();

        @Target(METHOD)
        @Retention(RUNTIME)
        @Inherited
        @interface Repeatable {

            Nullable[] value();

        }
    }

    @Target(METHOD)
    @Retention(RUNTIME)
    @Repeatable(SetNull.Repeatable.class)
    @Inherited
    @interface SetNull {

        String value();

        @Target(METHOD)
        @Retention(RUNTIME)
        @Inherited
        @interface Repeatable {

            SetNull[] value();

        }
    }

    @Target(METHOD)
    @Retention(RUNTIME)
    @Repeatable(SetValue.Repeatable.class)
    @Inherited
    @interface SetValue {

        String field();

        String value();

        @Target(METHOD)
        @Retention(RUNTIME)
        @Inherited
        @interface Repeatable {

            SetValue[] value();

        }
    }
}
