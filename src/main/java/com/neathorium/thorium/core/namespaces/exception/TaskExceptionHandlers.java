package com.neathorium.thorium.core.namespaces.exception;

import com.neathorium.thorium.core.constants.exception.ExceptionConstants;
import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;
import com.neathorium.thorium.core.namespaces.DataFactoryFunctions;
import com.neathorium.thorium.core.namespaces.DataFunctions;
import com.neathorium.thorium.core.namespaces.predicates.DataPredicates;
import com.neathorium.thorium.core.records.Data;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface TaskExceptionHandlers {
    static <T> Data<Boolean> futureHandler(CompletableFuture<T> task) {
        final var nameof = "futureHandler";
        if (NullableFunctions.isNull(task)) {
            return DataFactoryFunctions.getInvalidBooleanWith(nameof, "Task parameter" + CoreFormatterConstants.WAS_NULL);
        }

        var exception = ExceptionConstants.EXCEPTION;
        try {
            task.get();
        } catch (CancellationException | InterruptedException | ExecutionException ex) {
            exception = ex;
        }

        final var status = ExceptionFunctions.isNonException(exception) && task.isDone();
        return DataFactoryFunctions.getBoolean(status, nameof, status ? CoreFormatterConstants.INVOCATION_SUCCESSFUL : CoreFormatterConstants.INVOCATION_EXCEPTION, exception);
    }

    static Data<Boolean> futureDataHandler(CompletableFuture<? extends Data<?>> task) {
        final var nameof = "futureDataHandler";
        if (NullableFunctions.isNull(task)) {
            return DataFactoryFunctions.getInvalidBooleanWith(nameof, "Task parameter" + CoreFormatterConstants.WAS_NULL);
        }

        var exception = ExceptionConstants.EXCEPTION;
        Data<?> data = null;
        try {
            data = task.get();
        } catch (CancellationException | InterruptedException | ExecutionException ex) {
            exception = ex;
        }

        final var isNotBlank = NullableFunctions.isNotNull(data) && NullableFunctions.isNotNull(data.message) && isNotBlank(data.message.nameof);
        return DataFactoryFunctions.getBoolean(
            ExceptionFunctions.isNonException(exception) && DataPredicates.isValidNonFalse(data),
            isNotBlank ? data.message.nameof : nameof,
            task.isDone() ? DataFunctions.getStatusMessageFromData(data) : CoreFormatterConstants.INVOCATION_EXCEPTION,
            exception
        );
    }
}
