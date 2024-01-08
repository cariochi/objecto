package com.cariochi.objecto;

import com.cariochi.objecto.settings.StringGenerationType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.cariochi.objecto.settings.StringGenerationType.ALPHABETIC;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Inherited
public @interface WithSettings {

    int depth() default 7;

    int typeDepth() default 3;

    LongsRange longsRange() default @LongsRange(min = 1L, max = 100_000L);

    IntegersRange integersRange() default @IntegersRange(min = 1, max = 100_000);

    IntegersRange bytesRange() default @IntegersRange(min = 65, max = 91);

    DoublesRange doublesRange() default @DoublesRange(min = 1.0, max = 100_000.0);

    FloatsRange floatsRange() default @FloatsRange(min = 1F, max = 100_000F);

    DoublesRange yearsRange() default @DoublesRange(min = -5.0, max = 1.0);

    IntegersRange collectionsSize() default @IntegersRange(min = 2, max = 5);

    IntegersRange arraysSize() default @IntegersRange(min = 2, max = 5);

    IntegersRange mapsSize() default @IntegersRange(min = 2, max = 5);

    StringSettings stringSettings() default @StringSettings;

    @interface StringSettings {

        IntegersRange size() default @IntegersRange(min = 8, max = 16);

        boolean uppercase() default true;

        StringGenerationType type() default ALPHABETIC;

    }

    @interface LongsRange {

        long min();

        long max();

    }

    @interface IntegersRange {

        int min();

        int max();

    }

    @interface DoublesRange {

        double min();

        double max();

    }

    @interface FloatsRange {

        float min();

        float max();

    }

}
