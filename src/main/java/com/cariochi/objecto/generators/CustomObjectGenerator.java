package com.cariochi.objecto.generators;

import com.cariochi.reflecto.fields.JavaField;
import java.lang.reflect.Type;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

import static com.cariochi.reflecto.Reflecto.reflect;
import static java.util.stream.Collectors.toList;

@Slf4j
class CustomObjectGenerator extends Generator {

    public CustomObjectGenerator(ObjectoGenerator objectoGenerator) {
        super(objectoGenerator);
    }

    @Override
    public boolean isSupported(Type type, GenerationContext context) {
        return true;
    }

    @Override
    public Object generate(Type type, GenerationContext context) {

        final Object instance = createInstance(type, context);

        if (instance != null) {

            final List<JavaField> fields = reflect(instance).fields().asList().stream().filter(field -> !field.isStatic()).collect(toList());
            for (JavaField field : fields) {
                final Type fieldType = field.getGenericType();
                if (fieldType != null) {
                    final GenerationContext fieldContext = context
                            .withOwnerType(type)
                            .withField(field.getName())
                            .withInstance(field.getValue());
                    final Object fieldValue = generateRandomObject(fieldType, fieldContext);
                    try {
                        field.setValue(fieldValue);
                    } catch (Exception e) {
                        log.error("Cannot set field value: {}", fieldContext.path());
                    }
                }
            }
        }
        return instance;
    }


}
