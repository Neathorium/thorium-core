package com.neathorium.thorium.core.executor.namespaces;

import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.records.executor.ExecutionStateData;
import com.neathorium.thorium.java.extensions.namespaces.predicates.BasicPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public interface ExecutionStateDataFactory {
    private static Map<String, Data<?>> getDefaultMap() {
        return new LinkedHashMap<>();
    }

    private static List<Integer> getDefaultList() {
        return new ArrayList<>();
    }

    private static List<Integer> getListWithIndices(int length) {
        final var localLength = BasicPredicates.isPositiveNonZero(length) ? length : 0;
        final var list = IntStream.range(0, localLength < 2 ? 1 : localLength).boxed().toList();
        return new ArrayList<>(list);
    }

    static ExecutionStateData getWith(Map<String, Data<?>> map, List<Integer> indices) {
        final var localMap = NullablePredicates.isNotNull(map) ? map : ExecutionStateDataFactory.getDefaultMap();
        final var localIndices = NullablePredicates.isNotNull(indices) ? indices : ExecutionStateDataFactory.getDefaultList();
        return new ExecutionStateData(localMap, localIndices);
    }

    static ExecutionStateData getWithDefaults() {
        return ExecutionStateDataFactory.getWith(ExecutionStateDataFactory.getDefaultMap(), ExecutionStateDataFactory.getDefaultList());
    }

    static ExecutionStateData getWithDefaultIndices(Map<String, Data<?>> map) {
        return ExecutionStateDataFactory.getWith(map, ExecutionStateDataFactory.getDefaultList());
    }

    static ExecutionStateData getWithDefaultMap(List<Integer> indices) {
        return ExecutionStateDataFactory.getWith(ExecutionStateDataFactory.getDefaultMap(), indices);
    }

    static ExecutionStateData getWithDefaultMapAndSpecificLength(int length) {
        final var localLength = BasicPredicates.isPositiveNonZero(length) ? length : 0;
        return ExecutionStateDataFactory.getWithDefaultMap(ExecutionStateDataFactory.getListWithIndices(localLength));
    }

    static ExecutionStateData getWith(Map<String, Data<?>> map, int length) {
        return ExecutionStateDataFactory.getWith(map, ExecutionStateDataFactory.getListWithIndices(length));
    }

    static ExecutionStateData getWith(int length) {
        return ExecutionStateDataFactory.getWith(ExecutionStateDataFactory.getDefaultMap(), ExecutionStateDataFactory.getListWithIndices(length));
    }
}
