package com.neathorium.thorium.core.namespaces.executor;

import com.neathorium.thorium.core.data.namespaces.predicates.DataPredicates;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.records.executor.ExecutionStateData;
import com.neathorium.thorium.java.extensions.interfaces.functional.QuadPredicate;
import com.neathorium.thorium.java.extensions.namespaces.predicates.BasicPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.EqualsPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.GuardPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.SizablePredicates;

public interface ExecutorFunctionDataFunctions {
    static boolean isAllDone(ExecutionStateData stateData, int length, int index, int indicesLength) {
        if (
            NullablePredicates.isNull(stateData) ||
            GuardPredicates.areAny(BasicPredicates::isNegative, length, index, indicesLength) ||
            BasicPredicates.isSmallerThan(length, indicesLength) ||
            BasicPredicates.isSmallerThan(length, index)
        ) {
            return false;
        }

        final var executionMap = stateData.executionMap;
        final var mapNull = NullablePredicates.isNull(executionMap);
        final var indicesRemain = BasicPredicates.isPositiveNonZero(indicesLength);
        if (mapNull || indicesRemain) {
            return false;
        }

        final var mapSize = executionMap.size();
        final var mapFilled = SizablePredicates.isSizeEqualTo(mapSize, length);
        final var areValid = GuardPredicates.areAll(DataPredicates::isValidNonFalse, executionMap.values().toArray(new Data[0]));
        return (mapFilled && areValid);
    }

    static boolean isAnyDone(ExecutionStateData stateData, int length, int index, int indicesLength) {
        if (
            NullablePredicates.isNull(stateData) ||
            GuardPredicates.areAny(BasicPredicates::isNegative, length, index, indicesLength) ||
            BasicPredicates.isSmallerThan(length, indicesLength) ||
            BasicPredicates.isSmallerThan(length, index)
        ) {
            return false;
        }

        final var executionMap = stateData.executionMap;
        final var mapNull = NullablePredicates.isNull(executionMap);
        final var indicesRemain = BasicPredicates.isPositiveNonZero(indicesLength);
        if (mapNull || indicesRemain) {
            return false;
        }

        final var mapSize = executionMap.size();
        final var mapFilled = SizablePredicates.isSizeEqualTo(mapSize, length);
        final var areValid = DataPredicates.isValidNonFalse(executionMap.values().toArray(new Data[0])[mapSize-1]);
        return (mapFilled && areValid);
    }

    static QuadPredicate<ExecutionStateData, Integer, Integer, Integer> isSomeDone(int expected) {
        return (stateData, length, index, indicesLength) -> isAnyDone(stateData, length, index, indicesLength) && EqualsPredicates.isEqual((length - indicesLength), expected);
    }
}
