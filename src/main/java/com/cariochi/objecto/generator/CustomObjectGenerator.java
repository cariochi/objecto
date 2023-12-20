package com.cariochi.objecto.generator;

import com.cariochi.objecto.ObjectoSettings;
import com.cariochi.objecto.RandomObjectGenerator;
import com.cariochi.reflecto.Reflecto;
import com.cariochi.reflecto.fields.JavaField;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;

public class CustomObjectGenerator extends Generator {

    public CustomObjectGenerator(RandomObjectGenerator randomObjectGenerator) {
        super(randomObjectGenerator);
    }

    @Override
    public boolean isSupported(Type type) {
        return true;
    }

    @Override
    public Object create(Type type, ObjectoSettings settings) {
        if (settings.depth() == 1) {
            return null;
        }
        final Object instance = createInstance(type);
        if (instance != null) {
            final List<JavaField> fields = Reflecto.reflect(instance).fields().asList();
            for (JavaField field : fields) {
                Type genericType = field.getGenericType();
                if (genericType instanceof TypeVariable) {
                    final Object fieldValue = field.getValue();
                    if (fieldValue != null) {
                        genericType = fieldValue.getClass();
                    } else {
                        continue;
                    }
                }
                Object fieldValue = generateFiledValue(type, genericType, field.getName(), settings.withDepth(settings.depth() - 1));
                field.setValue(fieldValue);
            }
        }
        return instance;
    }

}
