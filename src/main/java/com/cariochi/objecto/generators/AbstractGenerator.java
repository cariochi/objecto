package com.cariochi.objecto.generators;

abstract class AbstractGenerator {

    abstract boolean isSupported(Context context);

    abstract Object generate(Context context);

}
