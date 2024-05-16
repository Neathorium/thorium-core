package com.neathorium.thorium.core.wait.namespaces.formatters;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.wait.constants.WaitExceptionConstants;
import com.neathorium.thorium.core.wait.records.WaitTimeData;
import com.neathorium.thorium.java.extensions.namespaces.utilities.StringUtilities;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

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

    /*static String getExecutionTimeMessage(String startFragment, String message, WaitTimeData data, Instant startTime, Instant stopTime) {
        final var spaces = "    ";
        final var list = new ArrayList<String>();
        list.add(StringUtilities.endsWithCaseInsensitive(startFragment, "\n") ? startFragment : startFragment + "\n");
        list.add((StringUtilities.endsWithCaseInsensitive(message, "\n") ? message : message + "\n"));
        list.add("Execution ran from time(\"" + startTime.toString() + "\") to (\"" + stopTime.toString() + "\")" + CoreFormatterConstants.END_LINE);
        list.add("Duration(\"" + data.ENTRY_PAIR_DATA().DURATION().LENGTH() + "\" " + data.ENTRY_PAIR_DATA().DURATION().TIME_UNIT() + "), actually ran for " + ChronoUnit.MILLIS.between(startTime, stopTime) + " milliseconds, with " + data.ENTRY_PAIR_DATA().INTERVAL().LENGTH() + "\" " + data.ENTRY_PAIR_DATA().DURATION().TIME_UNIT() + " interval" + CoreFormatterConstants.END_LINE);

        return String.join(spaces, list);
    }*/

    /*static String getExecutionTimeMessage(boolean success, String message, WaitTimeData data, Instant startTime, Instant stopTime) {
        final var localMessage = "\t" + (StringUtilities.endsWithCaseInsensitive(message, "\n") ? message : message + "\n");
        return (
                (success ? CoreFormatterConstants.WAITING_SUCCESSFUL : WaitExceptionConstants.WAITING_FAILED) +
                        localMessage +
                        "\tExecution ran from time(\"" + startTime.toString() + "\") to (\"" + stopTime.toString() + "\")" + CoreFormatterConstants.END_LINE +
                        "\tDuration(\"" + data.ENTRY_PAIR_DATA().DURATION().LENGTH() + "\" milliseconds), actually ran for " + ChronoUnit.MILLIS.between(startTime, stopTime) + " milliseconds, with " + data.ENTRY_PAIR_DATA().INTERVAL().LENGTH() + " milliseconds interval" + CoreFormatterConstants.END_LINE
        );
    }*/
}
