package com.neathorium.thorium.core.abstracts.reflection;

import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;

import java.util.Objects;

public abstract class InvokeBaseMessageData {
    public final String MESSAGE;
    public final String RETURN_TYPE;
    public final String PARAMETER_TYPES;

    public InvokeBaseMessageData(String message, String returnType, String parameterTypes) {
        this.MESSAGE = message;
        this.RETURN_TYPE = returnType;
        this.PARAMETER_TYPES = parameterTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (NullableFunctions.isNull(o) || CoreUtilities.isNotEqual(getClass(), o.getClass())) {
            return false;
        }

        final var that = (InvokeBaseMessageData) o;
        return (
            CoreUtilities.isEqual(MESSAGE, that.MESSAGE) &&
            CoreUtilities.isEqual(RETURN_TYPE, that.RETURN_TYPE) &&
            CoreUtilities.isEqual(PARAMETER_TYPES, that.PARAMETER_TYPES)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(MESSAGE, RETURN_TYPE, PARAMETER_TYPES);
    }

    @Override
    public String toString() {
        return (
            "InvokeBaseMessageData{" +
            "message='" + MESSAGE + '\'' +
            ", returnType='" + RETURN_TYPE + '\'' +
            ", parameterTypes='" + PARAMETER_TYPES + '\'' +
            '}'
        );
    }
}
