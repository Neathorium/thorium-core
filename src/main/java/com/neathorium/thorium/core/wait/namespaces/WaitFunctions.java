package com.neathorium.thorium.core.wait.namespaces;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.data.namespaces.DataFunctions;
import com.neathorium.thorium.core.data.namespaces.factories.DataFactoryFunctions;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.data.interfaces.DataSupplier;

import com.neathorium.thorium.core.executor.namespaces.ExecutionStateDataFactory;
import com.neathorium.thorium.core.records.executor.ExecutionResultData;
import com.neathorium.thorium.core.records.executor.ExecutionStateData;
import com.neathorium.thorium.core.wait.constants.WaitDataConstants;
import com.neathorium.thorium.core.wait.constants.WaitExceptionConstants;
import com.neathorium.thorium.core.wait.constants.WaitFormatterConstants;
import com.neathorium.thorium.core.wait.exceptions.WaitTimeoutException;
import com.neathorium.thorium.core.wait.exceptions.WrappedExecutionException;
import com.neathorium.thorium.core.wait.exceptions.WrappedThreadInterruptedException;
import com.neathorium.thorium.core.wait.exceptions.WrappedTimeoutException;
import com.neathorium.thorium.core.wait.interfaces.IWaitTask;
import com.neathorium.thorium.core.wait.namespaces.factories.WaitDataFactory;
import com.neathorium.thorium.core.wait.namespaces.factories.tasks.common.WaitRepeatTaskFactory;
import com.neathorium.thorium.core.wait.namespaces.factories.tasks.common.WaitTaskFactory;
import com.neathorium.thorium.core.wait.namespaces.factories.tasks.common.WaitTaskStateDataFactory;
import com.neathorium.thorium.core.wait.namespaces.formatters.WaitFormatters;
import com.neathorium.thorium.core.wait.namespaces.validators.WaitDataValidators;
import com.neathorium.thorium.core.wait.namespaces.validators.WaitValidators;
import com.neathorium.thorium.core.wait.records.WaitData;
import com.neathorium.thorium.core.wait.records.WaitTimeData;
import com.neathorium.thorium.core.wait.records.WaitTimeEntryData;
import com.neathorium.thorium.core.wait.records.tasks.WaitTask;
import com.neathorium.thorium.core.wait.records.tasks.WaitRepeatTask;
import com.neathorium.thorium.core.wait.records.tasks.common.WaitTaskCommonData;
import com.neathorium.thorium.exceptions.exception.ArgumentNullException;
import com.neathorium.thorium.java.extensions.interfaces.functional.TriFunction;
import com.neathorium.thorium.java.extensions.namespaces.predicates.BasicPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.EqualsPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;
import com.neathorium.thorium.java.extensions.namespaces.utilities.BooleanUtilities;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface WaitFunctions {
    private static Void handleResultVoidTask(
        WaitTaskCommonData<Void, Void, Void> commonData,
        Void dependency,
        Function<Void, Void> resultHandler
    ) {
        final var function = commonData.FUNCTION();
        if (NullablePredicates.isNotNull(function)) {
            function.apply(dependency);
        }

        return null;
    }

    private static <T, U, V, HandledReturnType> HandledReturnType handleResultRegularTask(
        WaitTaskCommonData<T, U, V> commonData,
        T dependency,
        Function<U, HandledReturnType> resultHandler
    ) {
        return resultHandler.apply(commonData.FUNCTION().apply(dependency));
    }

    private static <T, U, V, HandledReturnType> HandledReturnType getResult(
        IWaitTask<T, U, V> task,
        TriFunction<WaitTaskCommonData<T, U, V>, T, Function<U, HandledReturnType>, HandledReturnType> resultGetter,
        Function<U, HandledReturnType> resultHandler
    ) {
        final var commonData = task.COMMON_DATA();
        final var dependency = task.STATE_DATA().get().DEPENDENCY();
        return resultGetter.apply(commonData, dependency, resultHandler);
    }

    private static void runTaskHandleReturnVoidResult(
        String nameof,
        String message,
        WaitTask<Void, Void, Void> task,
        Void result
    ) {
        final var localNameof = StringUtils.isNotBlank(nameof) ? nameof : "WaitFunctions.runTask";
        final var localMessage = StringUtils.isNotBlank(message) ? message : ("Void Task executed successfully" + CoreFormatterConstants.END_LINE);
        final var returnData = DataFactoryFunctions.replaceMethodMessageData(DataFactoryFunctions.getValidWith(result, localNameof, localMessage), task.STATE_DATA().get().DATA().MESSAGE());
        task.STATE_DATA().set(WaitTaskStateDataFactory.replaceData(task.STATE_DATA().get(), returnData));
    }

    private static <T, ReturnType> void runTaskHandleReturnResult(
        String nameof,
        String message,
        WaitTask<T, ReturnType, ReturnType> task,
        ReturnType result
    ) {
        final var localNameof = StringUtils.isNotBlank(nameof) ? nameof : "WaitFunctions.runTask";
        final var localMessage = StringUtils.isNotBlank(message) ? message : ("Task executed successfully" + CoreFormatterConstants.END_LINE);
        final var returnData = DataFactoryFunctions.getValidWith(result, localNameof, localMessage);
        task.STATE_DATA().set(WaitTaskStateDataFactory.replaceData(task.STATE_DATA().get(), returnData));
    }

    private static <ConditionType> void runTaskHandleRepeatReturnData(
        String nameof,
        String message,
        WaitRepeatTask<ConditionType> task,
        Data<ExecutionResultData<ConditionType>> result
    ) {
        final var localNameof = StringUtils.isNotBlank(nameof) ? nameof : "WaitFunctions.runTask";
        final var localMessage = StringUtils.isNotBlank(message) ? message : ("Repeat Task executed successfully" + CoreFormatterConstants.END_LINE);
        final var returnData = DataFactoryFunctions.getValidWith(result, localNameof, localMessage);
        task.STATE_DATA().set(WaitTaskStateDataFactory.replaceDataAndDependency(task.STATE_DATA().get(), returnData, result.OBJECT().STATE_DATA()));
    }

    static void throwIfOverLimit(ScheduledExecutorService scheduler, int limit, int count) {
        if (BasicPredicates.isPositiveNonZero(limit) && BasicPredicates.isBiggerThan(count, limit)) {
            final var nameof = "WaitFunctions.throwIfOverLimit";
            scheduler.shutdown();
            throw new WaitTimeoutException(nameof + ": Exception while running task, count(\"" + count + "\") was bigger than limit(\"" + limit + "\").");
        }
    }

    static <ReturnType> Boolean returnOnFalseThrowIfOverLimit(
        Predicate<ReturnType> exitCondition,
        ScheduledExecutorService scheduler,
        int limit,
        int count,
        ReturnType result
    ) {
        WaitFunctions.throwIfOverLimit(scheduler, limit, count);
        return exitCondition.test(result);
    }

    private static void runVoidTaskCore(WaitTask<Void, Void, Void> task) {
        final var result = WaitFunctions.getResult(task, WaitFunctions::handleResultVoidTask, Function.identity());
        final var condition = WaitFunctions.returnOnFalseThrowIfOverLimit(
            NullablePredicates::isNull,
            task.SCHEDULER(),
            task.STATE_DATA().get().LIMIT(),
            task.STATE_DATA().get().COUNTER().incrementAndGet(),
            result
        );
        if (BooleanUtilities.isFalse(condition)) {
            return;
        }

        WaitFunctions.runTaskHandleReturnVoidResult("runTask", "Void task executed successfully" + CoreFormatterConstants.END_LINE, task, result);
        task.SCHEDULER().shutdown();
    }

    private static <T, ReturnType> void runTaskCore(WaitTask<T, ReturnType, ReturnType> task) {
        final var result = WaitFunctions.getResult(task, WaitFunctions::handleResultRegularTask, Function.identity());
        final var condition = WaitFunctions.returnOnFalseThrowIfOverLimit(
            task.COMMON_DATA().EXIT_CONDITION(),
            task.SCHEDULER(),
            task.STATE_DATA().get().LIMIT(),
            task.STATE_DATA().get().COUNTER().incrementAndGet(),
            result
        );
        if (BooleanUtilities.isFalse(condition)) {
            return;
        }

        WaitFunctions.runTaskHandleReturnResult("runTask", "Task executed successfully" + CoreFormatterConstants.END_LINE, task, result);
        task.SCHEDULER().shutdown();
    }

    private static <ReturnType> void runTaskCore(WaitRepeatTask<ReturnType> task) {
        final var result = WaitFunctions.getResult(task, WaitFunctions::handleResultRegularTask, DataSupplier::get);
        final var condition = WaitFunctions.returnOnFalseThrowIfOverLimit(
            task.COMMON_DATA().EXIT_CONDITION(),
            task.SCHEDULER(),
            task.STATE_DATA().get().LIMIT(),
            task.STATE_DATA().get().COUNTER().incrementAndGet(),
            result
        );
        if (BooleanUtilities.isFalse(condition)) {
            return;
        }

        WaitFunctions.runTaskHandleRepeatReturnData("runTask",  "Repeat task executed successfully" + CoreFormatterConstants.END_LINE, task, result);
        task.SCHEDULER().shutdown();
    }

    private static Runnable runVoidTask(WaitTask<Void, Void, Void> task) {
        return () -> WaitFunctions.runVoidTaskCore(task);
    }

    private static <T, ReturnType> Runnable runTask(WaitTask<T, ReturnType, ReturnType> task) {
        return () -> WaitFunctions.runTaskCore(task);
    }

    private static <ReturnType> Runnable runTask(WaitRepeatTask<ReturnType> task) {
        return () -> WaitFunctions.runTaskCore(task);
    }

    private static <T, U, ReturnType> Data<ReturnType> commonCore(Runnable runnable, IWaitTask<T, U, ReturnType> task, String nameof) {
        final var handledData = WaitExceptionHandlers.futureInformationHandler(task, runnable);
        final var updatedTaskData = task.STATE_DATA().get().DATA();
        return DataFactoryFunctions.replaceObjectStatusAndName(handledData, DataFunctions.getObject(updatedTaskData), DataFunctions.getStatus(updatedTaskData), nameof);
    }

    private static Data<Void> sleepCore(Runnable runnable, WaitTask<Void, Void, Void> task, WaitTimeEntryData data) {
        return WaitFunctions.commonCore(
            () -> {
                try {
                    task.SCHEDULER().schedule(runnable, data.LENGTH(), data.TIME_UNIT()).get();
                } catch (InterruptedException ex) {
                    throw new WrappedThreadInterruptedException("Thread was interrupted, exception is wrapped for code" + CoreFormatterConstants.END_LINE, ex);
                } catch (ExecutionException ex) {
                    throw new WrappedExecutionException("Exception occurred during Execution, exception is wrapped for code" + CoreFormatterConstants.END_LINE, ex);
                }
            },
            task,
            "sleep"
        );
    }

    private static <T, U, ReturnType> Data<ReturnType> untilTimeoutCore(Runnable runnable, IWaitTask<T, U, ReturnType> task, WaitTimeData data) {
        return WaitFunctions.commonCore(
            () -> {
                try {
                    task.SCHEDULER().scheduleWithFixedDelay(
                        runnable,
                        0,
                        data.ENTRY_PAIR_DATA().INTERVAL().LENGTH(),
                        data.ENTRY_PAIR_DATA().INTERVAL().TIME_UNIT()
                    ).get(
                        data.ENTRY_PAIR_DATA().DURATION().LENGTH(),
                        data.ENTRY_PAIR_DATA().DURATION().TIME_UNIT()
                    );
                } catch (InterruptedException ex) {
                    throw new WrappedThreadInterruptedException("Thread was interrupted, exception is wrapped for code" + CoreFormatterConstants.END_LINE, ex);
                } catch (ExecutionException ex) {
                    throw new WrappedExecutionException("Exception occurred during Execution, exception is wrapped for code" + CoreFormatterConstants.END_LINE, ex);
                } catch (TimeoutException ex) {
                    throw new WrappedTimeoutException("Timeout exception occurred, exception is wrapped for code" + CoreFormatterConstants.END_LINE, ex);
                }
            },
            task,
            "untilTimeout"
        );
    }

    private static Data<Void> sleep(
        Function<WaitTask<Void, Void, Void>, Runnable> runner,
        WaitTask<Void, Void, Void> task,
        WaitTimeEntryData data
    ) {
        return WaitFunctions.sleepCore(runner.apply(task), task, data);
    }

    private static <T, U, ReturnType, TaskType extends WaitTask<T, U, ReturnType>> Data<ReturnType> untilTimeout(
        Function<TaskType, Runnable> runner,
        TaskType task,
        WaitTimeData timeData
    ) {
        return WaitFunctions.untilTimeoutCore(runner.apply(task), task, timeData);
    }

    private static <ConditionType> Data<Data<ExecutionResultData<ConditionType>>> repeatUntilTimeout(
        Function<WaitRepeatTask<ConditionType>, Runnable> runner,
        WaitRepeatTask<ConditionType> task,
        WaitTimeData timeData
    ) {
        return WaitFunctions.untilTimeoutCore(runner.apply(task), task, timeData);
    }

    private static Supplier<Data<Void>> sleepSupplier(
        Function<WaitTask<Void, Void, Void>, Runnable> runner,
        WaitTask<Void, Void, Void> task,
        WaitTimeEntryData data
    ) {
        return () -> WaitFunctions.sleep(runner, task, data);
    }

    private static <T, U, ReturnType, TaskType extends WaitTask<T, U, ReturnType>> Supplier<Data<ReturnType>> untilTimeoutSupplier(
        Function<TaskType, Runnable> runner,
        TaskType task,
        WaitTimeData timeData
    ) {
        return () -> WaitFunctions.untilTimeout(runner, task, timeData);
    }

    private static <ReturnType> Supplier<Data<Data<ExecutionResultData<ReturnType>>>> repeatUntilTimeoutSupplier(
        Function<WaitRepeatTask<ReturnType>, Runnable> runner,
        WaitRepeatTask<ReturnType> task,
        WaitTimeData timeData
    ) {
        return () -> WaitFunctions.repeatUntilTimeout(runner, task, timeData);
    }

    private static <T, U, ReturnType> ReturnType coreCore(Supplier<Data<ReturnType>> supplier, WaitData<T, U, ReturnType> waitData) {
        final var timeData = waitData.TIME_DATA();
        final var clock = timeData.CLOCK();
        final var start = clock.instant();
        final var result = supplier.get();
        final var end = clock.instant();
        final var conditionMessage = NullablePredicates.isNotNull(result.OBJECT()) ? DataFunctions.getMessageFromData(result.OBJECT()) : waitData.CONDITION_MESSAGE();
        final var status = EqualsPredicates.isEqual(result.MESSAGE().MESSAGE(), WaitFormatterConstants.TASK_SUCCESSFULLY_ENDED);
        final var startFragment = (status ? CoreFormatterConstants.WAITING_SUCCESSFUL : WaitExceptionConstants.WAITING_FAILED);
        final var message = WaitFormatters.getExecutionTimeMessage(startFragment, conditionMessage, timeData, start, end);
        if (BooleanUtilities.isTrue(result.STATUS())) {
            return result.OBJECT();
        }

        throw new WaitTimeoutException(message + CoreFormatterConstants.END_LINE, result.EXCEPTION());
    }

    private static void sleep(WaitData<Void, Void, Void> data, Data<Void> initialValue) {
        final var errorMessage = WaitDataValidators.isValidSleepData(data);
        if (isNotBlank(errorMessage)) {
            throw new ArgumentNullException(errorMessage);
        }

        final var task = WaitTaskFactory.getWithDefaultScheduler(data.TASK_DATA(), new AtomicReference<>(WaitTaskStateDataFactory.getWithNoRepeating(initialValue, null)));
        final var sleepSupplier = WaitFunctions.sleepSupplier(WaitFunctions::runVoidTask, task, data.TIME_DATA().ENTRY_PAIR_DATA().DURATION());
        WaitFunctions.coreCore(sleepSupplier, data);
    }

    private static <DependencyType, ReturnType> ReturnType core(DependencyType dependency, WaitData<DependencyType, ReturnType, ReturnType> data, Data<ReturnType> initialValue) {
        final var errorMessage = WaitValidators.isValidWaitParameters(dependency, data);
        if (StringUtils.isNotBlank(errorMessage)) {
            throw new ArgumentNullException(errorMessage);
        }

        final var task = WaitTaskFactory.getWithDefaultScheduler(data.TASK_DATA(), new AtomicReference<>(WaitTaskStateDataFactory.getWithNoRepeating(initialValue, dependency)));
        final var supplier = WaitFunctions.untilTimeoutSupplier(WaitFunctions::runTask, task, data.TIME_DATA());
        return WaitFunctions.coreCore(supplier, data);
    }

    private static <ReturnType> Data<ExecutionResultData<ReturnType>> repeat(
        ExecutionStateData dependency,
        WaitData<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>, Data<ExecutionResultData<ReturnType>>> data,
        Data<Data<ExecutionResultData<ReturnType>>> initialValue,
        int limit
    ) {
        final var errorMessage = WaitValidators.isValidWaitParameters(dependency, data);
        if (StringUtils.isNotBlank(errorMessage)) {
            throw new ArgumentNullException(errorMessage);
        }

        final var task = WaitRepeatTaskFactory.getWithDefaultScheduler(data.TASK_DATA(), new AtomicReference<>(WaitTaskStateDataFactory.getWithDefaultCounter(initialValue, dependency, limit)));
        final var supplier = WaitFunctions.repeatUntilTimeoutSupplier(WaitFunctions::runTask, task, data.TIME_DATA());
        return WaitFunctions.coreCore(supplier, data);
    }

    static void sleep(int duration) {
        WaitFunctions.sleep(WaitDataFactory.getWithSleepDuration(null, null, duration), WaitDataConstants.SLEEP_START_DATA);
    }

    static <DependencyType, ReturnType> Function<DependencyType, ReturnType> core(WaitData<DependencyType, ReturnType, ReturnType> waitData) {
        return dependency -> WaitFunctions.core(dependency, waitData, DataFactoryFunctions.getInvalidWith(null, "core", "core"));
    }

    static <ReturnType> Function<ExecutionStateData, Data<ExecutionResultData<ReturnType>>> repeat(
        WaitData<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>, Data<ExecutionResultData<ReturnType>>> waitData,
        int limit
    ) {
        return dependency -> WaitFunctions.repeat(dependency, waitData, DataFactoryFunctions.getInvalidWith(null, "repeat", "repeat"), limit);
    }

    static <ReturnType> Function<ExecutionStateData, Data<ExecutionResultData<ReturnType>>> repeat(
        WaitData<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>, Data<ExecutionResultData<ReturnType>>> waitData
    ) {
        return WaitFunctions.repeat(waitData, -1);
    }

    static <ReturnType> Data<ExecutionResultData<ReturnType>> repeatWithDefaultState(
        WaitData<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>, Data<ExecutionResultData<ReturnType>>> waitData,
        int limit
    ) {
        return WaitFunctions.repeat(waitData, limit).apply(ExecutionStateDataFactory.getWithDefaults());
    }

    static <ReturnType> Data<ExecutionResultData<ReturnType>> repeatWithDefaultState(
        WaitData<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>, Data<ExecutionResultData<ReturnType>>> waitData
    ) {
        return WaitFunctions.repeat(waitData).apply(ExecutionStateDataFactory.getWithDefaults());
    }
}
