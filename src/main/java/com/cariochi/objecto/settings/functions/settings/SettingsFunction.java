package com.cariochi.objecto.settings.functions.settings;

import com.cariochi.objecto.settings.ObjectoSettings;
import java.lang.annotation.Annotation;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public interface SettingsFunction<A extends Annotation> extends Function<A, UnaryOperator<ObjectoSettings>> {

    UnaryOperator<ObjectoSettings> apply(A annotation);

}
