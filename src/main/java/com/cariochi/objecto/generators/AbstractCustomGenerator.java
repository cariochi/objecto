package com.cariochi.objecto.generators;

import com.cariochi.objecto.ObjectoRandom;
import com.cariochi.reflecto.methods.TargetMethod;
import com.cariochi.reflecto.parameters.ReflectoParameter;
import com.cariochi.reflecto.types.ReflectoType;
import java.util.List;

public abstract class AbstractCustomGenerator implements Generator {

    protected final TargetMethod method;

    protected AbstractCustomGenerator(TargetMethod method) {
        this.method = method;
    }

    protected Object invokeGenerationMethod(Context context) {
        final List<ReflectoParameter> parameters = method.parameters().list();
        if (parameters.size() == 1) {
            final ReflectoType parameterType = parameters.get(0).type();
            if (parameterType.is(ObjectoRandom.class)) {
                return method.invoke(context.getRandom());
            }
            if (parameterType.is(java.util.Random.class)) {
                return method.invoke(context.getRandom().getRandom());
            }
        }
        return method.invoke();
    }

}
