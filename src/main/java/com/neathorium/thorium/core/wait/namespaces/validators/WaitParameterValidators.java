package com.neathorium.thorium.core.wait.namespaces.validators;

import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;
import com.neathorium.thorium.core.wait.records.WaitTimeData;
import com.neathorium.thorium.core.wait.records.tasks.common.WaitTaskCommonData;

import static org.apache.commons.lang3.StringUtils.isBlank;

public interface WaitParameterValidators {
    static String validateWaitTimeData(WaitTimeData timeData) {
        var message = CoreFormatter.isNullMessageWithName(timeData, "TimeData");
        if (isBlank(message)) {
            message += (
                CoreFormatter.isNullMessageWithName(timeData.CLOCK(), "TimeData clock") +
                CoreFormatter.isNullMessageWithName(timeData.INTERVAL(), "TimeData interval") +
                CoreFormatter.isNullMessageWithName(timeData.DURATION(), "TimeData duration")
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
                CoreFormatter.isNullMessageWithName(data.function, "Function") +
                CoreFormatter.isNullMessageWithName(data.exitCondition, "Exit Condition")
            );
        }

        return CoreFormatter.getNamedErrorMessageOrEmpty("validateUntilParameters", message);
    }
}
