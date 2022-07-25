package com.neathorium.thorium.core.records.reflection;


import com.neathorium.thorium.java.extensions.interfaces.functional.TriFunction;
import com.neathorium.thorium.java.extensions.namespaces.predicates.EqualsPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;

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
        if (this == o) {
            return true;
        }

        if (NullablePredicates.isNull(o) || EqualsPredicates.isNotEqual(getClass(), o.getClass())) {
            return false;
        }

        final var that = (InvokeParametersFieldDefaultsData<?>) o;
        return (
            EqualsPredicates.isEqual(validator, that.validator) &&
            EqualsPredicates.isEqual(handler, that.handler)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(validator, handler);
    }
}
