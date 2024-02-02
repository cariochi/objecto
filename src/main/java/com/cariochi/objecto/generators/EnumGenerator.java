package com.cariochi.objecto.generators;

import com.cariochi.objecto.settings.Range;
import com.cariochi.reflecto.types.TypeReflection;

class EnumGenerator extends AbstractGenerator {

    @Override
    public boolean isSupported(Context context) {
        return context.getType().isEnum();
    }

    @Override
    public Object generate(Context context) {
        final TypeReflection typeReflection = context.getType();
        final int randomIndex = context.getRandom().nextInt(Range.of(0, typeReflection.getEnumConstants().size()));
        return typeReflection.getEnumConstants().get(randomIndex);
    }

}
