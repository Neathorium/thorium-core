package com.neathorium.thorium.core.wait.namespaces.validators;

import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;
import com.neathorium.thorium.core.wait.records.WaitData;

import static org.apache.commons.lang3.StringUtils.isBlank;

public interface WaitDataValidators {
    static <T, U, V> String isValidWaitData(WaitData<T, U, V> data) {
        var message = CoreFormatter.isNullMessageWithName(data, "Wait Data");
        if (isBlank(message)) {
            message += WaitParameterValidators.validateUntilParameters(data.taskData, data.timeData);
        }

        return CoreFormatter.getNamedErrorMessageOrEmpty("isValidWaitData", message);
    }

    static <T, U, V> String isValidSleepData(WaitData<Void, Void, Void> data) {
        var message = CoreFormatter.isNullMessageWithName(data, "Wait Data");
        if (isBlank(message)) {
            message += CoreFormatter.isNullMessageWithName(data.timeData, "TimeData");
        }
        if (isBlank(message)) {
            final var timeData = data.timeData;
            message += (
                CoreFormatter.isNullMessageWithName(timeData.CLOCK(), "TimeData clock") +
                CoreFormatter.isNullMessageWithName(timeData.DURATION(), "TimeData duration")
            );
        }

        return CoreFormatter.getNamedErrorMessageOrEmpty("validateWaitTimeData", message);
    }
}
