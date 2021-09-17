package com.neathorium.thorium.core.namespaces.validators.wait;

import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;
import com.neathorium.thorium.core.records.wait.WaitData;

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
                CoreFormatter.isNullMessageWithName(timeData.clock, "TimeData clock") +
                CoreFormatter.isNullMessageWithName(timeData.duration, "TimeData duration")
            );
        }

        return CoreFormatter.getNamedErrorMessageOrEmpty("validateWaitTimeData", message);
    }
}
