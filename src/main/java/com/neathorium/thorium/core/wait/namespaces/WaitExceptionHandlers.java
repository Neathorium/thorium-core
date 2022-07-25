package com.neathorium.thorium.core.wait.namespaces;

import com.neathorium.thorium.core.data.namespaces.factories.DataFactoryFunctions;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.wait.constants.WaitFormatterConstants;
import com.neathorium.thorium.core.wait.exceptions.WrappedExecutionException;
import com.neathorium.thorium.core.wait.exceptions.WrappedThreadInterruptedException;
import com.neathorium.thorium.core.wait.exceptions.WrappedTimeoutException;
import com.neathorium.thorium.core.wait.namespaces.formatters.WaitExceptionFormatters;
import com.neathorium.thorium.core.wait.records.tasks.WaitTask;
import com.neathorium.thorium.exceptions.constants.ExceptionConstants;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;
import com.neathorium.thorium.java.extensions.namespaces.utilities.BooleanUtilities;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.function.UnaryOperator;

public interface WaitExceptionHandlers {
    static Data<UnaryOperator<String>> futureInformationHandler(WaitTask<?, ?, ?> task, Runnable runnable) {
        var exception = ExceptionConstants.EXCEPTION;
        UnaryOperator<String> formatter = null;
        try {
            runnable.run();
        } catch (WrappedThreadInterruptedException ex) {
            Thread.currentThread().interrupt();
            exception = new InterruptedException(ex.getMessage());
            formatter = WaitExceptionFormatters::getWaitInterruptMessage;
        } catch (CancellationException ex) {
            exception = ex;
            if (BooleanUtilities.isFalse(task.STATE_DATA.data.STATUS())) {
                formatter = WaitExceptionFormatters::getWaitCancellationWithoutResultMessage;
            }
        } catch (WrappedExecutionException ex) {
            exception = new ExecutionException(ex.getMessage(), ex);
            formatter = WaitExceptionFormatters::getWaitExpectedExceptionMessage;
        } catch (WrappedTimeoutException ex) {
            exception = new TimeoutException(ex.getMessage());
            formatter = WaitExceptionFormatters::getWaitTimeoutExceptionMessage;
        }

        final var message =  NullablePredicates.isNotNull(formatter) ? formatter.apply(exception.getMessage()) : WaitFormatterConstants.TASK_SUCCESSFULLY_ENDED;
        return DataFactoryFunctions.getWith(formatter, task.STATE_DATA.data.STATUS(), "futureInformationHandler", message, exception);
    }
}
