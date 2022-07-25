package com.neathorium.thorium.core.namespaces.validators;

import com.neathorium.thorium.core.data.records.Data;

import static org.apache.commons.lang3.StringUtils.isBlank;

public interface DataValidators {
    private static String isInvalidParametersMessage(Data<?> data) {
        final var baseName = "Data";
        return (
            CoreFormatter.isNullMessageWithName(data.EXCEPTION(), baseName + " Exception") +
            CoreFormatter.isNullMessageWithName(data.EXCEPTION_MESSAGE(), baseName + " Exception message") +
            MethodMessageDataValidators.isInvalidMessage(data.MESSAGE())
        );
    }

    static String isInvalidMessage(Data<?> data) {
        final var baseName = "Data";
        var message = CoreFormatter.isNullMessageWithName(data, baseName);
        if (isBlank(message)) {
            message += isInvalidParametersMessage(data);
        }

        return CoreFormatter.getNamedErrorMessageOrEmpty("isInvalidMessage", message);
    }

    static String isInvalidOrFalseMessage(Data<?> data) {
        var message = isInvalidMessage(data);
        if (isBlank(message)) {
            message += CoreFormatter.isFalseMessage(data);
        }

        return CoreFormatter.getNamedErrorMessageOrEmpty("isInvalidOrFalseMessage", message);
    }
}
