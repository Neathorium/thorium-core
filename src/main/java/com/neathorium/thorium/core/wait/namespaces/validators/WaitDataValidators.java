package com.neathorium.thorium.core.wait.namespaces.validators;

import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;
import com.neathorium.thorium.core.wait.interfaces.IWaitData;
import com.neathorium.thorium.core.wait.records.WaitData;
import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.StringUtils.isBlank;

public interface WaitDataValidators {
    static <T, U, V> String isValidWaitData(IWaitData<T, U, V> data) {
        var message = CoreFormatter.isNullMessageWithName(data, "Wait Data");
        if (isBlank(message)) {
            message += WaitParameterValidators.validateUntilParameters(data.TASK_DATA(), data.TIME_DATA());
        }

        return CoreFormatter.getNamedErrorMessageOrEmpty("isValidWaitData", message);
    }

    static String isValidSleepData(WaitData<Void, Void, Void> data) {
        final var nameof = "WaitDataValidators.isValidSleepData";
        var errors = CoreFormatter.isNullMessageWithName(data, "Wait Data");
        if (StringUtils.isBlank(errors)) {
            errors += CoreFormatter.isNullMessageWithName(data.TIME_DATA(), "TimeData");
        }

        if (StringUtils.isNotBlank(errors)) {
            return CoreFormatter.getNamedErrorMessageOrEmpty(nameof, errors);
        }

        final var timeData = data.TIME_DATA();
        final var entryData = data.TIME_DATA().ENTRY_PAIR_DATA();
        errors += (
            CoreFormatter.isNullMessageWithName(timeData.CLOCK(), "TimeData clock") +
            CoreFormatter.isNullMessageWithName(entryData, "Duration and Interval pair data")
        );
        if (StringUtils.isNotBlank(errors)) {
            return CoreFormatter.getNamedErrorMessageOrEmpty(nameof, errors);
        }

        errors += (
            CoreFormatter.isNullMessageWithName(entryData.DURATION(), "Duration data") +
            CoreFormatter.isNullMessageWithName(entryData.INTERVAL(), "Interval data")
        );


        return CoreFormatter.getNamedErrorMessageOrEmpty(nameof, errors);
    }
}
