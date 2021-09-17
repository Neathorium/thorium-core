package com.neathorium.thorium.core.records.executor;

import java.util.Objects;

public class ExecutionResultData<T> {
    public final ExecutionStateData stateData;
    public final T result;

    public ExecutionResultData(ExecutionStateData stateData, T result) {
        this.stateData = stateData;
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (ExecutionResultData<?>) o;
        return Objects.equals(stateData, that.stateData) && Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stateData, result);
    }
}
