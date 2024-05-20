package com.neathorium.thorium.core.namespaces.formatter.executor;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.data.namespaces.DataFunctions;
import com.neathorium.thorium.core.data.namespaces.factories.DataFactoryFunctions;
import com.neathorium.thorium.core.data.namespaces.predicates.DataPredicates;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.executor.namespaces.step.ParallelStepExecutionExceptionFactory;
import com.neathorium.thorium.core.namespaces.exception.TaskExceptionHandlers;
import com.neathorium.thorium.core.wait.records.WaitTimeEntryData;
import com.neathorium.thorium.exceptions.constants.ExceptionConstants;
import com.neathorium.thorium.exceptions.namespaces.ExceptionFunctions;
import com.neathorium.thorium.java.extensions.namespaces.predicates.BasicPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.SizablePredicates;
import com.neathorium.thorium.java.extensions.namespaces.utilities.BooleanUtilities;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.IntPredicate;

public interface StepExecutorFormatters {
    private static String getTaskExecutionTimeMessage(Instant startTime, Instant stopTime, WaitTimeEntryData entryData) {
        return (
            "\tExecution ran from time(\"" + startTime.toString() + "\") to (\"" + stopTime.toString() + "\")" + CoreFormatterConstants.END_LINE +
            "\tDuration(\"" + entryData.LENGTH() + "\" " + entryData.TIME_UNIT() + "), actually ran for " + ChronoUnit.MILLIS.between(startTime, stopTime) + " milliseconds" + CoreFormatterConstants.END_LINE
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
        IntPredicate conditionHandler,
        List<CompletableFuture<? extends Data<?>>> tasks,
        CompletableFuture<?> handlerTask,
        Data<Boolean> result,
        Instant startTime,
        Instant stopTime,
        WaitTimeEntryData entryData
    ) {
        final var localNameof = StringUtils.isNotBlank(nameof) ? nameof : "StepExecutorFormatters.getExecuteParallelTimedMessageDataCore";
        final var exceptionList = new HashMap<Integer, Throwable>();
        final var separator = "    ";
        final var messageBuilder = new StringBuilder(StepExecutorFormatters.getTaskExecutionTimeMessage(startTime, stopTime, entryData));
        final var done = BooleanUtilities.isFalse(BooleanUtilities.isFalse(handlerTask.isDone()) || DataPredicates.isInvalidOrFalse(result));
        Exception exception = ExceptionConstants.EXCEPTION;
        if (BooleanUtilities.isFalse(done)) {
            messageBuilder.append(separator).append(DataFunctions.getFormattedMessage(result));
            exception = result.EXCEPTION();
            if (ExceptionFunctions.isException(exception)) {
                exceptionList.put(0, exception);
            }
        }

        final var length = tasks.size();
        var passed = length;

        Data<?> current;
        for (var index = 0; index < length; ++index) {
            current = TaskExceptionHandlers.futureDataHandler(tasks.get(index));
            if (DataPredicates.isInvalidOrFalse(current)) {
                --passed;
                exception = current.EXCEPTION();
                if (ExceptionFunctions.isException(exception)) {
                    exceptionList.put(index + 1, exception);
                }
            }

            messageBuilder.append(StepExecutorFormatters.getTaskIndexedMessage(index + 1, DataFunctions.getStatusMessageFromData(current).replace("\n" + separator, "\n" + separator + separator)));
        }

        final var status = done && conditionHandler.test(passed);
        final var returnException = BasicPredicates.isZeroOrNonPositive(exceptionList.size()) ? ExceptionConstants.EXCEPTION : ParallelStepExecutionExceptionFactory.getWith(exceptionList);
        return DataFactoryFunctions.getBoolean(status, localNameof, getAmountFragmentMessage(passed, length) + messageBuilder, returnException);
    }

    static Data<Boolean> getExecuteAllParallelTimedMessageData(
        List<CompletableFuture<? extends Data<?>>> tasks,
        CompletableFuture<?> all,
        Data<Boolean> result,
        Instant startTime,
        Instant stopTime,
        WaitTimeEntryData entryData
    ) {
        final var nameof = "StepExecutorFormatters.getExecuteAllParallelTimedMessageData";
        return StepExecutorFormatters.getExecuteParallelTimedMessageDataCore(nameof, (var passed) -> SizablePredicates.isSizeEqualTo(passed, tasks.size()), tasks, all, result, startTime, stopTime, entryData);
    }

    static Data<Boolean> getExecuteAnyParallelTimedMessageData(
        List<CompletableFuture<? extends Data<?>>> tasks,
        CompletableFuture<?> all,
        Data<Boolean> result,
        Instant startTime,
        Instant stopTime,
        WaitTimeEntryData entryData
    ) {
        final var nameof = "StepExecutorFormatters.getExecuteAnyParallelTimedMessageData";
        return StepExecutorFormatters.getExecuteParallelTimedMessageDataCore(nameof, BasicPredicates::isPositiveNonZero, tasks, all, result, startTime, stopTime, entryData);
    }
}
