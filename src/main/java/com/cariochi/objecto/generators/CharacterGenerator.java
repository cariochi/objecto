package com.cariochi.objecto.generators;

import com.cariochi.objecto.settings.ObjectoSettings;
import com.cariochi.objecto.settings.Range;

class CharacterGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return context.getType().is(Character.class);
    }

    @Override
    public Object generate(Context context) {
        final ObjectoSettings settings = context.getSettings();
        Range<Character> range = settings.chars();
        return (char) context.getRandom().nextInt(range.min(), range.max());
    }

}
