package com.neathorium.thorium.core.records.executor;

import com.neathorium.thorium.core.records.command.CommandRangeData;
import com.neathorium.thorium.java.extensions.interfaces.functional.TriFunction;

import java.util.List;
import java.util.Objects;

public record ExecutionParametersData<ArrayType, ReturnType> (
    ExecutorFunctionData FUNCTION_DATA,
    TriFunction<ExecutorFunctionData, ExecutionStateData, List<ArrayType>, ReturnType> EXECUTOR,
    CommandRangeData RANGE
) {}
