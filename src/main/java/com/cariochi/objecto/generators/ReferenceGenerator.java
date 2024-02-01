package com.cariochi.objecto.generators;

import com.cariochi.reflecto.types.TypeReflection;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReferenceGenerator implements Generator {

    private final Type type;
    private final String path;

    @Override
    public boolean isSupported(Context context) {
        if (context.getFieldName().startsWith("[") && !context.getFieldName().equals("[0]")) {
            return false;
        }
        return findReferenceContext(context).isPresent();
    }

    @Override
    public Object generate(Context context) {
        return findReferenceContext(context)
                .map(Context::getInstance)
                .orElse(null);
    }

    private Optional<Context> findReferenceContext(Context context) {
        final LinkedList<String> items = new LinkedList<>(List.of(path.split("\\.")));
        for (int i = 0; i < items.size(); i++) {

            final Optional<Context> possibleContext = findContext(items, context)
                    .filter(referenceContext -> referenceContext.getType().actualType().equals(context.getType().actualType()));

            if (possibleContext.isPresent()) {
                return possibleContext;
            }

            items.addFirst(items.removeLast());

        }
        return Optional.empty();
    }

    private Optional<Context> findContext(List<String> items, Context context) {
        boolean hasType = type.equals(context.getType().actualType());
        Optional<Context> possibleContext = Optional.of(context);
        for (int i = 0; i < items.size(); i++) {
            final String item = items.get(items.size() - 1 - i);
            possibleContext = possibleContext.flatMap(c -> c.findPreviousContext(item)).map(Context::getPrevious);
            if (possibleContext.isEmpty()) {
                return Optional.empty();
            }
            hasType |= type.equals(possibleContext.map(Context::getType).map(TypeReflection::actualType).orElse(null));
        }
        return hasType ? possibleContext : Optional.empty();
    }

}
