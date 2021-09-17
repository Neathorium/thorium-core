package com.neathorium.thorium.core.namespaces.wait;

import com.neathorium.thorium.core.constants.CoreDataConstants;
import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.constants.wait.WaitDataConstants;
import com.neathorium.thorium.core.constants.wait.WaitFormatterConstants;
import com.neathorium.thorium.core.exceptions.ArgumentNullException;
import com.neathorium.thorium.core.exceptions.WaitTimeoutException;
import com.neathorium.thorium.core.exceptions.WrappedExecutionException;
import com.neathorium.thorium.core.exceptions.WrappedThreadInterruptedException;
import com.neathorium.thorium.core.exceptions.WrappedTimeoutException;
import com.neathorium.thorium.core.extensions.interfaces.functional.boilers.DataSupplier;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;
import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.extensions.namespaces.predicates.BasicPredicates;
import com.neathorium.thorium.core.namespaces.factories.wait.WaitDataFactory;
import com.neathorium.thorium.core.namespaces.DataFactoryFunctions;
import com.neathorium.thorium.core.namespaces.executor.ExecutionStateDataFactory;
import com.neathorium.thorium.core.namespaces.factories.wait.tasks.common.WaitRepeatTaskFactory;
import com.neathorium.thorium.core.namespaces.factories.wait.tasks.common.WaitTaskFactory;
import com.neathorium.thorium.core.namespaces.factories.wait.tasks.common.WaitTaskStateDataFactory;
import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;
import com.neathorium.thorium.core.namespaces.validators.WaitValidators;
import com.neathorium.thorium.core.namespaces.validators.wait.WaitDataValidators;
import com.neathorium.thorium.core.records.Data;
import com.neathorium.thorium.core.records.executor.ExecutionResultData;
import com.neathorium.thorium.core.records.executor.ExecutionStateData;
import com.neathorium.thorium.core.records.wait.tasks.repeat.WaitRepeatTask;
import com.neathorium.thorium.core.records.wait.tasks.regular.WaitTask;
import com.neathorium.thorium.core.records.wait.WaitData;
import com.neathorium.thorium.core.records.wait.WaitTimeData;
import com.neathorium.thorium.core.namespaces.DataFunctions;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface Wait {
    private static void runVoidTaskCore(WaitTask<Void, Void, Void> task) {
        final var function = task.commonData.function;
        if (NullableFunctions.isNotNull(function)) {
            function.apply(null);
        }

        final var limit = task.stateData.limit;
        final var count = task.stateData.counter.incrementAndGet();
        if (BasicPredicates.isPositiveNonZero(limit) && BasicPredicates.isBiggerThan(count, limit)) {
            task.scheduler.shutdown();
            throw new WaitTimeoutException("Exception while running void task, count(\"" + count + "\") was bigger than limit(\"" + limit + "\").");
        }

        final var returnData = DataFactoryFunctions.replaceMessage(CoreDataConstants.VOID_TASK_RAN_SUCCESSFULLY, task.stateData.data.message.nameof, task.stateData.data.message.message);
        task.stateData = WaitTaskStateDataFactory.replaceData(task.stateData, returnData);
        task.scheduler.shutdown();
    }

    private static <T, ReturnType> void runTaskCore(WaitTask<T, ReturnType, ReturnType> task) {
        final var commonData = task.commonData;
        final var result = commonData.function.apply(task.stateData.dependency);
        final var limit = task.stateData.limit;
        final var count = task.stateData.counter.incrementAndGet();
        if (BasicPredicates.isPositiveNonZero(limit) && BasicPredicates.isBiggerThan(count, limit)) {
            task.scheduler.shutdown();
            throw new WaitTimeoutException("Exception while running task, count(\"" + count + "\") was bigger than limit(\"" + limit + "\").");
        }

        if (CoreUtilities.isFalse(commonData.exitCondition.test(result))) {
            return;
        }

        final var returnData = DataFactoryFunctions.getValidWith(result, "runTask", "Task executed successfully" + CoreFormatterConstants.END_LINE);
        task.stateData = WaitTaskStateDataFactory.replaceData(task.stateData, returnData);
        task.scheduler.shutdown();
    }

    private static <ReturnType> void runTaskCore(WaitRepeatTask<ReturnType> task) {
        final var commonData = task.commonData;
        final var result = commonData.function.apply(task.stateData.dependency).get();
        final var limit = task.stateData.limit;
        final var count = task.stateData.counter.incrementAndGet();
        if (BasicPredicates.isPositiveNonZero(limit) && BasicPredicates.isBiggerThan(count, limit)) {
            task.scheduler.shutdown();
            throw new WaitTimeoutException("Exception while running task, count(\"" + count + "\") was bigger than limit(\"" + limit + "\").");
        }

        if (CoreUtilities.isFalse(commonData.exitCondition.test(result))) {
            return;
        }

        final var returnData = DataFactoryFunctions.getValidWith(result, "runTask", "Repeat task executed successfully" + CoreFormatterConstants.END_LINE);
        task.stateData = WaitTaskStateDataFactory.replaceDataAndDependency(task.stateData, returnData, result.object.stateData);
        task.scheduler.shutdown();
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
        final var result = WaitExceptionHandlers.futureInformationHandler(task, runnable);

        final var formatter = result.object;
        final var exception = result.exception;
        final var message =  NullableFunctions.isNotNull(formatter) ? formatter.apply(exception.getMessage()) : WaitFormatterConstants.TASK_SUCCESSFULLY_ENDED;
        return DataFactoryFunctions.getWith(task.stateData.data.object, task.stateData.data.status, nameof, message, exception);
    }

    private static Data<Void> sleepCore(Runnable runnable, WaitTask<Void, Void, Void> task, Duration duration) {
        return commonCore(
            () -> {
                try {
                    task.scheduler.schedule(runnable, duration.toMillis(), TimeUnit.MILLISECONDS).get();
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
                    task.scheduler.scheduleWithFixedDelay(runnable, 0, data.interval.toMillis(), TimeUnit.MILLISECONDS).get(data.duration.toMillis(), TimeUnit.MILLISECONDS);
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
        final var start = waitData.timeData.clock.instant();
        final var result = supplier.get();
        final var end = waitData.timeData.clock.instant();
        final var conditionMessage = NullableFunctions.isNotNull(result.object) ? DataFunctions.getMessageFromData(result.object) : waitData.conditionMessage;
        final var message = CoreFormatter.getExecutionTimeMessage(CoreUtilities.isEqual(result.message.message, WaitFormatterConstants.TASK_SUCCESSFULLY_ENDED), conditionMessage, waitData.timeData, start, end);
        if (CoreUtilities.isTrue(result.status)) {
            return result.object;
        }

        throw new WaitTimeoutException(message + CoreFormatterConstants.END_LINE, result.exception);
    }

    private static void sleep(WaitData<Void, Void, Void> data, Data<Void> initialValue) {
        final var errorMessage = WaitDataValidators.isValidSleepData(data);
        if (isNotBlank(errorMessage)) {
            throw new ArgumentNullException(errorMessage);
        }

        final var task = WaitTaskFactory.getWithDefaultScheduler(data.taskData, WaitTaskStateDataFactory.getWithNoRepeating(initialValue, null));
        coreCore(sleepSupplier(Wait::runVoidTask, task, data.timeData.duration), data);
    }

    private static <DependencyType, ReturnType> ReturnType core(DependencyType dependency, WaitData<DependencyType, ReturnType, ReturnType> data, Data<ReturnType> initialValue) {
        final var errorMessage = WaitValidators.isValidWaitParameters(dependency, data);
        if (isNotBlank(errorMessage)) {
            throw new ArgumentNullException(errorMessage);
        }

        final var task = WaitTaskFactory.getWithDefaultScheduler(data.taskData, WaitTaskStateDataFactory.getWithNoRepeating(initialValue, dependency));
        return coreCore(untilTimeoutSupplier(Wait::runTask, task, data.timeData), data);
    }

    private static <DependencyType, ReturnType> ReturnType voidCore(DependencyType dependency, WaitData<DependencyType, ReturnType, ReturnType> data, Data<ReturnType> initialValue) {
        final var errorMessage = WaitValidators.isValidWaitParameters(dependency, data);
        if (isNotBlank(errorMessage)) {
            throw new ArgumentNullException(errorMessage);
        }

        final var task = WaitTaskFactory.getWithDefaultScheduler(data.taskData, WaitTaskStateDataFactory.getWithNoRepeating(initialValue, dependency));
        return coreCore(untilTimeoutSupplier(Wait::runTask, task, data.timeData), data);
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
        return coreCore(repeatUntilTimeoutSupplier(Wait::runTask, task, data.timeData), data);
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
