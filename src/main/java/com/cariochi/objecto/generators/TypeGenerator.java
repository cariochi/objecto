package com.cariochi.objecto.generators;

import com.cariochi.reflecto.objects.methods.ObjectMethod;
import lombok.Getter;

@Getter
class TypeGenerator extends AbstractCustomGenerator {


    public TypeGenerator(ObjectoGenerator generator, ObjectMethod method) {
        super(method);
    }

    @Override
    public boolean isSupported(Context context) {
        return method.getReturnType().actualType().equals(context.getType().actualType());
    }

    @Override
    public Object generate(Context context) {
        return invokeGenerationMethod(context);
    }

}
