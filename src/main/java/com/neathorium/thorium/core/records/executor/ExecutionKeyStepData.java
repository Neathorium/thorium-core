package com.neathorium.thorium.core.records.executor;

import com.neathorium.thorium.core.extensions.interfaces.functional.boilers.DataSupplier;
import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;
import com.neathorium.thorium.core.records.Data;

import java.util.Objects;
import java.util.function.Function;

public class ExecutionKeyStepData<T, U, V> implements DataSupplier<V> {
    public final Function<U, Data<V>> step;
    public final Function<T, U> dependencyGetter;
    public final T dependencyKey;

    public ExecutionKeyStepData(Function<U, Data<V>> step, Function<T, U> dependencyGetter, T dependencyKey) {
        this.step = step;
        this.dependencyGetter = dependencyGetter;
        this.dependencyKey = dependencyKey;
    }

    @Override
    public boolean equals(Object o) {
        if (CoreUtilities.isEqual(this, o)) {
            return true;
        }

        if (NullableFunctions.isNull(o) || CoreUtilities.isNotEqual(getClass(), o.getClass())) {
            return false;
        }

        final var that = (ExecutionKeyStepData<?, ?, ?>) o;
        return (
            CoreUtilities.isEqual(step, that.step) &&
            CoreUtilities.isEqual(dependencyGetter, that.dependencyGetter) &&
            CoreUtilities.isEqual(dependencyKey, that.dependencyKey)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(step, dependencyGetter, dependencyKey);
    }

    @Override
    public String toString() {
        return (
            "ExecutionKeyStepData{" + String.join(", ", "step=" + step, "dependencyGetter=" + dependencyGetter, "dependencyKey=") + dependencyKey + '}'
        );
    }

    @Override
    public Data<V> apply() {
        return step.apply(dependencyGetter.apply(dependencyKey));
    }

    @Override
    public Data<V> apply(Void o) {
        return apply();
    }
}
