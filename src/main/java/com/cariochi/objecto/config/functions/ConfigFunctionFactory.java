package com.cariochi.objecto.config.functions;

import com.cariochi.objecto.generators.model.ConfigFunction;
import com.cariochi.reflecto.methods.ReflectoMethod;
import java.lang.annotation.Annotation;

public interface ConfigFunctionFactory<A extends Annotation> {

    ConfigFunction createSettings(A annotation, ReflectoMethod method);

}
