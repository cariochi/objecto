package com.cariochi.objecto.generators;

class BooleanGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return context.getType().is(Boolean.class);
    }

    @Override
    public Object generate(Context context) {
        return context.getRandom().nextBoolean();
    }

}
