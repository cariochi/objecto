package com.cariochi.objecto;

import com.cariochi.objecto.settings.Settings;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.cariochi.objecto.settings.Settings.Strings.Type.ALPHABETIC;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, METHOD})
@Retention(RUNTIME)
@Repeatable(WithSettingsList.class)
@Inherited
public @interface WithSettings {

    String path() default "";

    int maxDepth() default 4;

    int maxRecursionDepth() default 2;

    LongRange longs() default @LongRange(min = 1L, max = 100_000L);

    IntRange integers() default @IntRange(min = 1, max = 100_000);

    IntRange bytes() default @IntRange(min = 65, max = 91);

    BigDecimals bigDecimals() default @BigDecimals;

    DoubleRange doubles() default @DoubleRange(min = 1.0, max = 100_000.0);

    FloatRange floats() default @FloatRange(min = 1F, max = 100_000F);

    DoubleRange years() default @DoubleRange(min = -5.0, max = 1.0);

    Collections collections() default @Collections;

    Collections arrays() default @Collections;

    Collections maps() default @Collections;

    Strings strings() default @Strings;

    @interface Strings {

        IntRange size() default @IntRange(min = 8, max = 16);

        boolean uppercase() default true;

        Settings.Strings.Type type() default ALPHABETIC;

        boolean fieldNamePrefix() default false;

    }

    @interface Collections {

        IntRange size() default @IntRange(min = 2, max = 5);

    }

    @interface BigDecimals {

        double min() default 0;

        double max() default 100_000.0;

        int scale() default 4;

    }

    @interface LongRange {

        long min();

        long max();

    }

    @interface IntRange {

        int min();

        int max();

    }

    @interface DoubleRange {

        double min();

        double max();

    }

    @interface FloatRange {

        float min();

        float max();

    }

}
