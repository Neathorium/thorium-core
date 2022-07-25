package com.neathorium.thorium.core.records.executor;

import com.neathorium.thorium.core.records.command.CommandRangeData;
import com.neathorium.thorium.java.extensions.interfaces.functional.TriFunction;

import java.util.Objects;

public class ExecutionParametersData<ArrayType, ReturnType> {
    public final ExecutorFunctionData functionData;
    public final TriFunction<ExecutorFunctionData, ExecutionStateData, ArrayType[], ReturnType> executor;
    public final CommandRangeData range;

    public ExecutionParametersData(
        ExecutorFunctionData functionData,
        TriFunction<ExecutorFunctionData, ExecutionStateData, ArrayType[], ReturnType> executor,
        CommandRangeData range
    ) {
        this.functionData = functionData;
        this.executor = executor;
        this.range = range;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (ExecutionParametersData<?, ?>) o;
        return Objects.equals(functionData, that.functionData) && Objects.equals(executor, that.executor) && Objects.equals(range, that.range);
    }

    @Override
    public int hashCode() {
        return Objects.hash(functionData, executor, range);
    }
}
