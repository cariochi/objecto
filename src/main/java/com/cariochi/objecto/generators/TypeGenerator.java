package com.cariochi.objecto.generators;


import com.cariochi.reflecto.methods.TargetMethod;
import lombok.Getter;

@Getter
class TypeGenerator extends AbstractCustomGenerator {


    public TypeGenerator(TargetMethod method) {
        super(method);
    }

    @Override
    public boolean isSupported(Context context) {
        return method.returnType().equals(context.getType());
    }

    @Override
    public Object generate(Context context) {
        return invokeGenerationMethod(context);
    }

}
