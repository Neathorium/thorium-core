package com.neathorium.thorium.core.extensions.interfaces.functional.boilers;

import java.util.function.Function;

@FunctionalInterface
public interface IContained<T, U> {
    Function<T, U> get();
}
