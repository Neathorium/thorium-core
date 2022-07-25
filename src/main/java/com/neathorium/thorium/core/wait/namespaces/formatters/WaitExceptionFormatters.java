package com.neathorium.thorium.core.wait.namespaces.formatters;

import com.neathorium.thorium.core.wait.constants.WaitExceptionConstants;

public interface WaitExceptionFormatters {
    private static String formatMessage(String exceptionMessage, String message) {
        return exceptionMessage + "    " + message.replaceAll("\n", "\n    ");
    }

    static String getWaitInterruptMessage(String message) {
        return formatMessage(WaitExceptionConstants.INTERRUPTION_MESSAGE, message);
    }

    static String getWaitExpectedExceptionMessage(String message) {
        return formatMessage(WaitExceptionConstants.EXPECTED_EXCEPTION_MESSAGE, message);
    }

    static String getWaitTimeoutExceptionMessage(String message) {
        return formatMessage(WaitExceptionConstants.TIMEOUT_EXCEPTION_MESSAGE, message);
    }

    static String getWaitCancellationWithoutResultMessage(String message) {
        return formatMessage(WaitExceptionConstants.CANCELLATION_EXCEPTION_MESSAGE, message);
    }
}
