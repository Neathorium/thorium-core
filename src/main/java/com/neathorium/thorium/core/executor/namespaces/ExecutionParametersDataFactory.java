package com.neathorium.thorium.core.executor.namespaces;

import com.neathorium.thorium.core.constants.CommandRangeDataConstants;
import com.neathorium.thorium.core.constants.ExecutorConstants;
import com.neathorium.thorium.core.records.command.CommandRangeData;
import com.neathorium.thorium.core.records.executor.ExecutionParametersData;
import com.neathorium.thorium.core.records.executor.ExecutionStateData;
import com.neathorium.thorium.core.records.executor.ExecutorFunctionData;
import com.neathorium.thorium.java.extensions.interfaces.functional.TriFunction;

import java.util.List;

public interface ExecutionParametersDataFactory {
    static <ArrayType, ReturnType> ExecutionParametersData<ArrayType, ReturnType> getWith(
        ExecutorFunctionData data,
        TriFunction<ExecutorFunctionData, ExecutionStateData, List<ArrayType>, ReturnType> executor,
        CommandRangeData range
    ) {
        return new ExecutionParametersData<>(data, executor, range);
    }

    static <ArrayType, ReturnType> ExecutionParametersData<ArrayType, ReturnType> getWithTwoCommandsRange(
        ExecutorFunctionData data,
        TriFunction<ExecutorFunctionData, ExecutionStateData, List<ArrayType>, ReturnType> executor
    ) {
        return ExecutionParametersDataFactory.getWith(data, executor, CommandRangeDataConstants.TWO_COMMANDS_RANGE);
    }

    static <ArrayType, ReturnType> ExecutionParametersData<ArrayType, ReturnType> getWithDefaultRange(
        ExecutorFunctionData data,
        TriFunction<ExecutorFunctionData, ExecutionStateData, List<ArrayType>, ReturnType> executor
    ) {
        return ExecutionParametersDataFactory.getWith(data, executor, CommandRangeDataConstants.DEFAULT_RANGE);
    }

    static <ArrayType, ReturnType> ExecutionParametersData<ArrayType, ReturnType> getWithDefaultFunctionData(
        TriFunction<ExecutorFunctionData, ExecutionStateData, List<ArrayType>, ReturnType> executor,
        CommandRangeData range
    ) {
        return ExecutionParametersDataFactory.getWith(ExecutorConstants.DEFAULT_EXECUTION_ENDED, executor, range);
    }

    static <ArrayType, ReturnType> ExecutionParametersData<ArrayType, ReturnType> getWithDefaultFunctionDataAndDefaultRange(
        TriFunction<ExecutorFunctionData, ExecutionStateData, List<ArrayType>, ReturnType> executor
    ) {
        return ExecutionParametersDataFactory.getWithDefaultFunctionData(executor, CommandRangeDataConstants.DEFAULT_RANGE);
    }

    static <ArrayType, ReturnType> ExecutionParametersData<ArrayType, ReturnType> getWithDefaultFunctionDataAndTwoCommandRange(
        TriFunction<ExecutorFunctionData, ExecutionStateData, List<ArrayType>, ReturnType> executor
    ) {
        return ExecutionParametersDataFactory.getWithDefaultFunctionData(executor, CommandRangeDataConstants.TWO_COMMANDS_RANGE);
    }
}
