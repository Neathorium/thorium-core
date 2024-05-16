package com.neathorium.thorium.core.namespaces.factories;

import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;
import com.neathorium.thorium.core.records.SimpleMessageData;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;
import org.apache.commons.lang.StringUtils;

import java.util.function.BiFunction;

public interface MessageDataFactory {
    private static SimpleMessageData getWithCore(
        BiFunction<BiFunction<String, Boolean, String>, String, SimpleMessageData> constructor,
        BiFunction<String, Throwable, IllegalArgumentException> exceptionConstructor,
        BiFunction<String, Boolean, String> formatter,
        String message
    ) {
        final var errors = (
            CoreFormatter.isNullMessageWithName(constructor, "Constructor") +
            CoreFormatter.isNullMessageWithName(exceptionConstructor, "Exception Constructor") +
            CoreFormatter.isNullMessageWithName(formatter, "Formatter") +
            CoreFormatter.isNullMessageWithName(message, "Message")
        );
        var localExceptionConstructor = exceptionConstructor;
        if (NullablePredicates.isNull(exceptionConstructor)) {
            localExceptionConstructor = IllegalArgumentException::new;
        }
        if (StringUtils.isNotBlank(errors)) {
            throw localExceptionConstructor.apply(errors, null);
        }

        return constructor.apply(formatter, message);
    }

    static SimpleMessageData getWith(
        BiFunction<String, Boolean, String> formatter,
        String message
    ) {
        return MessageDataFactory.getWithCore(SimpleMessageData::new, IllegalArgumentException::new, formatter, message);
    }
}
