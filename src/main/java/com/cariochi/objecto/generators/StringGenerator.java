package com.cariochi.objecto.generators;

class StringGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return context.getType().is(String.class);
    }

    @Override
    public Object generate(Context context) {
        String random = context.getRandom().nextString(context.getSettings().strings());
        if (context.getSettings().strings().fieldNamePrefix()) {
            random = context.getFieldName() + " " + random;
        }
        return random;
    }


}
