package com.cariochi.objecto.generators.model;

import com.cariochi.reflecto.types.ReflectoType;
import java.util.function.Consumer;
import lombok.Value;

@Value
public class PostProcessor {

    ReflectoType type;
    Consumer<Object> consumer;
}
