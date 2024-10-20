package com.cariochi.objecto.utils;

import com.cariochi.reflecto.methods.ReflectoMethod;
import com.cariochi.reflecto.types.ReflectoType;
import java.util.Optional;
import lombok.experimental.UtilityClass;

import static com.cariochi.reflecto.Reflecto.reflect;

@UtilityClass
public class ConstructorUtils {

    public static Optional<Object> parseString(ReflectoType type, String value) {

        ReflectoType actualType = type.isPrimitive() ? reflect(getWrapperClass(type.actualClass())) : type;

        final Optional<Object> staticConstructor = actualType.methods().stream()
                .filter(method -> method.modifiers().isPublic() && method.modifiers().isStatic())
                .filter(method -> method.parameters().size() == 1 && method.parameters().get(0).type().is(CharSequence.class))
                .filter(method -> actualType.isAssignableFrom(method.returnType().actualType()))
                .map(ReflectoMethod::asStatic)
                .map(method -> method.invoke(value))
                .findFirst();

        final Optional<Object> regularConstructor = actualType.constructors().declared().stream()
                .filter(constructor -> constructor.modifiers().isPublic())
                .filter(constructor -> constructor.parameters().size() == 1 && constructor.parameters().get(0).type().is(CharSequence.class))
                .map(constructor -> constructor.newInstance(value))
                .findFirst();

        return staticConstructor.or(() -> regularConstructor);
    }

    public static Class<?> getWrapperClass(Class<?> primitiveType) {
        if (primitiveType == boolean.class) {return Boolean.class;}
        if (primitiveType == byte.class) {return Byte.class;}
        if (primitiveType == char.class) {return Character.class;}
        if (primitiveType == double.class) {return Double.class;}
        if (primitiveType == float.class) {return Float.class;}
        if (primitiveType == int.class) {return Integer.class;}
        if (primitiveType == long.class) {return Long.class;}
        if (primitiveType == short.class) {return Short.class;}
        if (primitiveType == void.class) {return Void.class;}
        return primitiveType;
    }
}
