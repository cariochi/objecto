package com.cariochi.objecto.generator;

import com.cariochi.objecto.ObjectoSettings;
import com.cariochi.reflecto.Reflecto;
import com.cariochi.reflecto.fields.JavaField;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public class CustomObjectGenerator extends Generator {

    public CustomObjectGenerator(RandomObjectGenerator randomObjectGenerator) {
        super(randomObjectGenerator);
    }

    @Override
    public boolean isSupported(Type type) {
        return true;
    }

    @Override
    public Object create(Type type, Type ownerType, ObjectoSettings settings) {
        if (settings.depth() == 1) {
            return null;
        }
        final Object instance = createInstance(type);
        if (instance != null) {

            final List<JavaField> fields = Reflecto.reflect(instance).fields().asList();
            for (JavaField field : fields) {
                final Type fieldType = Optional.ofNullable(field.getValue())
                        .map(Object::getClass)
                        .map(Type.class::cast)
                        .orElseGet(field::getGenericType);
                if (fieldType != null) {
                    Object fieldValue = generateRandomObject(fieldType, type, field.getName(), settings.withDepth(settings.depth() - 1));
                    field.setValue(fieldValue);
                }
            }
        }
        return instance;
    }


}
