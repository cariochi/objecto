package com.cariochi.objecto.generators;

import com.cariochi.objecto.settings.Settings;

class CharacterGenerator extends AbstractGenerator {

    @Override
    public boolean isSupported(Context context) {
        return context.getType().is(Character.class);
    }

    @Override
    public Object generate(Context context) {
        final Settings settings = context.getSettings();
        return (char) context.getRandom().nextInt(settings.bytes());
    }

}
