package com.neathorium.thorium.core.wait.namespaces.formatters;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.wait.records.WaitTimeData;
import com.neathorium.thorium.java.extensions.namespaces.utilities.StringUtilities;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public interface WaitFormatters {
    static String getExecutionTimeMessage(String startFragment, String message, WaitTimeData data, Instant startTime, Instant stopTime) {
        final var localMessage = "\t" + (StringUtilities.endsWithCaseInsensitive(message, "\n") ? message : message + "\n");
        return (
            startFragment +
            localMessage +
            "\tExecution ran from time(\"" + startTime.toString() + "\") to (\"" + stopTime.toString() + "\")" + CoreFormatterConstants.END_LINE +
            "\tDuration(\"" + data.ENTRY_PAIR_DATA().DURATION().LENGTH() + "\" " + data.ENTRY_PAIR_DATA().DURATION().TIME_UNIT().toString() + "), actually ran for " + ChronoUnit.MILLIS.between(startTime, stopTime) + " milliseconds, with " + data.ENTRY_PAIR_DATA().INTERVAL().LENGTH() + "\" " + data.ENTRY_PAIR_DATA().DURATION().TIME_UNIT() + " interval" + CoreFormatterConstants.END_LINE
        );
    }
}
