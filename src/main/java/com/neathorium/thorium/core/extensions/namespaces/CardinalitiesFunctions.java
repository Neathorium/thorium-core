package com.neathorium.thorium.core.extensions.namespaces;

import java.util.function.Function;
import java.util.function.Predicate;

public interface CardinalitiesFunctions<T> {
    static <T> Predicate<T> noopBoolean(Predicate<T> object) {
        return object;
    }

    static <T> Predicate<T> invertBoolean(Predicate<T> object) {
        return object.negate();
    }

    static <T> Function<Predicate<T>, Predicate<T>> getPredicate(boolean invert) {
        return invert ? CardinalitiesFunctions::invertBoolean : CardinalitiesFunctions::noopBoolean;
    }

    static boolean invertBoolean(boolean status) {
        return !status;
    }

    static boolean noopBoolean(boolean status) {
        return status;
    }
}
