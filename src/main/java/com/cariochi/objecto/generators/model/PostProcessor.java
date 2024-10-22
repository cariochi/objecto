package com.cariochi.objecto.generators.model;

import com.cariochi.objecto.ObjectoRandom;
import com.cariochi.objecto.generators.Context;
import com.cariochi.reflecto.methods.TargetMethod;
import com.cariochi.reflecto.parameters.ReflectoParameter;
import com.cariochi.reflecto.types.ReflectoType;
import java.util.List;
import java.util.Random;
import lombok.Value;

@Value
public class PostProcessor {

    ReflectoType type;
    TargetMethod method;

    public void invoke(Context context, Object object) {
        final List<ReflectoParameter> parameters = method.parameters().list();
        if (parameters.size() == 1) {
            method.invoke(object);
        } else {
            final ReflectoType parameterType = parameters.get(1).type();
            if (parameterType.is(ObjectoRandom.class)) {
                method.invoke(object, context.getRandom());
            }
            if (parameterType.is(Random.class)) {
                method.invoke(object, context.getRandom().getRandom());
            }
        }
    }
}
