package com.cariochi.objecto.utils;

import com.cariochi.reflecto.types.TypeName;
import com.cariochi.reflecto.types.Types;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Optional;
import lombok.experimental.UtilityClass;

import static org.apache.commons.lang3.reflect.TypeUtils.getTypeArguments;

@UtilityClass
public class GenericTypeUtils {

    public static Type getActualType(final Type type, final Type assigningType) {
        if (type instanceof Class<?> || type instanceof ParameterizedType) {

            return type;

        } else if (type instanceof TypeVariable<?>) {

            if (assigningType == null) {
                return null;
            }
            return Optional.of(type)
                    .map(TypeVariable.class::cast)
                    .map(TypeVariable::getGenericDeclaration)
                    .filter(Class.class::isInstance)
                    .map(Class.class::cast)
                    .map(cl -> getTypeArguments(assigningType, cl))
                    .map(typeVarAssigns -> typeVarAssigns.get(type))
                    .map(typeArgument -> getActualType(typeArgument, assigningType))
                    .orElse(null);

        } else if (type instanceof GenericArrayType) {

            final Type rawType = getActualType(((GenericArrayType) type).getGenericComponentType(), assigningType);
            return rawType != null ? Types.type(TypeName.parse(rawType.getTypeName() + "[]")) : null;

        }

        return null;
    }

}
