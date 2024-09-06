package com.cariochi.objecto.generators;

import com.cariochi.reflecto.types.ReflectoType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class StreamGenerator extends AbstractObjectsGenerator {

    public StreamGenerator(ObjectoGenerator generator) {
        super(generator);
    }

    @Override
    public boolean isSupported(Context context) {
        return context.getType().is(Stream.class);
    }

    @Override
    public Object generate(Context context) {
        final ReflectoType elementType = context.getType().arguments().get(0);
        final List<Object> collection = new ArrayList<>();
        for (int i = 0; i < context.getRandom().nextInt(context.getSettings().collections().size()); i++) {
            Context nextContext = context.nextContext("[" + i + "]", elementType);
            final Object item = generator.generate(nextContext);
            if (item == null) {
                return collection.stream();
            }
            collection.add(item);
        }
        return collection.stream();
    }

}
