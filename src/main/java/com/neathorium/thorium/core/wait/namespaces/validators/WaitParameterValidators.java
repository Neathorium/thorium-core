package com.neathorium.thorium.core.wait.namespaces.validators;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;
import com.neathorium.thorium.core.wait.records.WaitTimeData;
import com.neathorium.thorium.core.wait.records.WaitTimeEntryData;
import com.neathorium.thorium.core.wait.records.WaitTimeEntryDataPair;
import com.neathorium.thorium.core.wait.records.tasks.common.WaitTaskCommonData;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.StringUtils.isBlank;

public interface WaitParameterValidators {
    static String isValid(long interval, TimeUnit timeUnit) {
        final var nameof = "WaitParameterValidators.isValid";
        final var errors = (
            ((interval > -1) ? "" : "Interval(\"" + interval + "\") was smaller than zero" + CoreFormatterConstants.END_LINE) +
            CoreFormatter.isNullMessageWithName(timeUnit, "Time Unit")
        );

        return CoreFormatter.getNamedErrorMessageOrEmpty(nameof, errors);
    }
    static String isValid(WaitTimeEntryData entryData) {
        final var nameof = "WaitParameterValidators.isValid";
        var errors = CoreFormatter.isNullMessageWithName(entryData, "Entry Data");

        return CoreFormatter.getNamedErrorMessageOrEmpty(nameof, errors);
    }
    static String isValid(WaitTimeEntryData interval, WaitTimeEntryData duration) {
        final var nameof = "WaitParameterValidators.isValid";
        var errors = (
            WaitParameterValidators.isValid(interval) +
            WaitParameterValidators.isValid(duration)
        );
        return CoreFormatter.getNamedErrorMessageOrEmpty(nameof, errors);
    }

    static String isValid(WaitTimeEntryDataPair entryPairData) {
        final var nameof = "WaitParameterValidators.isValid";
        var errors = CoreFormatter.isNullMessageWithName(entryPairData, "Entry Pair Data");
        if (StringUtils.isBlank(errors)) {
            errors += WaitParameterValidators.isValid(entryPairData.INTERVAL(), entryPairData.DURATION());
        }

        return CoreFormatter.getNamedErrorMessageOrEmpty(nameof, errors);
    }

    static String validateWaitTimeData(WaitTimeData timeData) {
        var message = CoreFormatter.isNullMessageWithName(timeData, "TimeData");
        if (isBlank(message)) {
            message += (
                CoreFormatter.isNullMessageWithName(timeData.CLOCK(), "TimeData clock") +
                WaitParameterValidators.isValid(timeData.ENTRY_PAIR_DATA())
            );
        }

        return CoreFormatter.getNamedErrorMessageOrEmpty("validateWaitTimeData", message);
    }

    static <T, U, V> String validateUntilParameters(WaitTaskCommonData<T, U, V> taskData, WaitTimeData timeData) {
        return CoreFormatter.getNamedErrorMessageOrEmpty(
            "validateUntilParameters",
            isValidWaitTaskCommonData(taskData) + validateWaitTimeData(timeData)
        );
    }

    static <T, U, V> String isValidWaitTaskCommonData(WaitTaskCommonData<T, U, V> data) {
        var message = CoreFormatter.isNullMessageWithName(data, "Wait Task Common Data");
        if (isBlank(message)) {
            message += (
                CoreFormatter.isNullMessageWithName(data.FUNCTION(), "Function") +
                CoreFormatter.isNullMessageWithName(data.EXIT_CONDITION(), "Exit Condition")
            );
        }

        return CoreFormatter.getNamedErrorMessageOrEmpty("validateUntilParameters", message);
    }
}
