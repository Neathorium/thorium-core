package com.neathorium.thorium.core.wait.namespaces;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.data.namespaces.DataFunctions;
import com.neathorium.thorium.core.data.namespaces.factories.DataFactoryFunctions;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.data.interfaces.DataSupplier;

import com.neathorium.thorium.core.namespaces.executor.ExecutionStateDataFactory;
import com.neathorium.thorium.core.records.executor.ExecutionResultData;
import com.neathorium.thorium.core.records.executor.ExecutionStateData;
import com.neathorium.thorium.core.wait.constants.WaitConstants;
import com.neathorium.thorium.core.wait.constants.WaitDataConstants;
import com.neathorium.thorium.core.wait.constants.WaitFormatterConstants;
import com.neathorium.thorium.core.wait.exceptions.WaitTimeoutException;
import com.neathorium.thorium.core.wait.exceptions.WrappedExecutionException;
import com.neathorium.thorium.core.wait.exceptions.WrappedThreadInterruptedException;
import com.neathorium.thorium.core.wait.exceptions.WrappedTimeoutException;
import com.neathorium.thorium.core.wait.namespaces.factories.WaitDataFactory;
import com.neathorium.thorium.core.wait.namespaces.factories.tasks.common.WaitRepeatTaskFactory;
import com.neathorium.thorium.core.wait.namespaces.factories.tasks.common.WaitTaskFactory;
import com.neathorium.thorium.core.wait.namespaces.factories.tasks.common.WaitTaskStateDataFactory;
import com.neathorium.thorium.core.wait.namespaces.formatters.WaitFormatters;
import com.neathorium.thorium.core.wait.namespaces.validators.WaitDataValidators;
import com.neathorium.thorium.core.wait.namespaces.validators.WaitValidators;
import com.neathorium.thorium.core.wait.records.WaitData;
import com.neathorium.thorium.core.wait.records.WaitTimeData;
import com.neathorium.thorium.core.wait.records.tasks.WaitTask;
import com.neathorium.thorium.core.wait.records.tasks.WaitRepeatTask;
import com.neathorium.thorium.exceptions.exception.ArgumentNullException;
import com.neathorium.thorium.java.extensions.namespaces.predicates.BasicPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.EqualsPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;
import com.neathorium.thorium.java.extensions.namespaces.utilities.BooleanUtilities;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface WaitFunctions {
    private static void runVoidTaskCore(WaitTask<Void, Void, Void> task) {
        final var function = task.COMMON_DATA.function;
        if (NullablePredicates.isNotNull(function)) {
            function.apply(null);
        }

        final var limit = task.STATE_DATA.limit;
        final var count = task.STATE_DATA.counter.incrementAndGet();
        if (BasicPredicates.isPositiveNonZero(limit) && BasicPredicates.isBiggerThan(count, limit)) {
            task.SCHEDULER.shutdown();
            throw new WaitTimeoutException("Exception while running void task, count(\"" + count + "\") was bigger than limit(\"" + limit + "\").");
        }

        final var returnData = DataFactoryFunctions.replaceMessage(WaitConstants.VOID_TASK_RAN_SUCCESSFULLY, task.STATE_DATA.data.MESSAGE().NAMEOF(), task.STATE_DATA.data.MESSAGE().MESSAGE());
        task.STATE_DATA = WaitTaskStateDataFactory.replaceData(task.STATE_DATA, returnData);
        task.SCHEDULER.shutdown();
    }

    private static <T, ReturnType> void runTaskCore(WaitTask<T, ReturnType, ReturnType> task) {
        final var commonData = task.COMMON_DATA;
        final var result = commonData.function.apply(task.STATE_DATA.dependency);
        final var limit = task.STATE_DATA.limit;
        final var count = task.STATE_DATA.counter.incrementAndGet();
        if (BasicPredicates.isPositiveNonZero(limit) && BasicPredicates.isBiggerThan(count, limit)) {
            task.SCHEDULER.shutdown();
            throw new WaitTimeoutException("Exception while running task, count(\"" + count + "\") was bigger than limit(\"" + limit + "\").");
        }

        if (BooleanUtilities.isFalse(commonData.exitCondition.test(result))) {
            return;
        }

        final var returnData = DataFactoryFunctions.getValidWith(result, "runTask", "Task executed successfully" + CoreFormatterConstants.END_LINE);
        task.STATE_DATA = WaitTaskStateDataFactory.replaceData(task.STATE_DATA, returnData);
        task.SCHEDULER.shutdown();
    }

    private static <ReturnType> void runTaskCore(WaitRepeatTask<ReturnType> task) {
        final var commonData = task.COMMON_DATA;
        final var result = commonData.function.apply(task.STATE_DATA.dependency).get();
        final var limit = task.STATE_DATA.limit;
        final var count = task.STATE_DATA.counter.incrementAndGet();
        if (BasicPredicates.isPositiveNonZero(limit) && BasicPredicates.isBiggerThan(count, limit)) {
            task.SCHEDULER.shutdown();
            throw new WaitTimeoutException("Exception while running task, count(\"" + count + "\") was bigger than limit(\"" + limit + "\").");
        }

        if (BooleanUtilities.isFalse(commonData.exitCondition.test(result))) {
            return;
        }

        final var returnData = DataFactoryFunctions.getValidWith(result, "runTask", "Repeat task executed successfully" + CoreFormatterConstants.END_LINE);
        task.STATE_DATA = WaitTaskStateDataFactory.replaceDataAndDependency(task.STATE_DATA, returnData, result.OBJECT().stateData);
        task.SCHEDULER.shutdown();
    }

    private static Runnable runVoidTask(WaitTask<Void, Void, Void> task) {
        return () -> runVoidTaskCore(task);
    }

    private static <T, ReturnType> Runnable runTask(WaitTask<T, ReturnType, ReturnType> task) {
        return () -> runTaskCore(task);
    }

    private static <ReturnType> Runnable runTask(WaitRepeatTask<ReturnType> task) {
        return () -> runTaskCore(task);
    }

    private static <T, U, ReturnType> Data<ReturnType> commonCore(Runnable runnable, WaitTask<T, U, ReturnType> task, String nameof) {
        return DataFactoryFunctions.replaceObjectStatusAndName(WaitExceptionHandlers.futureInformationHandler(task, runnable), task.STATE_DATA.data.OBJECT(), task.STATE_DATA.data.STATUS(), nameof);
    }

    private static Data<Void> sleepCore(Runnable runnable, WaitTask<Void, Void, Void> task, Duration duration) {
        return commonCore(
            () -> {
                try {
                    task.SCHEDULER.schedule(runnable, duration.toMillis(), TimeUnit.MILLISECONDS).get();
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

    private static <T, U, ReturnType> Data<ReturnType> untilTimeoutCore(Runnable runnable, WaitTask<T, U, ReturnType> task, WaitTimeData data) {
        return commonCore(
            () -> {
                try {
                    task.SCHEDULER.scheduleWithFixedDelay(runnable, 0, data.INTERVAL().toMillis(), TimeUnit.MILLISECONDS).get(data.DURATION().toMillis(), TimeUnit.MILLISECONDS);
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

    private static Data<Void> sleep(Function<WaitTask<Void, Void, Void>, Runnable> runner, WaitTask<Void, Void, Void> task, Duration duration) {
        return sleepCore(runner.apply(task), task, duration);
    }

    private static <T, U, ReturnType> Data<ReturnType> untilTimeout(Function<WaitTask<T, U, ReturnType>, Runnable> runner, WaitTask<T, U, ReturnType> task, WaitTimeData timeData) {
        return untilTimeoutCore(runner.apply(task), task, timeData);
    }

    private static <ReturnType> Data<Data<ExecutionResultData<ReturnType>>> repeatUntilTimeout(Function<WaitRepeatTask<ReturnType>, Runnable> runner, WaitRepeatTask<ReturnType> task, WaitTimeData timeData) {
        return untilTimeoutCore(runner.apply(task), task, timeData);
    }

    private static Supplier<Data<Void>> sleepSupplier(Function<WaitTask<Void, Void, Void>, Runnable> runner, WaitTask<Void, Void, Void> task, Duration duration) {
        return () -> sleep(runner, task, duration);
    }

    private static <T, U, ReturnType> Supplier<Data<ReturnType>> untilTimeoutSupplier(Function<WaitTask<T, U, ReturnType>, Runnable> runner, WaitTask<T, U, ReturnType> task, WaitTimeData timeData) {
        return () -> untilTimeout(runner, task, timeData);
    }

    private static <ReturnType> Supplier<Data<Data<ExecutionResultData<ReturnType>>>> repeatUntilTimeoutSupplier(
        Function<WaitRepeatTask<ReturnType>, Runnable> runner,
        WaitRepeatTask<ReturnType> task,
        WaitTimeData timeData
    ) {
        return () -> repeatUntilTimeout(runner, task, timeData);
    }

    private static <T, U, ReturnType> ReturnType coreCore(Supplier<Data<ReturnType>> supplier, WaitData<T, U, ReturnType> waitData) {
        final var clock = waitData.timeData.CLOCK();
        final var start = clock.instant();
        final var result = supplier.get();
        final var end = clock.instant();
        final var conditionMessage = NullablePredicates.isNotNull(result.OBJECT()) ? DataFunctions.getMessageFromData(result.OBJECT()) : waitData.conditionMessage;
        final var message = WaitFormatters.getExecutionTimeMessage(EqualsPredicates.isEqual(result.MESSAGE().MESSAGE(), WaitFormatterConstants.TASK_SUCCESSFULLY_ENDED), conditionMessage, waitData.timeData, start, end);
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

        final var task = WaitTaskFactory.getWithDefaultScheduler(data.taskData, WaitTaskStateDataFactory.getWithNoRepeating(initialValue, null));
        coreCore(sleepSupplier(WaitFunctions::runVoidTask, task, data.timeData.DURATION()), data);
    }

    private static <DependencyType, ReturnType> ReturnType core(DependencyType dependency, WaitData<DependencyType, ReturnType, ReturnType> data, Data<ReturnType> initialValue) {
        final var errorMessage = WaitValidators.isValidWaitParameters(dependency, data);
        if (isNotBlank(errorMessage)) {
            throw new ArgumentNullException(errorMessage);
        }

        final var task = WaitTaskFactory.getWithDefaultScheduler(data.taskData, WaitTaskStateDataFactory.getWithNoRepeating(initialValue, dependency));
        return coreCore(untilTimeoutSupplier(WaitFunctions::runTask, task, data.timeData), data);
    }

    private static <DependencyType, ReturnType> ReturnType voidCore(DependencyType dependency, WaitData<DependencyType, ReturnType, ReturnType> data, Data<ReturnType> initialValue) {
        final var errorMessage = WaitValidators.isValidWaitParameters(dependency, data);
        if (isNotBlank(errorMessage)) {
            throw new ArgumentNullException(errorMessage);
        }

        final var task = WaitTaskFactory.getWithDefaultScheduler(data.taskData, WaitTaskStateDataFactory.getWithNoRepeating(initialValue, dependency));
        return coreCore(untilTimeoutSupplier(WaitFunctions::runTask, task, data.timeData), data);
    }

    private static <ReturnType> Data<ExecutionResultData<ReturnType>> repeat(
        ExecutionStateData dependency,
        WaitData<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>, Data<ExecutionResultData<ReturnType>>> data,
        Data<Data<ExecutionResultData<ReturnType>>> initialValue,
        int limit
    ) {
        final var errorMessage = WaitValidators.isValidWaitParameters(dependency, data);
        if (isNotBlank(errorMessage)) {
            throw new ArgumentNullException(errorMessage);
        }

        final var task = WaitRepeatTaskFactory.getWithDefaultScheduler(data.taskData, WaitTaskStateDataFactory.getWithDefaultCounter(initialValue, dependency, limit));
        return coreCore(repeatUntilTimeoutSupplier(WaitFunctions::runTask, task, data.timeData), data);
    }

    static void sleep(int duration) {
        sleep(WaitDataFactory.getWithSleepDuration(null, null, duration), WaitDataConstants.SLEEP_START_DATA);
    }

    static <DependencyType, ReturnType> Function<DependencyType, ReturnType> voidCore(WaitData<DependencyType, ReturnType, ReturnType> waitData) {
        return dependency -> core(dependency, waitData, DataFactoryFunctions.getInvalidWith(null, "core", "core"));
    }

    static <DependencyType, ReturnType> Function<DependencyType, ReturnType> core(WaitData<DependencyType, ReturnType, ReturnType> waitData) {
        return dependency -> core(dependency, waitData, DataFactoryFunctions.getInvalidWith(null, "core", "core"));
    }

    static <ReturnType> Function<ExecutionStateData, Data<ExecutionResultData<ReturnType>>> repeat(
        WaitData<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>, Data<ExecutionResultData<ReturnType>>> waitData,
        int limit
    ) {
        return dependency -> repeat(dependency, waitData, DataFactoryFunctions.getInvalidWith(null, "repeat", "repeat"), limit);
    }

    static <ReturnType> Function<ExecutionStateData, Data<ExecutionResultData<ReturnType>>> repeat(
        WaitData<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>, Data<ExecutionResultData<ReturnType>>> waitData
    ) {
        return repeat(waitData, -1);
    }

    static Function<?, Void> sleepFunction(int timeout) {
        return any -> {
            sleep(timeout);
            return null;
        };
    }

    static <ReturnType> Data<ExecutionResultData<ReturnType>> repeatWithDefaultState(
        WaitData<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>, Data<ExecutionResultData<ReturnType>>> waitData,
        int limit
    ) {
        return repeat(waitData, limit).apply(ExecutionStateDataFactory.getWithDefaults());
    }

    static <ReturnType> Data<ExecutionResultData<ReturnType>> repeatWithDefaultState(
        WaitData<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>, Data<ExecutionResultData<ReturnType>>> waitData
    ) {
        return repeat(waitData).apply(ExecutionStateDataFactory.getWithDefaults());
    }
}
