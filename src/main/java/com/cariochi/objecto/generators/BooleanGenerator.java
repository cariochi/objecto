package com.cariochi.objecto.generators;

import com.cariochi.objecto.utils.Random;

class BooleanGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return context.getType().equals(Boolean.class);
    }

    @Override
    public Object generate(Context context) {
        return Random.nextBoolean();
    }

}
