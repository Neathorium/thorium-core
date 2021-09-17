package com.neathorium.thorium.core.records;

import com.neathorium.thorium.core.extensions.interfaces.functional.boilers.IGetMessage;
import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class SimpleMessageData implements IGetMessage {
    public final BiFunction<String, Boolean, String> FORMATTER;
    public final String MESSAGE;

    public SimpleMessageData(BiFunction<String, Boolean, String> formatter, String message) {
        this.FORMATTER = formatter;
        this.MESSAGE = message;
    }

    public SimpleMessageData(String message) {
        this(null, message);
    }

    public SimpleMessageData() {
        this(null, null);
    }

    @Override
    public Function<Boolean, String> get() {
        final var isFormatterNull = NullableFunctions.isNull(FORMATTER);
        final var isMessageBlank = isBlank(MESSAGE);
        if (isFormatterNull && isMessageBlank) {
            return CoreFormatter.isFormatterNullAndMessageBlank();
        }

        if (isFormatterNull) {
            return CoreFormatter.isFormatterNull(MESSAGE);
        }

        if (isMessageBlank) {
            return CoreFormatter.isMessageBlank(FORMATTER);
        }

        return CoreFormatter.isFormatterAndMessageValid(FORMATTER, MESSAGE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (NullableFunctions.isNull(o) || CoreUtilities.isNotEqual(getClass(), o.getClass())) {
            return false;
        }

        final var that = (SimpleMessageData) o;
        return (
            Objects.equals(FORMATTER, that.FORMATTER) &&
            Objects.equals(MESSAGE, that.MESSAGE)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(FORMATTER, MESSAGE);
    }

    @Override
    public String toString() {
        return (
            "SimpleMessageData{" +
            "FORMATTER=" + FORMATTER +
            ", MESSAGE='" + MESSAGE + '\'' +
            '}'
        );
    }
}
