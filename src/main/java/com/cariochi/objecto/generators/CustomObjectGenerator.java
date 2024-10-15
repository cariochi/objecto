package com.cariochi.objecto.generators;

import com.cariochi.reflecto.fields.ReflectoField;
import com.cariochi.reflecto.fields.TargetField;
import com.cariochi.reflecto.types.ReflectoType;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;

@Slf4j
class CustomObjectGenerator extends AbstractObjectsGenerator {

    public CustomObjectGenerator(ObjectoGenerator generator) {
        super(generator);
    }

    @Override
    public boolean isSupported(Context context) {
        return true;
    }

    @Override
    public Object generate(Context context) {

        if (context.getDepth() > context.getSettings().maxDepth()) {
            return null;
        }

        final Object instance = generator.newInstance(context);
        context.setInstance(instance);

        if (instance != null) {

            final List<ReflectoField> fields = context.getType().fields().stream()
                    .filter(field -> !field.modifiers().isStatic())
                    .collect(toList());

            for (ReflectoField field : fields) {
                final ReflectoType fieldType = field.type();
                if (fieldType != null) {
                    final TargetField targetField = field.withTarget(instance);
                    final Context fieldContext = context.nextContext(field.name(), fieldType);
                    fieldContext.setInstance(targetField.getValue());
                    final Object fieldValue = generator.generate(fieldContext);
                    try {
                        targetField.setValue(fieldValue);
                    } catch (Exception e) {
                        log.error("Cannot set field value: {}", fieldContext.getPath());
                    }
                }
            }

        }
        return instance;
    }


}
