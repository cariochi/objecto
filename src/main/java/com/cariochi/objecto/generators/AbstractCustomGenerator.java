package com.cariochi.objecto.generators;

import com.cariochi.objecto.proxy.ObjectoProxyHandler;
import com.cariochi.objecto.random.ObjectoRandom;
import com.cariochi.reflecto.methods.TargetMethod;
import com.cariochi.reflecto.parameters.ReflectoParameter;
import com.cariochi.reflecto.types.ReflectoType;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.Getter;

@Getter
public abstract class AbstractCustomGenerator implements Generator {

    protected final TargetMethod method;

    protected AbstractCustomGenerator(TargetMethod method) {
        this.method = method;
    }

    protected Object invokeGenerationMethod(Context context) {
        if (!context.isDirty() && method.modifiers().isAbstract()) {
            final ObjectoProxyHandler objectoProxyHandler = com.cariochi.reflecto.proxy.ProxyFactory.getHandler(method.target())
                    .filter(ObjectoProxyHandler.class::isInstance)
                    .map(ObjectoProxyHandler.class::cast)
                    .orElse(null);
            final Map<String, Object[]> methodParameters = objectoProxyHandler.getMethodParameters(method.method(), new Object[0]);
            return objectoProxyHandler.invoke(method.method(), methodParameters, context.withDirty(true));
        }

        final List<ReflectoParameter> parameters = method.parameters().list();
        if (parameters.size() == 1) {
            final ReflectoType parameterType = parameters.get(0).type();
            if (parameterType.is(ObjectoRandom.class)) {
                return method.invoke(context.getRandom());
            }
            if (parameterType.is(Random.class)) {
                return method.invoke(context.getRandom().getRandom());
            }
        }
        return method.invoke();
    }

}
