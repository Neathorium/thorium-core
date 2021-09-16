package com.neathorium.thorium.core.extensions.namespaces.predicates;

import java.util.Collection;

import static com.neathorium.thorium.core.extensions.namespaces.EmptiableFunctions.isNotNullAndNonEmpty;
import static com.neathorium.thorium.core.extensions.namespaces.EmptiableFunctions.isNullOrEmpty;

public interface CollectionPredicates {
    static <T, U> boolean isEmptyOrNotOfType(Collection<T> collection, Class<U> clazz) {
        return isNullOrEmpty(collection) || !clazz.isInstance(collection.iterator().next());
    }

    static <T, U> boolean isNonEmptyAndOfType(Collection<T> collection, Class<U> clazz) {
        return isNotNullAndNonEmpty(collection) && clazz.isInstance(collection.iterator().next());
    }
}
