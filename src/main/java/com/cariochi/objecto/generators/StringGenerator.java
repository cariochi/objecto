package com.cariochi.objecto.generators;

import com.cariochi.objecto.utils.Random;

class StringGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return context.getRawClass().equals(String.class);
    }

    @Override
    public Object generate(Context context) {
        return Random.nextString(context.getSettings().stringsSettings());
    }


}
