package com.cariochi.objecto.generators;

interface Generator {

    boolean isSupported(Context context);

    Object generate(Context context);

}
