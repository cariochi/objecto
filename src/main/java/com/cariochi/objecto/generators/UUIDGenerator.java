package com.cariochi.objecto.generators;

import java.util.UUID;

class UUIDGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return context.getType().is(UUID.class);
    }

    @Override
    public Object generate(Context context) {
        return context.getRandom().nextUUID();
    }

}
