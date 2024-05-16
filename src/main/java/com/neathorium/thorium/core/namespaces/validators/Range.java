package com.neathorium.thorium.core.namespaces.validators;

import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;

public interface Range {
    static Boolean isOutOfRange(int min, int length, int max) {
        return (length < min) || (length > max);
    }

    static Boolean isOutOfRange(long min, long length, long max) {
        return (length < min) || (length > max);
    }

    static Boolean isOutOfRange(Integer min, Integer length, Integer max) {
        return NullablePredicates.areNotNull(min, length, max) && ((length < min) || (length > max));
    }

    static Boolean isOutOf(int min, int length, int max) {
        return Range.isOutOfRange(min, length, max);
    }

    static Boolean isOutOf(long min, long length, long max) {
        return Range.isOutOfRange(min, length, max);
    }
}
