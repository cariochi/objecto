package com.cariochi.objecto.instantiators;

import com.cariochi.objecto.generators.Context;
import com.cariochi.objecto.generators.ObjectoGenerator;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractInstantiator implements Function<Context, Object> {

    private final ObjectoGenerator generator;

    protected Object generate(Context context) {
        return generator.generate(context);
    }

}
