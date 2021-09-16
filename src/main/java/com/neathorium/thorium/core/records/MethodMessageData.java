package com.neathorium.thorium.core.records;

import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;

import java.util.Objects;
import java.util.function.BiFunction;

public class MethodMessageData {
    public final BiFunction<String, String, String> formatter;
    public final String nameof;
    public final String message;

    public MethodMessageData(BiFunction<String, String, String> formatter, String nameof, String message) {
        this.formatter = formatter;
        this.nameof = nameof;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (CoreUtilities.isEqual(this, o)) {
            return true;
        }

        if (NullableFunctions.isNull(o) || CoreUtilities.isNotEqual(getClass(), o.getClass())) {
            return false;
        }

        final var that = (MethodMessageData) o;
        return (
            CoreUtilities.isEqual(formatter, that.formatter) &&
            CoreUtilities.isEqual(nameof, that.nameof) &&
            CoreUtilities.isEqual(message, that.message)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(formatter, nameof, message);
    }

    @Override
    public String toString() {
        return (
            "MethodMessageData{" +
            "formatter=" + formatter +
            ", nameof='" + nameof + '\'' +
            ", message='" + message + '\'' +
            '}'
        );
    }
}
