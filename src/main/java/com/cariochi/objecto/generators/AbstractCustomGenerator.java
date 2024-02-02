package com.cariochi.objecto.generators;

import com.cariochi.objecto.utils.ObjectoRandom;
import com.cariochi.reflecto.objects.methods.ObjectMethod;
import com.cariochi.reflecto.types.methods.MethodParameter;
import java.util.List;

public abstract class AbstractCustomGenerator extends AbstractGenerator {

    protected final ObjectMethod method;

    protected AbstractCustomGenerator(ObjectMethod method) {
        this.method = method;
    }

    protected Object invokeGenerationMethod(Context context) {
        final List<MethodParameter> parameters = method.getParameters();
        if (parameters.size() == 1) {
            if (parameters.get(0).getType().is(ObjectoRandom.class)) {
                return method.invoke(context.getRandom());
            }
            if (parameters.get(0).getType().is(java.util.Random.class)) {
                return method.invoke(context.getRandom().getRandom());
            }
        }
        return method.invoke();
    }

}
