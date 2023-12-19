package com.cariochi.objecto.generator;

import java.lang.reflect.Type;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExternalTypeGenerator {

    private final Type type;
    private final Supplier<Object> generator;

    public boolean isSupported(Type type) {
        return this.type.equals(type);
    }

    public Object create() {
        return generator.get();
    }

}
