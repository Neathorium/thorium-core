package com.neathorium.thorium.core.extensions.interfaces.functional;

import java.util.function.Predicate;

/**
 * Represents a function that accepts four arguments, produces a result.
 * This is the four-arity interface following the style of functional
 * interfaces {@link java.util.function.Predicate} and {@link java.util.function.BiPredicate BiFunction}
 * from the JDK.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <V> the type of the third argument to the function
 * @param <W> the type of the fourth argument to the function
 *
 * @see TriPredicate
 * @see java.util.function.BiPredicate
 * @see Predicate
 * @since 0.1
 */
@FunctionalInterface
public interface QuadPredicate<T, U, V, W> {
    boolean test(T t, U u, V v, W w);
}