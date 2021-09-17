package com.neathorium.thorium.core.namespaces.executor;

import com.neathorium.thorium.core.extensions.namespaces.predicates.BasicPredicates;
import com.neathorium.thorium.core.records.Data;
import com.neathorium.thorium.core.records.executor.ExecutionStateData;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
        return IntStream.range(0, localLength < 2 ? 1 : localLength).boxed().collect(Collectors.toList());
    }

    static ExecutionStateData getWith(Map<String, Data<?>> map, List<Integer> indices) {
        return new ExecutionStateData(
            NullableFunctions.isNotNull(map) ? map : getDefaultMap(),
            NullableFunctions.isNotNull(indices) ? indices : getDefaultList()
        );
    }

    static ExecutionStateData getWithDefaults() {
        return getWith(getDefaultMap(), getDefaultList());
    }

    static ExecutionStateData getWithDefaultIndices(Map<String, Data<?>> map) {
        return getWith(map, getDefaultList());
    }

    static ExecutionStateData getWithDefaultMap(List<Integer> indices) {
        return getWith(getDefaultMap(), indices);
    }

    static ExecutionStateData getWithDefaultMapAndSpecificLength(int length) {
        final var localLength = BasicPredicates.isPositiveNonZero(length) ? length : 0;
        return getWithDefaultMap(getListWithIndices(localLength));
    }

    static ExecutionStateData getWith(Map<String, Data<?>> map, int length) {
        return getWith(map, getListWithIndices(length));
    }
}
