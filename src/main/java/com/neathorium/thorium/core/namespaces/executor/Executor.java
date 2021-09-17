package com.neathorium.thorium.core.namespaces.executor;

import com.neathorium.thorium.core.constants.CoreDataConstants;
import com.neathorium.thorium.core.constants.CoreConstants;
import com.neathorium.thorium.core.namespaces.DataExecutionFunctions;
import com.neathorium.thorium.core.namespaces.DataFactoryFunctions;
import com.neathorium.thorium.core.records.Data;
import com.neathorium.thorium.core.records.executor.ExecutionResultData;
import com.neathorium.thorium.core.records.executor.ExecutionStateData;
import com.neathorium.thorium.core.records.executor.ExecutionStepsData;
import com.neathorium.thorium.core.records.executor.ExecutorFunctionData;
import com.neathorium.thorium.core.records.executor.ExecutionParametersData;
import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;
import com.neathorium.thorium.core.namespaces.predicates.DataPredicates;

import java.util.function.Function;

public interface Executor {
    private static <DependencyType, ReturnType> Data<ExecutionResultData<ReturnType>> executeCore(
        ExecutionStepsData<DependencyType> stepsData,
        ExecutorFunctionData functionData,
        ExecutionStateData stateData
    ) {
        Data<?> data = CoreDataConstants.NO_STEPS;
        final var exitCondition = functionData.breakCondition;
        final var filter = functionData.filterCondition;
        final var indices = stateData.indices;
        final var map = stateData.executionMap;
        final var length = indices.size();
        final var steps = stepsData.steps;
        final var dependency = stepsData.dependency;
        var stepIndex = 0;
        var index = 0;
        var key = "";
        while (exitCondition.test(data, index, indices.size())) {
            stepIndex = indices.get(index);
            data = steps[stepIndex].apply(dependency);
            key = CoreFormatter.getExecutionResultKey(data.message.nameof, stepIndex);
            if (!map.containsKey(key) || DataPredicates.isInvalidOrFalse(map.get(key))) {
                map.put(key, data);
            }

            if (filter.test(data)) {
                indices.remove(index);
            } else {
                ++index;
            }
        }

        final var executionStatus = ExecutionStateDataFactory.getWith(map, indices);
        final var status = functionData.endCondition.test(executionStatus, steps.length, index, indices.size());
        final var message = functionData.messageData.get().apply(status) + CoreFormatterConstants.COLON_NEWLINE + functionData.endMessageHandler.apply(executionStatus, key, index, length);
        @SuppressWarnings("unchecked")
        final var returnObject = (ReturnType)data.object;
        return DataFactoryFunctions.getWith(ExecutionResultDataFactory.getWith(executionStatus, returnObject), status, "executeCore", message);
    }

    @SafeVarargs
    private static <DependencyType, ArrayType, ReturnType, ParameterReturnType> Function<DependencyType, Data<ReturnType>> executeGuardCore(
        ExecutionParametersData<ArrayType, Function<DependencyType, Data<ParameterReturnType>>> execution,
        Function<DependencyType, Data<ReturnType>> executionChain,
        Data<ReturnType> negative,
        Function<DependencyType, Data<?>>... steps
    ) {
        return DataExecutionFunctions.ifDependency("executeGuardCore", CoreFormatter.getValidCommandMessage(steps, execution.range), executionChain, negative);
    }

    @SafeVarargs
    static <DependencyType, ReturnType> Function<DependencyType, Data<ExecutionResultData<ReturnType>>> execute(
        ExecutionParametersData<Function<DependencyType, Data<?>>, Function<DependencyType, Data<ExecutionResultData<ReturnType>>>> execution,
        ExecutionStateData stateData,
        Function<DependencyType, Data<?>>... steps
    ) {
        @SuppressWarnings("unchecked")
        final var negativeReturnObject = (ReturnType) CoreConstants.STOCK_OBJECT;
        final var negative = DataFactoryFunctions.getInvalidWith(ExecutionResultDataFactory.getWithDefaultState(negativeReturnObject), "execute", CoreFormatterConstants.EMPTY);
        return executeGuardCore(execution, execution.executor.apply(execution.functionData, stateData, steps), negative, steps);
    }

    private static <DependencyType, ReturnType> Data<ReturnType> executeData(
        ExecutionStepsData<DependencyType> stepsData,
        ExecutionParametersData<Function<DependencyType, Data<?>>, Function<DependencyType, Data<ExecutionResultData<ReturnType>>>> execution
    ) {
        final var result = execute(execution, ExecutionStateDataFactory.getWithDefaultMapAndSpecificLength(stepsData.length), stepsData.steps).apply(stepsData.dependency);
        return DataFactoryFunctions.replaceObject(result, result.object.result);
    }

    @SafeVarargs
    private static <DependencyType, ReturnType> Function<DependencyType, Data<ReturnType>> executeData(
        ExecutionParametersData<Function<DependencyType, Data<?>>, Function<DependencyType, Data<ExecutionResultData<ReturnType>>>> execution,
        Function<DependencyType, Data<?>>... steps
    ) {
        return dependency -> executeData(ExecutionStepsDataFactory.getWithStepsAndDependency(steps, dependency), execution);
    }

    @SafeVarargs
    static <DependencyType, ReturnType> Function<DependencyType, Data<ExecutionResultData<ReturnType>>> execute(ExecutorFunctionData functionData, ExecutionStateData stateData, Function<DependencyType, Data<?>>... steps) {
        return dependency -> executeCore(ExecutionStepsDataFactory.getWithStepsAndDependency(steps, dependency), functionData, stateData);
    }

    @SafeVarargs
    static <DependencyType, ReturnType> Function<DependencyType, Data<ExecutionResultData<ReturnType>>> execute(ExecutorFunctionData functionData, Function<DependencyType, Data<?>>... steps) {
        return dependency -> executeCore(ExecutionStepsDataFactory.getWithStepsAndDependency(steps, dependency), functionData, ExecutionStateDataFactory.getWithDefaultMapAndSpecificLength(steps.length));
    }

    @SafeVarargs
    static <DependencyType, ReturnType> Function<DependencyType, Data<ReturnType>> execute(ExecutionParametersData<Function<DependencyType, Data<?>>, Function<DependencyType, Data<ExecutionResultData<ReturnType>>>> execution, Function<DependencyType, Data<?>>... steps) {
        @SuppressWarnings("unchecked")
        final var negativeReturnObject = (ReturnType) CoreConstants.STOCK_OBJECT;
        final var negative = DataFactoryFunctions.getWith(negativeReturnObject, false, CoreFormatterConstants.EMPTY);
        return executeGuardCore(execution, executeData(execution, steps), negative, steps);
    }
}
