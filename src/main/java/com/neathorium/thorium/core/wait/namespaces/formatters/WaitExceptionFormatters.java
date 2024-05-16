package com.neathorium.thorium.core.wait.namespaces.formatters;

import com.neathorium.thorium.core.wait.constants.WaitExceptionConstants;
import org.apache.commons.lang3.StringUtils;

public interface WaitExceptionFormatters {
    private static String formatMessage(String exceptionMessage, String message) {
        final var localMessage = StringUtils.isNotBlank(message) ? message : "";
        return exceptionMessage + "    " + localMessage.replaceAll("\n", "\n    ");
    }

    static String getWaitInterruptMessage(String message) {
        return WaitExceptionFormatters.formatMessage(WaitExceptionConstants.INTERRUPTION_MESSAGE, message);
    }

    static String getWaitExpectedExceptionMessage(String message) {
        return WaitExceptionFormatters.formatMessage(WaitExceptionConstants.EXPECTED_EXCEPTION_MESSAGE, message);
    }

    static String getWaitTimeoutExceptionMessage(String message) {
        return WaitExceptionFormatters.formatMessage(WaitExceptionConstants.TIMEOUT_EXCEPTION_MESSAGE, message);
    }

    static String getWaitCancellationWithoutResultMessage(String message) {
        return WaitExceptionFormatters.formatMessage(WaitExceptionConstants.CANCELLATION_EXCEPTION_MESSAGE, message);
    }
}
