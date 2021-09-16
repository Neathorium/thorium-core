package com.neathorium.thorium.core.namespaces;

import com.neathorium.thorium.core.constants.CoreDataConstants;
import com.neathorium.thorium.core.constants.DataFactoryConstants;
import com.neathorium.thorium.core.constants.exception.ExceptionConstants;
import com.neathorium.thorium.core.namespaces.factories.MethodMessageDataFactory;
import com.neathorium.thorium.core.records.Data;
import com.neathorium.thorium.core.records.MethodMessageData;
import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;

import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface DataFactoryFunctions {
    static <T> Data<T> getWith(T object, boolean status, MethodMessageData messageData, Exception exception, String exceptionMessage) {
        final var isExceptionNull = Objects.isNull(exception);
        final var message = (isExceptionNull ? CoreFormatterConstants.EXCEPTION_WAS_NULL : CoreFormatterConstants.EMPTY) + messageData.message;
        final var exMessage = isNotBlank(exceptionMessage) ? exceptionMessage : ((isExceptionNull ? CoreFormatterConstants.EXCEPTION_WAS_NULL : exception.getMessage()));
        final var methodMessage = MethodMessageDataFactory.getWith(messageData.nameof, message);
        return new Data<>(object, status, methodMessage, exception, exMessage);
    }

    static <T> Data<T> getWith(T object, boolean status, MethodMessageData messageData, Exception exception) {
        return getWith(object, status, messageData, exception, exception.getLocalizedMessage());
    }

    static <T> Data<T> getWith(T object, boolean status, MethodMessageData messageData) {
        return getWith(object, status, messageData, ExceptionConstants.EXCEPTION);
    }

    static <T> Data<T> getWith(T object, boolean status, String nameof, String message, Exception exception, String exceptionMessage) {
        return getWith(object, status, MethodMessageDataFactory.getWith(nameof, message), exception, exceptionMessage);
    }

    static <T> Data<T> getWith(T object, boolean status, String nameof, String message, Exception exception) {
        return getWith(object, status, MethodMessageDataFactory.getWith(nameof, message), exception);
    }

    static <T> Data<T> getWith(T object, boolean status, String nameof, String message) {
        return getWith(object, status, MethodMessageDataFactory.getWith(nameof, message));
    }

    static <T> Data<T> getWith(T object, boolean status, String message, Exception exception, String exceptionMessage) {
        return getWith(object, status, MethodMessageDataFactory.getWithMessage(message), exception, exceptionMessage);
    }

    static <T> Data<T> getWith(T object, boolean status, String message, Exception exception) {
        return getWith(object, status, MethodMessageDataFactory.getWithMessage(message), exception);
    }

    static <T> Data<T> getWith(T object, boolean status, String message) {
        return getWith(object, status, MethodMessageDataFactory.getWithMessage(message));
    }

    static <T> Data<T> getValidWith(T object, String nameof, String message) {
        return getWith(object, true, nameof, message);
    }

    static <T> Data<T> getInvalidWith(T object, String nameof, String message) {
        return getWith(object, false, nameof, message);
    }

    static Data<Boolean> getInvalidBooleanWith(String nameof, String message) {
        return getWith(false, false, nameof, message);
    }

    static Data<Boolean> getInvalidBooleanWith(String nameof, String message, Exception exception) {
        return getWith(false, false, nameof, message, exception);
    }

    static <T> Data<T> getErrorFunction(T object, String nameof, String message) {
        return getInvalidWith(object, nameof, message);
    }

    static <T> Data<T> getErrorFunction(T object, String message) {
        return getErrorFunction(object, "getErrorFunction", message);
    }

    static <T> Data<T> replaceNameAndMessage(Data<T> data, String nameof, String message) {
        return getWith(data.object, data.status, nameof, message, data.exception, data.exceptionMessage);
    }

    static <T> Data<T> replaceMethodMessageAndName(Data<T> data, String nameof, MethodMessageData messageData) {
        return replaceNameAndMessage(data, nameof, messageData.message);
    }

    static <T> Data<T> replaceMethodMessageData(Data<T> data, MethodMessageData message) {
        return getWith(data.object, data.status, message, data.exception, data.exceptionMessage);
    }

    static <T> Data<T> replaceObject(Data<?> data, T object) {
        return getWith(object, data.status, data.message, data.exception, data.exceptionMessage);
    }

    static <T> Data<T> replaceStatus(Data<T> data, boolean status) {
        return getWith(data.object, status, data.message, data.exception, data.exceptionMessage);
    }

    static <T> Data<T> replaceMessage(Data<T> data, String message) {
        return getWith(data.object, data.status, data.message.nameof, data.message.message, data.exception, data.exceptionMessage);
    }

    static <T> Data<T> replaceObjectAndMessage(Data<?> data, T object, String message) {
        return getWith(object, data.status, data.message.nameof, message, data.exception, data.exceptionMessage);
    }

    static <T> Data<T> replaceStatusAndMessage(Data<T> data, boolean status, String message) {
        return getWith(data.object, status, data.message.nameof, message, data.exception, data.exceptionMessage);
    }

    static <T> Data<T> replaceName(Data<T> data, String nameof) {
        return getWith(data.object, data.status, nameof, data.message.message, data.exception, data.exceptionMessage);
    }

    static <T> Data<T> prependMessage(Data<T> data, String message) {
        return getWith(data.object, data.status, message + data.message, data.exception, data.exceptionMessage);
    }

    static <T> Data<T> prependMessage(Data<T> data, String nameof, String message) {
        return getWith(data.object, data.status, nameof, message + data.message.message, data.exception, data.exceptionMessage);
    }

    static <T> Data<T> replaceMessage(Data<T> data, String nameof, String message) {
        return getWith(data.object, data.status, nameof, message, data.exception, data.exceptionMessage);
    }

    static <T> Data<T> appendMessage(Data<T> data, String message) {
        return getWith(data.object, data.status, data.message + message, data.exception, data.exceptionMessage);
    }

    static <T> Data<T> appendMessage(Data<T> data, String nameof, String message) {
        return getWith(data.object, data.status, nameof, data.message.message + message, data.exception, data.exceptionMessage);
    }

    static Data<Boolean> getBoolean(boolean status, String nameof, String message, Exception exception, String exceptionMessage) {
        return CoreUtilities.areNotNull(message, nameof, exception, exceptionMessage) && status ? getWith(status, status, nameof, message, exception, exceptionMessage) : CoreDataConstants.NULL_BOOLEAN;
    }

    static Data<Boolean> getBoolean(boolean status, String message, Exception exception, String exceptionMessage) {
        return getWith(status, status, message, exception, exceptionMessage);
    }

    static Data<Boolean> getBoolean(boolean status, String nameof, String message, Exception exception) {
        return getWith(status, status, nameof, message, exception);
    }

    static Data<Boolean> getBoolean(boolean status, String message, Exception exception) {
        return getWith(status, status, message, exception);
    }

    static Data<Boolean> getBoolean(boolean status, String nameof, String message) {
        return getWith(status, status, nameof, message);
    }

    static Data<Boolean> getBoolean(boolean status, String message) {
        return getWith(status, status, message);
    }

    static Data<String> getString(String object, String message) {
        return getWith(object, isNotBlank(object), message);
    }

    static Data<String> getString(String object, String nameof, String message) {
        return getWith(object, isNotBlank(object), nameof, message);
    }

    static Data<String> getString(String object, String message, Exception exception, String exceptionMessage) {
        return getWith(object, isNotBlank(object), message, exception, exceptionMessage);
    }

    static Data<Object[]> getArrayWithName(Object[] object, boolean status, String nameof) {
        return CoreUtilities.areNotNull(object, nameof) ? (
            getWith(object, status, nameof, "Element was found. Array of length " + object.length + " was constructed" + CoreFormatterConstants.END_LINE)
        ) : CoreDataConstants.NULL_PARAMETER_ARRAY;
    }

    static Data<Object[]> getArrayWithName(Object[] object) {
        return NullableFunctions.isNotNull(object) ? (
            getWith(object, true, "getArrayWithName", "Element was found. Array of length " + object.length + " was constructed" + CoreFormatterConstants.END_LINE)
        ) : CoreDataConstants.NULL_PARAMETER_ARRAY;
    }
}
