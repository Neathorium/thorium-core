package com.neathorium.thorium.core.namespaces.executor;

import com.neathorium.thorium.core.extensions.namespaces.predicates.BasicPredicates;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;
import com.neathorium.thorium.core.records.Data;
import com.neathorium.thorium.core.records.executor.ExecutionStepsData;

import java.util.function.Function;

public interface ExecutionStepsDataFactory {
    static <DependencyType> ExecutionStepsData<DependencyType> getWith(Function<DependencyType, Data<?>>[] steps, DependencyType dependency, int length) {
        final var localLength = BasicPredicates.isNonNegative(length) ? length : 0;
        return new ExecutionStepsData<>(steps, dependency, localLength);
    }

    static <DependencyType> ExecutionStepsData<DependencyType> getWithStepsAndDependency(Function<DependencyType, Data<?>>[] steps, DependencyType dependency) {
        if (NullableFunctions.isNull(steps)) {
            return getWith(steps, dependency, 0);
        }

        final var stepLength = steps.length;
        final var length = BasicPredicates.isNonNegative(stepLength) ? stepLength : 0;
        return getWith(steps, dependency, length);
    }
}
