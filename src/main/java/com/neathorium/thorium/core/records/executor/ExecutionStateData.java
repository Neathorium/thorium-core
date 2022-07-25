package com.neathorium.thorium.core.records.executor;

import com.neathorium.thorium.core.data.records.Data;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ExecutionStateData {
    public final Map<String, Data<?>> executionMap;
    public final List<Integer> indices;

    public ExecutionStateData(Map<String, Data<?>> executionMap, List<Integer> indices) {
        this.executionMap = executionMap;
        this.indices = indices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (ExecutionStateData) o;
        return Objects.equals(executionMap, that.executionMap) && Objects.equals(indices, that.indices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(executionMap, indices);
    }
}
