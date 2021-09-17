package com.neathorium.thorium.core.records.reflection.message;

import com.neathorium.thorium.core.abstracts.reflection.InvokeBaseMessageData;

import java.util.Objects;

public class InvokeCommonMessageParametersData extends InvokeBaseMessageData {
    public InvokeCommonMessageParametersData(String message, String returnType, String parameterTypes) {
        super(message, returnType, parameterTypes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (InvokeCommonMessageParametersData) o;
        return Objects.equals(MESSAGE, that.MESSAGE) && Objects.equals(RETURN_TYPE, that.RETURN_TYPE) && Objects.equals(PARAMETER_TYPES, that.PARAMETER_TYPES);
    }

    @Override
    public int hashCode() {
        return Objects.hash(MESSAGE, RETURN_TYPE, PARAMETER_TYPES);
    }
}
