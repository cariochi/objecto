package com.cariochi.objecto.generators;

import com.cariochi.reflecto.fields.JavaField;
import java.lang.reflect.Type;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

import static com.cariochi.reflecto.Reflecto.reflect;
import static java.util.stream.Collectors.toList;

@Slf4j
class CustomObjectGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return true;
    }

    @Override
    public Object generate(Context context) {

        if (context.getDepth() > context.getSettings().maxDepth()) {
            return null;
        }

        final Object instance = context.newInstance();

        if (instance != null) {

            final List<JavaField> fields = reflect(instance).fields().asList().stream()
                    .filter(field -> !field.isStatic())
                    .collect(toList());

            for (JavaField field : fields) {
                final Type fieldType = field.getGenericType();
                if (fieldType != null) {
                    final Context fieldContext = context.nextContext(field.getName(), fieldType, context.getType(), field.getValue());
                    final Object fieldValue = fieldContext.generate();
                    try {
                        field.setValue(fieldValue);
                    } catch (Exception e) {
                        log.error("Cannot set field value: {}", fieldContext.getPath());
                    }
                }
            }
        }
        return instance;
    }


}
