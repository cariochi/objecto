package com.cariochi.objecto.creators;

import com.cariochi.objecto.generators.GenerationContext;
import java.lang.reflect.Type;
import java.util.function.BiFunction;

public interface Creator extends BiFunction<Type, GenerationContext, Object> {

}
