package com.neathorium.thorium.core.namespaces.exception;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.data.namespaces.DataFunctions;
import com.neathorium.thorium.core.data.namespaces.factories.DataFactoryFunctions;
import com.neathorium.thorium.core.data.namespaces.predicates.DataPredicates;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.exceptions.constants.ExceptionConstants;
import com.neathorium.thorium.exceptions.namespaces.ExceptionFunctions;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface TaskExceptionHandlers {
    static <T> Data<Boolean> futureHandler(CompletableFuture<T> task) {
        final var nameof = "futureHandler";
        if (NullablePredicates.isNull(task)) {
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
        if (NullablePredicates.isNull(task)) {
            return DataFactoryFunctions.getInvalidBooleanWith(nameof, "Task parameter" + CoreFormatterConstants.WAS_NULL);
        }

        var exception = ExceptionConstants.EXCEPTION;
        Data<?> data = null;
        try {
            data = task.get();
        } catch (CancellationException | InterruptedException | ExecutionException ex) {
            exception = ex;
        }

        final var isNotBlank = NullablePredicates.isNotNull(data) && NullablePredicates.isNotNull(data.MESSAGE()) && isNotBlank(data.MESSAGE().NAMEOF());
        return DataFactoryFunctions.getBoolean(
            ExceptionFunctions.isNonException(exception) && DataPredicates.isValidNonFalse(data),
            isNotBlank ? data.MESSAGE().NAMEOF() : nameof,
            task.isDone() ? DataFunctions.getStatusMessageFromData(data) : CoreFormatterConstants.INVOCATION_EXCEPTION,
            exception
        );
    }
}
