package com.neathorium.thorium.core.namespaces.validators;

import com.neathorium.thorium.core.records.MethodMessageData;

import static org.apache.commons.lang3.StringUtils.isBlank;

public interface MethodMessageDataValidators {

    static String isInvalidMessage(MethodMessageData data) {
        final var baseName = "Data";
        var message = CoreFormatter.isNullMessageWithName(data, baseName);
        if (isBlank(message)) {
            message += (
                CoreFormatter.isBlankMessageWithName(data.message, baseName + " Message") +
                CoreFormatter.isBlankMessageWithName(data.nameof, baseName + " Name of")
            );
        }

        return  CoreFormatter.getNamedErrorMessageOrEmpty("isInvalidMessage", message);
    }
}
