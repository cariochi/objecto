package com.cariochi.objecto.generators;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
abstract class AbstractGenerator {

    private final ObjectoGenerator generator;

    abstract boolean isSupported(Context context);

    abstract Object generate(Context context);

    public Object generateObject(Context context) {
        return generator.generate(context);
    }

    public Object newInstance(Context context) {
        final Object o = generator.getInstantiator().newInstance(context);
        context.setInstance(o);
        return o;
    }

}
