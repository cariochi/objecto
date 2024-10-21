package com.cariochi.objecto.settings.functions.fields;

import com.cariochi.objecto.generators.model.FieldSettings;
import com.cariochi.reflecto.methods.ReflectoMethod;
import java.lang.annotation.Annotation;
import java.util.List;

public interface FieldFunction {

    boolean isApplicable(Annotation annotation);

    List<FieldSettings> getFunctions(ReflectoMethod method, Annotation annotation);

}
