package com.neathorium.thorium.core.executor.namespaces;

import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.records.executor.ExecutionStepsData;
import com.neathorium.thorium.java.extensions.namespaces.predicates.BasicPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;

import java.util.List;
import java.util.function.Function;

public interface ExecutionStepsDataFactory {
    static <DependencyType> ExecutionStepsData<DependencyType> getWith(List<Function<DependencyType, Data<?>>> steps, DependencyType dependency, int length) {
        final var localLength = BasicPredicates.isNonNegative(length) ? length : 0;
        return new ExecutionStepsData<>(steps, dependency, localLength);
    }

    static <DependencyType> ExecutionStepsData<DependencyType> getWithStepsAndDependency(List<Function<DependencyType, Data<?>>> steps, DependencyType dependency) {
        if (NullablePredicates.isNull(steps)) {
            return ExecutionStepsDataFactory.getWith(List.of(), dependency, 0);
        }

        final var stepLength = steps.size();
        final var length = BasicPredicates.isNonNegative(stepLength) ? stepLength : 0;
        return ExecutionStepsDataFactory.getWith(steps, dependency, length);
    }
}
