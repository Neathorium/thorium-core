package com.neathorium.thorium.core.executor.namespaces.step;

import com.neathorium.thorium.core.constants.CoreConstants;
import com.neathorium.thorium.core.constants.ExecutorConstants;
import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.data.namespaces.DataFunctions;
import com.neathorium.thorium.core.data.namespaces.factories.DataFactoryFunctions;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.data.interfaces.DataSupplier;
import com.neathorium.thorium.core.namespaces.DataSupplierExecutionFunctions;
import com.neathorium.thorium.core.namespaces.exception.TaskExceptionHandlers;
import com.neathorium.thorium.core.executor.namespaces.ExecutionParametersDataFactory;
import com.neathorium.thorium.core.executor.namespaces.ExecutionResultDataFactory;
import com.neathorium.thorium.core.executor.namespaces.ExecutionStateDataFactory;
import com.neathorium.thorium.core.executor.namespaces.ExecutionStepsDataFactory;
import com.neathorium.thorium.core.executor.namespaces.Executor;
import com.neathorium.thorium.core.executor.namespaces.ExecutorFunctionDataFactory;
import com.neathorium.thorium.core.namespaces.factories.DataSupplierFactory;
import com.neathorium.thorium.core.namespaces.formatter.executor.StepExecutorFormatters;
import com.neathorium.thorium.core.namespaces.task.TaskFunctions;
import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;
import com.neathorium.thorium.core.records.SimpleMessageData;
import com.neathorium.thorium.core.records.executor.ExecutionParametersData;
import com.neathorium.thorium.core.records.executor.ExecutionResultData;
import com.neathorium.thorium.core.records.executor.ExecutionStateData;
import com.neathorium.thorium.core.records.executor.ExecutionStepsData;
import com.neathorium.thorium.core.wait.constants.WaitConstants;
import com.neathorium.thorium.core.wait.records.WaitTimeEntryData;
import com.neathorium.thorium.exceptions.exception.ArgumentNullException;
import com.neathorium.thorium.java.extensions.interfaces.functional.QuadFunction;
import com.neathorium.thorium.java.extensions.interfaces.functional.TriPredicate;
import com.neathorium.thorium.java.extensions.interfaces.functional.boilers.IGetMessage;
import com.neathorium.thorium.java.extensions.namespaces.concurrent.CompletableFutureExtensions;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;
import com.neathorium.thorium.java.extensions.namespaces.utilities.BooleanUtilities;
import com.neathorium.thorium.java.extensions.namespaces.utilities.ListUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public interface StepExecutor {
    private static <ReturnType, ParameterReturnType> DataSupplier<ReturnType> executeGuardCore(
        ExecutionParametersData<Function<Void, Data<?>>, DataSupplier<ParameterReturnType>> execution,
        DataSupplier<ReturnType> executionChain,
        Data<ReturnType> negative,
        int stepLength
    ) {
        final var nameof = "StepExecutor.executeGuardCore";
        return DataSupplierExecutionFunctions.ifDependency(nameof, CoreFormatter.getCommandAmountRangeErrorMessage(stepLength, execution.RANGE()), executionChain, negative);
    }

    static <ReturnType> DataSupplier<ExecutionResultData<ReturnType>> execute(
        ExecutionParametersData<Function<Void, Data<?>>, DataSupplier<ExecutionResultData<ReturnType>>> execution,
        ExecutionStateData stateData,
        List<Function<Void, Data<?>>> steps
    ) {
        @SuppressWarnings("unchecked")
        final var negativeReturnObject = (ReturnType) CoreConstants.STOCK_OBJECT;
        final var negative = DataFactoryFunctions.getInvalidWith(ExecutionResultDataFactory.getWithDefaultState(negativeReturnObject), "execute", CoreFormatterConstants.EMPTY);
        return StepExecutor.executeGuardCore(execution, execution.EXECUTOR().apply(execution.FUNCTION_DATA(), stateData, steps), negative, steps.size());
    }

    @SafeVarargs
    static <ReturnType> DataSupplier<ExecutionResultData<ReturnType>> execute(
        ExecutionParametersData<Function<Void, Data<?>>, DataSupplier<ExecutionResultData<ReturnType>>> execution,
        ExecutionStateData stateData,
        Function<Void, Data<?>>... steps
    ) {
        final var list = ListUtilities.get(steps);
        return StepExecutor.execute(execution, stateData, list);
    }

    private static <ReturnType> Data<ReturnType> executeData(
        ExecutionStepsData<Void> stepsData,
        ExecutionParametersData<Function<Void, Data<?>>, DataSupplier<ExecutionResultData<ReturnType>>> execution
    ) {
        final var result = StepExecutor.execute(execution, ExecutionStateDataFactory.getWithDefaults(), stepsData.STEPS()).apply(stepsData.DEPENDENCY());
        return DataFactoryFunctions.replaceObject(result, result.OBJECT().RESULT());
    }

    private static <ReturnType> DataSupplier<ReturnType> executeData(
        ExecutionParametersData<Function<Void, Data<?>>, DataSupplier<ExecutionResultData<ReturnType>>> execution,
        DataSupplier<?>... steps
    ) {
        final List<Function<Void, Data<?>>> list = new ArrayList<>();
        for (var step : steps) {
            list.add(step::apply);
        }
        return DataSupplierFactory.get((Void dependency) -> StepExecutor.executeData(ExecutionStepsDataFactory.getWithStepsAndDependency(list, dependency), execution));

    }

    static <ReturnType> DataSupplier<ReturnType> execute(ExecutionParametersData<Function<Void, Data<?>>, DataSupplier<ExecutionResultData<ReturnType>>> execution, DataSupplier<?>... steps) {
        @SuppressWarnings("unchecked")
        final var negativeReturnObject = (ReturnType) CoreConstants.STOCK_OBJECT;
        final var negative = DataFactoryFunctions.getInvalidWith(negativeReturnObject, "execute", CoreFormatterConstants.EMPTY);
        return executeGuardCore(execution, executeData(execution, steps), negative, steps.length);
    }

    static <ReturnType> DataSupplier<ReturnType> execute(IGetMessage stepMessage, DataSupplier<?>... steps) {
        return DataSupplierFactory.get(Executor.execute(
            ExecutionParametersDataFactory.getWithDefaultRange(
                ExecutorFunctionDataFactory.getWithExecuteParametersDataAndDefaultExitCondition(stepMessage, ExecutorConstants.DEFAULT_EXECUTION_DATA),
                Executor::execute
            ),
            steps
        ));
    }

    static <ReturnType> DataSupplier<ReturnType> execute(String message, DataSupplier<?>... steps) {
        return DataSupplierFactory.get(Executor.execute(
            ExecutionParametersDataFactory.getWithDefaultRange(
                ExecutorFunctionDataFactory.getWithSpecificMessage(message),
                Executor::execute
            ),
            steps
        ));
    }

    static <ReturnType> DataSupplier<ReturnType> execute(QuadFunction<ExecutionStateData, String, Integer, Integer, String> messageHandler, DataSupplier<?>... steps) {
        return DataSupplierFactory.get(Executor.execute(
            ExecutionParametersDataFactory.getWithTwoCommandsRange(
                ExecutorFunctionDataFactory.getWithDefaultExitConditionAndMessageData(messageHandler),
                Executor::execute
            ),
            steps
        ));
    }

    static <ReturnType> DataSupplier<ReturnType> execute(DataSupplier<?>... steps) {
        return DataSupplierFactory.get(Executor.execute(ExecutionParametersDataFactory.getWithDefaultFunctionDataAndDefaultRange(Executor::execute), steps));
    }

    static <ReturnType> DataSupplier<ReturnType> conditionalSequence(TriPredicate<Data<?>, Integer, Integer> guard, DataSupplier<?> before, DataSupplier<?> after) {
        final DataSupplier<?>[] steps = Arrays.asList(before, after).toArray(new DataSupplier<?>[0]);
        return DataSupplierFactory.get(Executor.execute(
            ExecutionParametersDataFactory.getWithTwoCommandsRange(
                ExecutorFunctionDataFactory.getWithSpecificMessageDataAndBreakCondition(new SimpleMessageData(CoreFormatterConstants.EXECUTION_ENDED), guard),
                Executor::execute
            ),
            steps
        ));
    }

    static <T, U, ReturnType> DataSupplier<ExecutionResultData<ReturnType>> conditionalSequence(DataSupplier<T> before, DataSupplier<U> after, Class<ReturnType> clazz) {
        final DataSupplier<?>[] steps = Arrays.asList(before, after).toArray(new DataSupplier<?>[0]);
        return DataSupplierFactory.get(Executor.execute(
            ExecutionParametersDataFactory.getWithDefaultFunctionDataAndTwoCommandRange(Executor::execute),
            steps
        ));
    }

    static <ReturnType> DataSupplier<ExecutionResultData<ReturnType>> execute(IGetMessage stepMessage, ExecutionStateData stateData, DataSupplier<?>... steps) {
        final var indices = stateData.INDICES();
        final var localStateData = (NullablePredicates.isNotNull(indices) && BooleanUtilities.isFalse(indices.isEmpty())) ? stateData : ExecutionStateDataFactory.getWith(stateData.EXECUTION_MAP(), steps.length);
        return DataSupplierFactory.get(Executor.execute(
            ExecutionParametersDataFactory.getWithDefaultRange(
                ExecutorFunctionDataFactory.getWithExecuteParametersDataAndDefaultExitCondition(stepMessage, ExecutorConstants.DEFAULT_EXECUTION_DATA),
                Executor::execute
            ),
            localStateData,
            steps
        ));
    }

    static <ReturnType> DataSupplier<ExecutionResultData<ReturnType>> execute(String message, ExecutionStateData stateData, DataSupplier<?>... steps) {
        ExecutionStateData localStateData;
        if (NullablePredicates.isNull(stateData)) {
            localStateData = ExecutionStateDataFactory.getWith(null, steps.length);
        } else {
            final var indices = stateData.INDICES();
            localStateData = NullablePredicates.isNotNull(indices) && BooleanUtilities.isFalse(indices.isEmpty()) ? stateData : ExecutionStateDataFactory.getWith(stateData.EXECUTION_MAP(), steps.length);
        }
        return DataSupplierFactory.get(Executor.execute(
            ExecutionParametersDataFactory.getWithDefaultRange(
                ExecutorFunctionDataFactory.getWithSpecificMessage(message),
                Executor::execute
            ),
            localStateData,
            steps
        ));
    }

    static <ReturnType> DataSupplier<ExecutionResultData<ReturnType>> execute(QuadFunction<ExecutionStateData, String, Integer, Integer, String> messageHandler, ExecutionStateData stateData, DataSupplier<?>... steps) {
        final var indices = stateData.INDICES();
        final var localStateData = (NullablePredicates.isNotNull(indices) && BooleanUtilities.isFalse(indices.isEmpty())) ? stateData : ExecutionStateDataFactory.getWith(stateData.EXECUTION_MAP(), steps.length);
        return DataSupplierFactory.get(Executor.execute(
            ExecutionParametersDataFactory.getWithTwoCommandsRange(
                ExecutorFunctionDataFactory.getWithDefaultExitConditionAndMessageData(messageHandler),
                Executor::execute
            ),
            localStateData,
            steps
        ));
    }

    static <ReturnType> DataSupplier<ExecutionResultData<ReturnType>> execute(ExecutionStateData stateData, DataSupplier<?>... steps) {
        final var indices = stateData.INDICES();
        final var localStateData = (NullablePredicates.isNotNull(indices) && BooleanUtilities.isFalse(indices.isEmpty())) ? stateData : ExecutionStateDataFactory.getWith(stateData.EXECUTION_MAP(), steps.length);
        return DataSupplierFactory.get(Executor.execute(ExecutionParametersDataFactory.getWithDefaultFunctionDataAndDefaultRange(Executor::execute), localStateData, steps));
    }


    static <ReturnType> DataSupplier<ExecutionResultData<ReturnType>> conditionalSequence(TriPredicate<Data<?>, Integer, Integer> guard, ExecutionStateData stateData, DataSupplier<?> before, DataSupplier<?> after) {
        final DataSupplier<?>[] steps = Arrays.asList(before, after).toArray(new DataSupplier<?>[0]);
        final var indices = stateData.INDICES();
        final var localStateData = (NullablePredicates.isNotNull(indices) && BooleanUtilities.isFalse(indices.isEmpty())) ? stateData : ExecutionStateDataFactory.getWith(stateData.EXECUTION_MAP(), steps.length);
        return DataSupplierFactory.get(Executor.execute(
            ExecutionParametersDataFactory.getWithTwoCommandsRange(
                ExecutorFunctionDataFactory.getWithSpecificMessageDataAndBreakCondition(new SimpleMessageData(CoreFormatterConstants.EXECUTION_ENDED), guard),
                Executor::execute
            ),
            localStateData,
            steps
        ));
    }

    static <T, U, ReturnType> DataSupplier<ExecutionResultData<ReturnType>> conditionalSequence(ExecutionStateData stateData, DataSupplier<T> before, DataSupplier<U> after, Class<ReturnType> clazz) {
        final DataSupplier<?>[] steps = Arrays.asList(before, after).toArray(new DataSupplier<?>[0]);
        final var indices = stateData.INDICES();
        final var localStateData = (NullablePredicates.isNotNull(indices) && BooleanUtilities.isFalse(indices.isEmpty())) ? stateData : ExecutionStateDataFactory.getWith(stateData.EXECUTION_MAP(), steps.length);
        return DataSupplierFactory.get(Executor.execute(
            ExecutionParametersDataFactory.getWithDefaultFunctionDataAndTwoCommandRange(Executor::execute),
            localStateData,
            steps
        ));
    }

    static <ReturnType> Function<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>> executeState(IGetMessage stepMessage, DataSupplier<?>... steps) {
        return stateData -> StepExecutor.execute(stepMessage, stateData, steps);
    }

    static <ReturnType> Function<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>> executeState(String message, DataSupplier<?>... steps) {
        return stateData -> StepExecutor.execute(message, stateData, steps);
    }

    static <ReturnType> Function<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>> executeState(QuadFunction<ExecutionStateData, String, Integer, Integer, String> messageHandler, DataSupplier<?>... steps) {
        return stateData -> StepExecutor.execute(messageHandler, stateData, steps);
    }

    static <ReturnType> Function<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>> executeState(DataSupplier<?>... steps) {
        return stateData -> StepExecutor.execute(stateData, steps);
    }

    static <ReturnType> Function<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>> conditionalSequenceState(TriPredicate<Data<?>, Integer, Integer> guard, DataSupplier<?> before, DataSupplier<?> after) {
        return stateData -> StepExecutor.conditionalSequence(guard, stateData, before, after);
    }

    static <T, U, ReturnType> Function<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>> conditionalSequenceState(DataSupplier<T> before, DataSupplier<U> after, Class<ReturnType> clazz) {
        return stateData -> StepExecutor.conditionalSequence(stateData, before, after, clazz);
    }

    static Data<Boolean> execute(int duration, DataSupplier<?>... steps) {
        final var nameof = "StepExecutor.execute";
        if (NullablePredicates.isNull(steps)) {
            throw new ArgumentNullException(nameof + ": Steps should contain at least 2 parallel steps, was null" + CoreFormatterConstants.END_LINE);
        }
        final var stepsLength = steps.length;
        if ((stepsLength < 2) || (duration < 300)) {
            throw new ArgumentNullException(nameof + ": Steps should contain at least 2 parallel steps" + CoreFormatterConstants.END_LINE);
        }

        final var entryData = new WaitTimeEntryData(duration, TimeUnit.MILLISECONDS);
        final var startTime = WaitConstants.CLOCK.instant();
        final var tasks = TaskFunctions.getTaskListWithTimeouts(entryData, steps);
        final var all = CompletableFutureExtensions.allOfTerminateOnFailureTimed(duration, TaskFunctions.getTaskArray(tasks));
        final var result = TaskExceptionHandlers.futureHandler(all);
        final var data = StepExecutorFormatters.getExecuteAllParallelTimedMessageData(tasks, all, result, startTime, WaitConstants.CLOCK.instant(), entryData);
        final var status = DataFunctions.getStatus(data);
        final var message = DataFunctions.getMessage(data);
        return DataFactoryFunctions.getBoolean(status, nameof, message, data.EXCEPTION());
    }

    static Data<Boolean> executeEndOnAnyResult(int duration, DataSupplier<?>... steps) {
        final var nameof = "StepExecutor.executeEndOnAnyResult";
        if (NullablePredicates.isNull(steps)) {
            throw new ArgumentNullException(nameof + ": Steps should contain at least 2 parallel steps, was null" + CoreFormatterConstants.END_LINE);
        }
        final var stepsLength = steps.length;
        if ((stepsLength < 2) || (duration < 300)) {
            throw new ArgumentNullException(nameof + ": Steps should contain at least 2 parallel steps" + CoreFormatterConstants.END_LINE);
        }

        final var entryData = new WaitTimeEntryData(duration, TimeUnit.MILLISECONDS);
        final var startTime = WaitConstants.CLOCK.instant();
        final var tasks = TaskFunctions.getTaskListWithTimeouts(entryData, steps);
        final var all = CompletableFutureExtensions.anyOfTerminateOnFailureTimed(duration, TaskFunctions.getTaskArray(tasks));
        final var result = TaskExceptionHandlers.futureHandler(all);
        final var data = StepExecutorFormatters.getExecuteAnyParallelTimedMessageData(tasks, all, result, startTime, WaitConstants.CLOCK.instant(), entryData);
        final var status = data.STATUS();
        final var message = data.MESSAGE().MESSAGE();
        return DataFactoryFunctions.getBoolean(status, nameof, message, data.EXCEPTION());
    }
}
