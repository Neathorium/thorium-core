package com.neathorium.thorium.core.wait.namespaces.factories;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.wait.records.WaitData;
import com.neathorium.thorium.core.wait.records.WaitTimeData;
import com.neathorium.thorium.core.wait.records.VoidWaitData;
import com.neathorium.thorium.core.wait.records.tasks.common.WaitTaskCommonData;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface WaitDataFactory {
    static <DependencyType, U, ReturnType> WaitData<DependencyType, U, ReturnType> getWith(Function<DependencyType, U> function, Predicate<ReturnType> exitCondition, String conditionMessage, WaitTimeData timeData) {
        return new WaitData<>(new WaitTaskCommonData<>(function, exitCondition), conditionMessage, timeData);
    }

    static <DependencyType, U, ReturnType> WaitData<DependencyType, U, ReturnType> getWithIntervalAndTimeout(Function<DependencyType, U> function, Predicate<ReturnType> exitCondition, String conditionMessage, int interval, int timeout) {
        return WaitDataFactory.getWith(function, exitCondition, conditionMessage, WaitTimeDataFactory.getWithDefaultClock(interval, timeout));
    }

    static <DependencyType, U, ReturnType> WaitData<DependencyType, U, ReturnType> getWithSleepDuration(Function<DependencyType, U> function, Predicate<ReturnType> exitCondition, int duration) {
        final var message = "Sleeping for " + duration + " milliseconds" + CoreFormatterConstants.END_LINE;
        return WaitDataFactory.getWithIntervalAndTimeout(function, exitCondition, message, 0, duration);
    }

    static <DependencyType, U, ReturnType> WaitData<DependencyType, U, ReturnType> getWithDefaultTimeData(Function<DependencyType, U> function, Predicate<ReturnType> exitCondition, String conditionMessage) {
        return WaitDataFactory.getWith(function, exitCondition, conditionMessage, WaitTimeDataFactory.getDefault());
    }

    static <U, ReturnType> VoidWaitData<U, ReturnType> getVoidWith(Function<Void, U> function, Predicate<ReturnType> exitCondition, String conditionMessage, WaitTimeData timeData) {
        return new VoidWaitData<>(new WaitTaskCommonData<>(function, exitCondition), conditionMessage, timeData);
    }

    static <U, ReturnType> VoidWaitData<U, ReturnType> getVoidWith(Supplier<U> function, Predicate<ReturnType> exitCondition, String conditionMessage, WaitTimeData timeData) {
        return new VoidWaitData<>(new WaitTaskCommonData<>((v) -> function.get(), exitCondition), conditionMessage, timeData);
    }

    static <U, ReturnType> VoidWaitData<U, ReturnType> getVoidWithIntervalAndTimeout(Function<Void, U> function, Predicate<ReturnType> exitCondition, String conditionMessage, int interval, int timeout) {
        return WaitDataFactory.getVoidWith(function, exitCondition, conditionMessage, WaitTimeDataFactory.getWithDefaultClock(interval, timeout));
    }

    static <U, ReturnType> VoidWaitData<U, ReturnType> getVoidWithSleepDuration(Function<Void, U> function, Predicate<ReturnType> exitCondition, int duration) {
        final var message = "Sleeping for " + duration + " milliseconds" + CoreFormatterConstants.END_LINE;
        return WaitDataFactory.getVoidWithIntervalAndTimeout(function, exitCondition, message, 0, duration);
    }

    static <U, ReturnType> VoidWaitData<U, ReturnType> getVoidWithDefaultTimeData(Function<Void, U> function, Predicate<ReturnType> exitCondition, String conditionMessage) {
        return WaitDataFactory.getVoidWith(function, exitCondition, conditionMessage, WaitTimeDataFactory.getDefault());
    }

    static <U, ReturnType> VoidWaitData<U, ReturnType> getVoidWithIntervalAndTimeout(Supplier<U> function, Predicate<ReturnType> exitCondition, String conditionMessage, int interval, int timeout) {
        return WaitDataFactory.getVoidWith(function, exitCondition, conditionMessage, WaitTimeDataFactory.getWithDefaultClock(interval, timeout));
    }

    static <U, ReturnType> VoidWaitData<U, ReturnType> getVoidWithSleepDuration(Supplier<U> function, Predicate<ReturnType> exitCondition, int duration) {
        final var message = "Sleeping for " + duration + " milliseconds" + CoreFormatterConstants.END_LINE;
        return WaitDataFactory.getVoidWithIntervalAndTimeout(function, exitCondition, message, 0, duration);
    }

    static <U, ReturnType> VoidWaitData<U, ReturnType> getVoidWithDefaultTimeData(Supplier<U> function, Predicate<ReturnType> exitCondition, String conditionMessage) {
        return WaitDataFactory.getVoidWith(function, exitCondition, conditionMessage, WaitTimeDataFactory.getDefault());
    }
}
