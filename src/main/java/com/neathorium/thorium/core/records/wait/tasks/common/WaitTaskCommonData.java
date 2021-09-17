package com.neathorium.thorium.core.records.wait.tasks.common;

import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;

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
        if (CoreUtilities.isEqual(this, o)) {
            return true;
        }

        if (NullableFunctions.isNull(o) || CoreUtilities.isNotEqual(getClass(), o.getClass())) {
            return false;
        }

        final var that = (WaitTaskCommonData<?, ?, ?>) o;
        return (
            CoreUtilities.isEqual(function, that.function) &&
            CoreUtilities.isEqual(exitCondition, that.exitCondition)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(function, exitCondition);
    }
}
