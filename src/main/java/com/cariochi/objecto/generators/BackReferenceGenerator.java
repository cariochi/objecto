package com.cariochi.objecto.generators;

import java.lang.reflect.Type;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BackReferenceGenerator implements Generator {

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
        if (type.equals(context.getType())) {
            // find direct references
            return context.findPreviousContext(path)
                    .map(Context::getPrevious)
                    .filter(referenceContext -> referenceContext.getType().equals(context.getType()));
        } else {
            // find reversed references
            final Deque<String> items = new LinkedList<>(List.of(path.split("\\.")));
            for (int i = 0; i < items.size() - 1; i++) {
                items.addFirst(items.removeLast());
                String p = String.join(".", items);
                final Optional<Context> possibleContext = context.findPreviousContext(p)
                        .map(Context::getPrevious)
                        .filter(referenceContext -> referenceContext.getType().equals(context.getType()));
                if (possibleContext.isPresent()) {
                    return possibleContext;
                }
            }
            return Optional.empty();
        }
    }

}
