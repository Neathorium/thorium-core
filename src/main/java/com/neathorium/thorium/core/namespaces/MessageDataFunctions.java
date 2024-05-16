package com.neathorium.thorium.core.namespaces;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.records.SimpleMessageData;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;

public interface MessageDataFunctions {
    static String get(SimpleMessageData data, boolean status) {
        final var formatter = data.FORMATTER;
        final var message = data.MESSAGE;
        final var isFormatterNull = NullablePredicates.isNull(formatter);
        final var isMessageBlank = StringUtils.isBlank(message);
        if (isFormatterNull && isMessageBlank) {
            return CoreFormatterConstants.EXECUTION_STATUS_COLON_SPACE + status + CoreFormatterConstants.END_LINE;
        }

        if (isFormatterNull) {
            return CoreFormatterConstants.EXECUTION_STATUS_COLON_SPACE + status + CoreFormatterConstants.END_LINE + "Message: " + message;
        }

        if (isMessageBlank) {
            return CoreFormatterConstants.EXECUTION_STATUS_COLON_SPACE + status + "Message was empty, please fix - result: " + formatter.apply(CoreFormatterConstants.EMPTY, status) + CoreFormatterConstants.END_LINE;
        }

        return formatter.apply(message, status);
    }

    static Function<Boolean, String> get(SimpleMessageData data) {
        return status -> MessageDataFunctions.get(data, status);
    }
}
