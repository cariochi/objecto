package com.cariochi.objecto.generators;

import com.cariochi.reflecto.objects.fields.ObjectField;
import com.cariochi.reflecto.types.TypeReflection;
import com.cariochi.reflecto.types.fields.TypeField;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

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

            final List<TypeField> typeFields = context.getType().fields().all().stream()
                    .filter(field -> !field.isStatic())
                    .collect(toList());

            for (TypeField typeField : typeFields) {
                final TypeReflection fieldType = typeField.getType();
                if (fieldType != null) {
                    final ObjectField javaField = typeField.toObjectField(instance);
                    final Context fieldContext = context.nextContext(typeField.getName(), fieldType, javaField.getValue());
                    final Object fieldValue = fieldContext.generate();
                    try {
                        javaField.setValue(fieldValue);
                    } catch (Exception e) {
                        log.error("Cannot set field value: {}", fieldContext.getPath());
                    }
                }
            }
        }
        return instance;
    }


}
