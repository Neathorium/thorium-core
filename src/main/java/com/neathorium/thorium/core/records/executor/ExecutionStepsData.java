package com.neathorium.thorium.core.records.executor;

import com.neathorium.thorium.core.data.records.Data;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

public class ExecutionStepsData<DependencyType> {
    public final Function<DependencyType, Data<?>>[] steps;
    public final DependencyType dependency;
    public final int length;

    public ExecutionStepsData(Function<DependencyType, Data<?>>[] steps, DependencyType dependency, int length) {
        this.steps = steps;
        this.dependency = dependency;
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (ExecutionStepsData<?>) o;
        return length == that.length && Arrays.equals(steps, that.steps) && Objects.equals(dependency, that.dependency);
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(dependency, length) + Arrays.hashCode(steps);
    }
}
