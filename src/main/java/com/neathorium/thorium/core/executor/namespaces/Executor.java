package com.neathorium.thorium.core.executor.namespaces;

import com.neathorium.thorium.core.constants.CoreConstants;
import com.neathorium.thorium.core.constants.ExecutorConstants;
import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.data.namespaces.DataFunctions;
import com.neathorium.thorium.core.namespaces.DataExecutionFunctions;
import com.neathorium.thorium.core.data.namespaces.factories.DataFactoryFunctions;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.namespaces.MapFunctions;
import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;
import com.neathorium.thorium.core.records.executor.ExecutionParametersData;
import com.neathorium.thorium.core.records.executor.ExecutionResultData;
import com.neathorium.thorium.core.records.executor.ExecutionStateData;
import com.neathorium.thorium.core.records.executor.ExecutionStepsData;
import com.neathorium.thorium.core.records.executor.ExecutorFunctionData;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;
import com.neathorium.thorium.java.extensions.namespaces.utilities.ListUtilities;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public interface Executor {
    private static <DependencyType, ReturnType> Data<ExecutionResultData<ReturnType>> executeCore(
        ExecutionStepsData<DependencyType> stepsData,
        ExecutorFunctionData functionData,
        ExecutionStateData stateData
    ) {
        final var nameof = "Executor.executeCore";
        final var exitCondition = functionData.BREAK_CONDITION();
        final var filter = functionData.FILTER_CONDITION();
        final var keyFormatter = functionData.KEY_FORMATTER();
        final var indices = stateData.INDICES();
        final var map = stateData.EXECUTION_MAP();
        final var length = indices.size();
        final var steps = stepsData.STEPS();
        final var dependency = stepsData.DEPENDENCY();
        var stepIndex = 0;
        var index = 0;
        var key = "";
        Data<?> data = ExecutorConstants.NO_STEPS;
        while (exitCondition.test(data, index, indices.size())) {
            stepIndex = indices.get(index);
            data = steps.get(stepIndex).apply(dependency);
            key = keyFormatter.apply(DataFunctions.getNameof(data), stepIndex);
            final var result = MapFunctions.putIfAbsentInvalidOrFalse(map, data, key);

            if (filter.test(data)) {
                indices.remove(index);
            } else {
                ++index;
            }
        }

        final var executionStatus = ExecutionStateDataFactory.getWith(map, indices);
        final var status = functionData.END_CONDITION().test(executionStatus, steps.size(), index, indices.size());
        final var message = (
            functionData.MESSAGE_DATA().get().apply(status) + CoreFormatterConstants.COLON_NEWLINE +
            functionData.END_MESSAGE_HANDLER().apply(executionStatus, key, index, length)
        );
        @SuppressWarnings("unchecked")
        final var object = (ReturnType) DataFunctions.getObject(data);
        final var returnObject = ExecutionResultDataFactory.getWith(executionStatus, object);
        return DataFactoryFunctions.getWith(returnObject, status, nameof, message);
    }

    static <DependencyType, ArrayType, ReturnType, ParameterReturnType> Function<DependencyType, Data<ReturnType>> executeGuardCore(
        ExecutionParametersData<ArrayType, Function<DependencyType, Data<ParameterReturnType>>> execution,
        Function<DependencyType, Data<ReturnType>> executionChain,
        Data<ReturnType> negative,
        List<Function<DependencyType, Data<?>>> steps
    ) {
        final var nameof = "Executor.executeGuardCore";
        return DataExecutionFunctions.ifDependency(nameof, CoreFormatter.getValidCommandMessage(steps, execution.RANGE()), executionChain, negative);
    }

    private static <DependencyType, ReturnType> Data<ReturnType> executeData(
        ExecutionStepsData<DependencyType> stepsData,
        ExecutionParametersData<Function<DependencyType, Data<?>>, Function<DependencyType, Data<ExecutionResultData<ReturnType>>>> execution
    ) {
        final var result = Executor.execute(execution, ExecutionStateDataFactory.getWithDefaultMapAndSpecificLength(stepsData.LENGTH()), stepsData.STEPS()).apply(stepsData.DEPENDENCY());
        return DataFactoryFunctions.replaceObject(result, result.OBJECT().RESULT());
    }

    private static <DependencyType, ReturnType> Function<DependencyType, Data<ReturnType>> executeData(
        ExecutionParametersData<Function<DependencyType, Data<?>>, Function<DependencyType, Data<ExecutionResultData<ReturnType>>>> execution,
        List<Function<DependencyType, Data<?>>> steps
    ) {
        return dependency -> Executor.executeData(ExecutionStepsDataFactory.getWithStepsAndDependency(steps, dependency), execution);
    }

    @SafeVarargs
    private static <DependencyType, ReturnType> Function<DependencyType, Data<ReturnType>> executeData(
        ExecutionParametersData<Function<DependencyType, Data<?>>, Function<DependencyType, Data<ExecutionResultData<ReturnType>>>> execution,
        Function<DependencyType, Data<?>>... steps
    ) {
        final List<Function<DependencyType, Data<?>>> stepsList = NullablePredicates.isNotNull(steps) ? Arrays.asList(steps) : List.of();
        return Executor.executeData(execution, stepsList);
    }

    static <DependencyType, ReturnType> Function<DependencyType, Data<ExecutionResultData<ReturnType>>> execute(
        ExecutionParametersData<Function<DependencyType, Data<?>>, Function<DependencyType, Data<ExecutionResultData<ReturnType>>>> execution,
        ExecutionStateData stateData,
        List<Function<DependencyType, Data<?>>> steps
    ) {
        @SuppressWarnings("unchecked")
        final var negativeReturnObject = (ReturnType) CoreConstants.STOCK_OBJECT;
        final var negative = DataFactoryFunctions.getInvalidWith(ExecutionResultDataFactory.getWithDefaultState(negativeReturnObject), "execute", CoreFormatterConstants.EMPTY);
        return Executor.executeGuardCore(execution, execution.EXECUTOR().apply(execution.FUNCTION_DATA(), stateData, steps), negative, steps);
    }

    @SafeVarargs
    static <DependencyType, ReturnType> Function<DependencyType, Data<ExecutionResultData<ReturnType>>> execute(
        ExecutionParametersData<Function<DependencyType, Data<?>>, Function<DependencyType, Data<ExecutionResultData<ReturnType>>>> execution,
        ExecutionStateData stateData,
        Function<DependencyType, Data<?>>... steps
    ) {
        final var stepsList = ListUtilities.get(steps);
        return Executor.execute(execution, stateData, stepsList);
    }


    static <DependencyType, ReturnType> Function<DependencyType, Data<ExecutionResultData<ReturnType>>> execute(
        ExecutorFunctionData functionData,
        ExecutionStateData stateData,
        List<Function<DependencyType, Data<?>>> steps
    ) {
        return dependency -> Executor.executeCore(ExecutionStepsDataFactory.getWithStepsAndDependency(steps, dependency), functionData, stateData);
    }

    @SafeVarargs
    static <DependencyType, ReturnType> Function<DependencyType, Data<ExecutionResultData<ReturnType>>> execute(
        ExecutorFunctionData functionData,
        ExecutionStateData stateData,
        Function<DependencyType, Data<?>>... steps
    ) {
        final var stepsList = ListUtilities.get(steps);
        return Executor.execute(functionData, stateData, stepsList);
    }

    static <DependencyType, ReturnType> Function<DependencyType, Data<ExecutionResultData<ReturnType>>> execute(
        ExecutorFunctionData functionData,
        List<Function<DependencyType, Data<?>>> steps
    ) {
        return Executor.execute(functionData, ExecutionStateDataFactory.getWithDefaultMapAndSpecificLength(steps.size()), steps);
    }

    @SafeVarargs
    static <DependencyType, ReturnType> Function<DependencyType, Data<ExecutionResultData<ReturnType>>> execute(
        ExecutorFunctionData functionData,
        Function<DependencyType, Data<?>>... steps
    ) {
        final var stepsList = ListUtilities.get(steps);
        return Executor.execute(functionData, stepsList);
    }

    static <DependencyType, ReturnType> Function<DependencyType, Data<ReturnType>> execute(
        ExecutionParametersData<Function<DependencyType, Data<?>>, Function<DependencyType, Data<ExecutionResultData<ReturnType>>>> execution,
        List<Function<DependencyType, Data<?>>> steps
    ) {
        @SuppressWarnings("unchecked")
        final var negativeReturnObject = (ReturnType) CoreConstants.STOCK_OBJECT;
        final var negative = DataFactoryFunctions.getWith(negativeReturnObject, false, CoreFormatterConstants.EMPTY);
        return Executor.executeGuardCore(execution, Executor.executeData(execution, steps), negative, steps);
    }

    @SafeVarargs
    static <DependencyType, ReturnType> Function<DependencyType, Data<ReturnType>> execute(
        ExecutionParametersData<Function<DependencyType, Data<?>>, Function<DependencyType, Data<ExecutionResultData<ReturnType>>>> execution,
        Function<DependencyType, Data<?>>... steps
    ) {
        final var stepsList = ListUtilities.get(steps);
        return Executor.execute(execution, stepsList);
    }
}
