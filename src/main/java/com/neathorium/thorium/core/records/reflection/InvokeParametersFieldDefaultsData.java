package com.neathorium.thorium.core.records.reflection;

import com.neathorium.thorium.core.extensions.interfaces.functional.TriFunction;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Predicate;

public class InvokeParametersFieldDefaultsData<T> {
    public final Predicate<Object[]> validator;
    public final TriFunction<Method, T, Object[], Object> handler;

    public InvokeParametersFieldDefaultsData(Predicate<Object[]> validator, TriFunction<Method, T, Object[], Object> handler) {
        this.validator = validator;
        this.handler = handler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (InvokeParametersFieldDefaultsData<?>) o;
        return Objects.equals(validator, that.validator) && Objects.equals(handler, that.handler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(validator, handler);
    }
}
