package com.neathorium.thorium.core.namespaces;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.exceptions.ArgumentNullException;
import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;
import com.neathorium.thorium.core.namespaces.exception.ExceptionFunctions;
import com.neathorium.thorium.core.namespaces.predicates.DataPredicates;
import com.neathorium.thorium.core.records.Data;
import com.neathorium.thorium.core.records.MethodMessageData;
import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface DataFunctions {
    static <T> T getObject(Data<T> data) {
        return data.object;
    }

    static <T> boolean getStatus(Data<T> data) {
        return data.status;
    }

    static <T> MethodMessageData getMethodMessageData(Data<T> data) {
        return data.message;
    }

    static String getFormattedMessage(Data data) {
        final var message = data.message;
        return message.formatter.apply(message.nameof, message.message);
    }

    static String getNameIfAbsent(Data<?> data, String nameof) {
        var name = "";
        if (NullableFunctions.isNotNull(data)) {
            name = data.message.nameof;
            if (StringUtilities.uncontains(name, nameof)) {
                name = nameof + ": " + name;
            }
        } else {
            name = isNotBlank(nameof) ? nameof : "getNameIfAbsent";
        }

        return name;
    }

    static <T> String getMessageFromData(T object) {
        final var message = CoreFormatter.isNullMessageWithName(object, "Object");
        if (isNotBlank(message)) {
            return message;
        }

        if (!(object instanceof Data)) {
            return String.valueOf(object);
        }

        final var data = ((Data<?>) object);
        return getFormattedMessage(data);
    }

    static <T> String getStatusMessageFromData(T object) {
        final var errorMessage = CoreFormatter.isNullMessageWithName(object, "Object");
        if (isNotBlank(errorMessage)) {
            return errorMessage;
        }

        if (!(object instanceof Data)) {
            return String.valueOf(object);
        }

        final var data = (Data<?>) object;
        final var messageData = data.message;
        var message = messageData.formatter.apply(messageData.nameof, messageData.message);
        if (DataPredicates.isInvalidOrFalse(data) && ExceptionFunctions.isException(data.exception)) {
            message += "\tAn exception has occurred: " + data.exceptionMessage + CoreFormatterConstants.END_LINE;
        }

        return message;
    }

    private static void throwIfNullCore(String name, Data<?> data) {
        final var message = CoreFormatter.isNullMessageWithName(data, "Data parameter");
        if (isBlank(message)) {
            return;
        }

        final var nameof = isNotBlank(name) ? name : "throwIfNullCore";
        throw new ArgumentNullException(nameof + ": " + message);
    }

    private static void throwIfExceptionCore(String name, Data<?> data) {
        final var exception = data.exception;
        if (ExceptionFunctions.isNonException(exception)) {
            return;
        }

        final var nameof = isNotBlank(name) ? name : "throwIfExceptionCore";
        throw new RuntimeException(nameof + ": " + data.exceptionMessage, exception);
    }

    static void throwIfException(Data<?> data) {
        final var nameof = "throwIfException";
        throwIfNullCore(nameof, data);
        throwIfExceptionCore(nameof, data);
    }

    static void throwIfInvalid(Data<?> data) {
        final var nameof = "throwIfInvalid";
        throwIfNullCore(nameof, data);
        if (DataPredicates.isInvalidOrFalse(data)) {
            throwIfException(data);
            throw new IllegalStateException(nameof + ": " + getFormattedMessage(data));
        }
    }
}
