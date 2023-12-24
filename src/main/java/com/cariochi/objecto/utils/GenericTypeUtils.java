package com.cariochi.objecto.utils;

import com.cariochi.reflecto.types.Types;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Optional;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.reflect.TypeUtils;

import static org.apache.commons.lang3.reflect.TypeUtils.getTypeArguments;

@UtilityClass
public class GenericTypeUtils {

    public static Class<?> getRawClass(Type type, Type assigningType) {
        return TypeUtils.getRawType(type, assigningType);
    }

    public static Type getRawType(Type type, Type assigningType) {
        if (type instanceof Class<?> || type instanceof ParameterizedType) {
            return type;
        } else if (type instanceof TypeVariable<?>) {
            return assigningType == null ? null : getType((TypeVariable<?>) type, assigningType);
        } else if (type instanceof GenericArrayType) {
            return getType((GenericArrayType) type, assigningType);
        } else {
            return null;
        }
    }

    private static Type getType(TypeVariable<?> type, Type assigningType) {
        return Optional.of(type.getGenericDeclaration())
                .filter(Class.class::isInstance)
                .map(Class.class::cast)
                .map(aClass -> getTypeArguments(assigningType, aClass))
                .map(typeArguments -> typeArguments.get(type))
                .map(typeArgument -> getRawType(typeArgument, assigningType))
                .orElse(null);
    }

    private static Type getType(GenericArrayType type, Type assigningType) {
        final Type componentType = type.getGenericComponentType();
        final Type rawType = getRawType(componentType, assigningType);
        return rawType != null ? Types.type(rawType.getTypeName() + "[]") : null;
    }


}
