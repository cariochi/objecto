package com.cariochi.objecto.generators;

import com.cariochi.objecto.settings.Settings;
import com.cariochi.objecto.utils.Random;
import java.lang.reflect.Type;

class PrimitiveGenerator implements Generator {

    @Override
    public boolean isSupported(Context context) {
        return context.getRawClass().isPrimitive();
    }

    @Override
    public Object generate(Context context) {
        final Type type = context.getType();
        final Settings settings = context.getSettings();
        if (type.equals(int.class)) {
            return Random.nextInt(settings.integersRange());
        } else if (type.equals(double.class)) {
            return Random.nextDouble(settings.doublesRange());
        } else if (type.equals(float.class)) {
            return Random.nextFloat(settings.floatsRange());
        } else if (type.equals(long.class)) {
            return Random.nextLong(settings.longsRange());
        } else if (type.equals(short.class)) {
            return (short) Random.nextInt(settings.integersRange());
        } else if (type.equals(byte.class)) {
            return (byte) Random.nextInt(settings.bytesRange());
        } else if (type.equals(char.class)) {
            return (char) Random.nextInt(settings.bytesRange());
        } else if (type.equals(boolean.class)) {
            return Random.nextBoolean();
        } else {
            return null;
        }
    }

}
