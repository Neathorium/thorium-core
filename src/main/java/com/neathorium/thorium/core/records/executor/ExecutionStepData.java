package com.neathorium.thorium.core.records.executor;

import com.neathorium.thorium.core.extensions.interfaces.functional.boilers.DataSupplier;
import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;
import com.neathorium.thorium.core.records.Data;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class ExecutionStepData<DependencyType, ReturnType> implements DataSupplier<ReturnType> {
    public final Function<DependencyType, Data<ReturnType>> step;
    public final Supplier<DependencyType> dependency;

    public ExecutionStepData(Function<DependencyType, Data<ReturnType>> step, Supplier<DependencyType> dependency) {
        this.step = step;
        this.dependency = dependency;
    }

    @Override
    public boolean equals(Object o) {
        if (CoreUtilities.isEqual(this, o)) {
            return true;
        }

        if (NullableFunctions.isNull(o) || CoreUtilities.isNotEqual(getClass(), o.getClass())) {
            return false;
        }

        final var that = (ExecutionStepData<?, ?>) o;
        return CoreUtilities.isEqual(step, that.step) && CoreUtilities.isEqual(dependency, that.dependency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(step, dependency);
    }

    @Override
    public Data<ReturnType> apply() {
        return step.apply(dependency.get());
    }

    @Override
    public Data<ReturnType> apply(Void o) {
        return apply();
    }

    @Override
    public String toString() {
        return (
            "ExecutionStepData{" +
            "step=" + step +
            ", dependency=" + dependency +
            '}'
        );
    }
}
