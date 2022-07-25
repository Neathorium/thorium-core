package com.neathorium.thorium.core.namespaces.formatter.executor;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.data.namespaces.DataFunctions;
import com.neathorium.thorium.core.data.namespaces.factories.DataFactoryFunctions;
import com.neathorium.thorium.core.data.namespaces.predicates.DataPredicates;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.namespaces.exception.TaskExceptionHandlers;
import com.neathorium.thorium.java.extensions.namespaces.predicates.BasicPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.SizablePredicates;
import com.neathorium.thorium.java.extensions.namespaces.utilities.BooleanUtilities;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public interface StepExecutorFormatters {
    private static String getTaskExecutionTimeMessage(Instant startTime, Instant stopTime, int duration) {
        return (
            "\tExecution ran from time(\"" + startTime.toString() + "\") to (\"" + stopTime.toString() + "\")" + CoreFormatterConstants.END_LINE +
            "\tDuration(\"" + duration + "\" milliseconds), actually ran for " + ChronoUnit.MILLIS.between(startTime, stopTime) + " milliseconds" + CoreFormatterConstants.END_LINE
        );
    }

    private static String getTaskIndexedMessage(int index, String message) {
        return "\t" + index + ". task: " + message;
    }

    private static String getAmountFragmentMessage(int passed, int length) {
        var message = "";
        if (SizablePredicates.isSizeEqualTo(passed, length)) {
            message = "All(\"" + length + "\") tasks executed successfully";
        } else {
            if (BasicPredicates.isPositiveNonZero(passed)) {
                message = "Some(\"" + passed + "\") tasks executed successfully out of all(\"" + length + "\")";
            } else {
                message = "No tasks executed successfully";
            }
        }

        return message + CoreFormatterConstants.END_LINE;
    }

    private static Data<Boolean> getExecuteParallelTimedMessageDataCore(
        String nameof,
        Predicate<Integer> conditionHandler,
        List<CompletableFuture<? extends Data<?>>> tasks,
        CompletableFuture<?> handlerTask,
        Data<Boolean> result,
        Instant startTime,
        Instant stopTime,
        int duration
    ) {
        final var messageBuilder = new StringBuilder(getTaskExecutionTimeMessage(startTime, stopTime, duration));
        final var done = BooleanUtilities.isFalse(BooleanUtilities.isFalse(handlerTask.isDone()) || DataPredicates.isInvalidOrFalse(result));
        if (BooleanUtilities.isFalse(done)) {
            messageBuilder.append("\t").append(DataFunctions.getFormattedMessage(result));
        }

        final var length = tasks.size();
        var passed = length;

        Data<?> current;
        for (var index = 0; index < length; ++index) {
            current = TaskExceptionHandlers.futureDataHandler(tasks.get(index));
            if (DataPredicates.isInvalidOrFalse(current)) {
                --passed;
            }

            messageBuilder.append(getTaskIndexedMessage(index + 1, DataFunctions.getStatusMessageFromData(current).replaceAll("\\n\\t", "\n\t\t")));
        }

        return DataFactoryFunctions.getBoolean(done && conditionHandler.test(passed), nameof, getAmountFragmentMessage(passed, length) + messageBuilder);
    }

    static Data<Boolean> getExecuteAllParallelTimedMessageData(List<CompletableFuture<? extends Data<?>>> tasks, CompletableFuture<?> all, Data<Boolean> result, Instant startTime, Instant stopTime, int duration) {
        final var nameof = "getExecuteAllParallelTimedMessageData";
        return getExecuteParallelTimedMessageDataCore(nameof, (var passed) -> SizablePredicates.isSizeEqualTo(passed, tasks.size()), tasks, all, result, startTime, stopTime, duration);
    }

    static Data<Boolean> getExecuteAnyParallelTimedMessageData(List<CompletableFuture<? extends Data<?>>> tasks, CompletableFuture<?> all, Data<Boolean> result, Instant startTime, Instant stopTime, int duration) {
        final var nameof = "getExecuteAnyParallelTimedMessageData";
        return getExecuteParallelTimedMessageDataCore(nameof, BasicPredicates::isPositiveNonZero, tasks, all, result, startTime, stopTime, duration);
    }
}
