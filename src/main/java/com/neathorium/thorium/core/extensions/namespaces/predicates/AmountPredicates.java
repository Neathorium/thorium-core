package com.neathorium.thorium.core.extensions.namespaces.predicates;

import com.neathorium.thorium.core.extensions.namespaces.SizableFunctions;

import java.util.function.Supplier;

import static com.neathorium.thorium.core.extensions.namespaces.NullableFunctions.isNotNull;

public interface AmountPredicates {
    static boolean isSingle(Supplier<Integer> sizeFunction) {
        return SizablePredicates.isSizeEqualTo(sizeFunction, 1);
    }

    static boolean isDouble(Supplier<Integer> sizeFunction) {
        return SizablePredicates.isSizeEqualTo(sizeFunction, 2);
    }

    static boolean hasMoreThan(Supplier<Integer> sizeFunction, int amount) {
        return (
            BasicPredicates.isNonNegative(amount) &&
            BasicPredicates.isBiggerThan(SizableFunctions.size(sizeFunction), amount)
        );
    }

    static boolean hasMoreThan(Object[] object, int amount) {
        return (
            BasicPredicates.isNonNegative(amount) &&
            BasicPredicates.isBiggerThan(SizableFunctions.size(object), amount)
        );
    }

    static boolean isAtleastDouble(Supplier<Integer> sizeFunction) {
        return hasMoreThan(sizeFunction, 2);
    }

    static boolean isMany(Supplier<Integer> sizeFunction) {
        return hasMoreThan(sizeFunction, 1);
    }

    static boolean hasAtleast(Supplier<Integer> sizeFunction, int amount) {
        return hasMoreThan(sizeFunction, amount - 1);
    }

    static boolean hasIndex(Supplier<Integer> sizeFunction, int index) {
        return hasMoreThan(sizeFunction, index);
    }

    static boolean isSingle(Object[] object) {
        return isNotNull(object) && SizablePredicates.isSizeEqualTo(object.length, 1);
    }

    static boolean isDouble(Object[] object) {
        return isNotNull(object) && SizablePredicates.isSizeEqualTo(object.length, 2);
    }

    static boolean isNonZero(Object[] object) {
        return isNotNull(object) && BasicPredicates.isBiggerThan(object.length, 0);
    }

    static boolean hasIndex(Object[] object, int index) {
        return hasMoreThan(object, index);
    }
}
