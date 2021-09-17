package com.neathorium.thorium.core.namespaces.validators;

import com.neathorium.thorium.core.records.HandleResultData;

import static org.apache.commons.lang3.StringUtils.isBlank;

public interface HandlerResultDataValidator {
    static <T, U> String isInvalidHandlerResultDataMessage(HandleResultData<T, U> data) {
        final var baseName = "Handle Result Data";
        var message = CoreFormatter.isNullMessageWithName(data, baseName);
        if (isBlank(message)) {
            message += (
                CoreFormatter.isNullMessageWithName(data.caster, baseName + " Caster") +
                CoreFormatter.isNullMessageWithName(data.parameter, baseName + " Parameter")
            );
        }

        return CoreFormatter.getNamedErrorMessageOrEmpty("isInvalidHandlerResultDataMessage", message);
    }
}
