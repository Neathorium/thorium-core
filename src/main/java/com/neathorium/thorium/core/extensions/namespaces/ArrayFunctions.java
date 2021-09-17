package com.neathorium.thorium.core.extensions.namespaces;

import com.neathorium.thorium.core.extensions.namespaces.predicates.BasicPredicates;

import java.util.Objects;

public interface ArrayFunctions {
    static <T> boolean isNullOrEmptyArray(T[] array) {
        if (NullableFunctions.isNull(array)) {
            return true;
        }

        final var length = array.length;
        final var expectedSize = 1;
        return (
            BasicPredicates.isBiggerThan(expectedSize, length) ||
            (Objects.equals(length, expectedSize) && NullableFunctions.isNull(array[0]))
        );
    }
}
