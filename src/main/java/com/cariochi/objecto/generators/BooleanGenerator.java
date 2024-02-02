package com.cariochi.objecto.generators;

class BooleanGenerator extends AbstractGenerator {

    @Override
    public boolean isSupported(Context context) {
        return context.getType().is(Boolean.class);
    }

    @Override
    public Object generate(Context context) {
        return context.getRandom().nextBoolean();
    }

}
