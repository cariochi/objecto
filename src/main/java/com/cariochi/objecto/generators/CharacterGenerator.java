package com.cariochi.objecto.generators;

import com.cariochi.objecto.config.Range;

class CharacterGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return context.getType().is(Character.class);
    }

    @Override
    public Object generate(Context context) {
        Range<Character> range = context.getConfig().chars();
        return (char) context.getRandom().nextInt(range.from(), range.to());
    }

}
