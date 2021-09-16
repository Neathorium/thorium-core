package com.neathorium.thorium.core.extensions.namespaces;

import com.neathorium.thorium.core.extensions.namespaces.predicates.SizablePredicates;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public interface EmptiableFunctions {
    static boolean isEmpty(Collection<?> collection) {
        return NullableFunctions.isNotNull(collection) && collection.isEmpty();
    }

    static boolean isNullOrEmpty(Collection<?> collection) {
        return NullableFunctions.isNull(collection) || collection.isEmpty();
    }

    static boolean isNotNullAndNonEmpty(Collection<?> collection) {
        return !(isNullOrEmpty(collection));
    }

    static boolean isEmpty(Map<?, ?> map) {
        return NullableFunctions.isNotNull(map) && map.isEmpty();
    }

    static boolean isNullOrEmpty(Map<?, ?> map) {
        return NullableFunctions.isNull(map) || map.isEmpty();
    }

    static boolean isNotNullAndNonEmpty(Map<?, ?> map) {
        return !(isNullOrEmpty(map));
    }

    static boolean hasOnlyNonNullValues(Collection<?> collection) {
        return (
            isNotNullAndNonEmpty(collection) &&
            SizablePredicates.isSizeEqualTo(collection::size, (int)collection.stream().filter(NullableFunctions::isNotNull).count())
        );
    }

    static boolean hasOnlyNonNullValues(Map<? ,?> map) {
        final var validMap = isNotNullAndNonEmpty(map);
        if (!validMap) {
            return false;
        }

        final var keys = Arrays.asList(map.keySet().toArray());
        final var values = Arrays.asList(map.values().toArray());
        return (
            SizablePredicates.isSizeEqualTo(map::size, (int)keys.stream().filter(NullableFunctions::isNotNull).count()) &&
            SizablePredicates.isSizeEqualTo(map::size, (int)values.stream().filter(NullableFunctions::isNotNull).count())
        );
    }
}
