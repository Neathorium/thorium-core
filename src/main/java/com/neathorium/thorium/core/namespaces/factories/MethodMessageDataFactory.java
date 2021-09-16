package com.neathorium.thorium.core.namespaces.factories;

import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;
import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;
import com.neathorium.thorium.core.records.MethodMessageData;

import java.util.function.BiFunction;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface MethodMessageDataFactory {
    static MethodMessageData getWith(BiFunction<String, String, String> formatter, String nameof, String message) {
        final var errorMessage = CoreFormatter.isNullMessageWithName(formatter, "Formatter function");
        if (NullableFunctions.isNull(formatter)) {
            throw new IllegalArgumentException(errorMessage);
        }

        final var lNameof = isNotBlank(nameof) ? nameof : "getWith";
        final var lMessage = isNotBlank(message) ? message : "Default method message";
        return new MethodMessageData(formatter, lNameof, lMessage);
    }

    static MethodMessageData getWith(String nameof, String message) {
        return getWith(CoreFormatter::getMethodMessageDataFormatted, nameof, message);
    }

    static MethodMessageData getWithMessage(String message) {
        return getWith("getWith", message);
    }
}
