package com.neathorium.thorium.core.extensions.interfaces.functional;

import java.util.Objects;
import java.util.function.Function;

/**
 * Represents a function that accepts four arguments, produces a result.
 * This is the three-arity interface following the style of functional
 * interfaces {@link java.util.function.Function} and {@link java.util.function.BiFunction BiFunction}
 * from the JDK.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <V> the type of the third argument to the function
 * @param <R> the type of the result of the function
 *
 * @see QuadFunction
 */
@FunctionalInterface
public interface TriFunction<T, U, V, R> {

    R apply(T t, U u, V v);

    default <S> TriFunction<T, U, V, S> andThen(Function<? super R, ? extends S> after) {
        Objects.requireNonNull(after);
        return (T t, U u, V v) -> after.apply(apply(t, u, v));
    }
}