package com.neathorium.thorium.core.wait.records.tasks.common;

import com.neathorium.thorium.java.extensions.namespaces.predicates.EqualsPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class WaitTaskCommonData<T, U, V> {
    public final Function<T, U> function;
    public final Predicate<V> exitCondition;

    public WaitTaskCommonData(Function<T, U> function, Predicate<V> exitCondition) {
        this.function = function;
        this.exitCondition = exitCondition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (NullablePredicates.isNull(o) || EqualsPredicates.isNotEqual(getClass(), o.getClass())) {
            return false;
        }

        final var that = (WaitTaskCommonData<?, ?, ?>) o;
        return (
            EqualsPredicates.isEqual(function, that.function) &&
            EqualsPredicates.isEqual(exitCondition, that.exitCondition)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(function, exitCondition);
    }
}
