package com.neathorium.thorium.core.wait.constants;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.wait.exceptions.WrappedExecutionException;
import com.neathorium.thorium.core.wait.exceptions.WrappedThreadInterruptedException;
import com.neathorium.thorium.core.wait.exceptions.WrappedTimeoutException;
import com.neathorium.thorium.exceptions.constants.ExceptionConstants;
import com.neathorium.thorium.exceptions.records.ExceptionData;

public abstract class WaitExceptionConstants {
    public static final String WAITING_FAILED = "Waiting failed" + CoreFormatterConstants.COLON_NEWLINE;
    public static final String INTERRUPTION_MESSAGE = WaitExceptionConstants.WAITING_FAILED + "Thread interruption occurred, exception message" + CoreFormatterConstants.COLON_NEWLINE;
    public static final String EXPECTED_EXCEPTION_MESSAGE = WaitExceptionConstants.WAITING_FAILED + "Expected exception occurred, exception message" + CoreFormatterConstants.COLON_NEWLINE;
    public static final String TIMEOUT_EXCEPTION_MESSAGE = WaitExceptionConstants.WAITING_FAILED + "Timeout exception occurred, exception message" + CoreFormatterConstants.COLON_NEWLINE;
    public static final String CANCELLATION_EXCEPTION_MESSAGE = WaitExceptionConstants.WAITING_FAILED + "Cancellation exception occurred with no result, exception message" + CoreFormatterConstants.COLON_NEWLINE;

    public static final ExceptionData<RuntimeException> THREAD_INTERRUPTED_DATA = new ExceptionData<>(WrappedThreadInterruptedException::new, "Thread was interrupted, exception is wrapped for code" + CoreFormatterConstants.END_LINE);
    public static final ExceptionData<RuntimeException> EXECUTION_EXCEPTION_DATA = new ExceptionData<>(WrappedExecutionException::new, "Exception occurred during Execution, exception is wrapped for code" + CoreFormatterConstants.END_LINE);
    public static final ExceptionData<RuntimeException> TIMEOUT_EXCEPTION_DATA = new ExceptionData<>(WrappedTimeoutException::new, "Timeout exception occurred, exception is wrapped for code" + CoreFormatterConstants.END_LINE);
    public static final ExceptionData<RuntimeException> NO_EXCEPTION_DATA = new ExceptionData<>((constructor, message) -> ExceptionConstants.RUNTIME_EXCEPTION, ExceptionConstants.RUNTIME_EXCEPTION.getMessage());
}
