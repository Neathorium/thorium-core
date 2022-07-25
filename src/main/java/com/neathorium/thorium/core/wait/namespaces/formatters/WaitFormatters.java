package com.neathorium.thorium.core.wait.namespaces.formatters;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.wait.constants.WaitExceptionConstants;
import com.neathorium.thorium.core.wait.records.WaitTimeData;
import com.neathorium.thorium.java.extensions.namespaces.utilities.StringUtilities;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public interface WaitFormatters {
    static String getExecutionTimeMessage(boolean success, String message, WaitTimeData data, Instant startTime, Instant stopTime) {
        final var localMessage = "\t" + (StringUtilities.endsWithCaseInsensitive(message, "\n") ? message : message + "\n");
        return (
            (success ? CoreFormatterConstants.WAITING_SUCCESSFUL : WaitExceptionConstants.WAITING_FAILED) +
            localMessage +
            "\tExecution ran from time(\"" + startTime.toString() + "\") to (\"" + stopTime.toString() + "\")" + CoreFormatterConstants.END_LINE +
            "\tDuration(\"" + data.DURATION().toMillis() + "\" milliseconds), actually ran for " + ChronoUnit.MILLIS.between(startTime, stopTime) + " milliseconds, with " + data.INTERVAL().toMillis() + " milliseconds interval" + CoreFormatterConstants.END_LINE
        );
    }
}
