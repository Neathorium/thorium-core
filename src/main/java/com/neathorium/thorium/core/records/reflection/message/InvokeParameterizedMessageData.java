package com.neathorium.thorium.core.records.reflection.message;

import com.neathorium.thorium.core.abstracts.reflection.InvokeBaseMessageData;

import java.util.Objects;

public class InvokeParameterizedMessageData extends InvokeBaseMessageData {
    public final String parameter;

    public InvokeParameterizedMessageData(String message, String returnType, String parameterTypes, String parameter) {
        super(message, returnType, parameterTypes);
        this.parameter = parameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (getClass() != o.getClass()) || !super.equals(o)) return false;
        final var that = (InvokeParameterizedMessageData) o;
        return Objects.equals(parameter, that.parameter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), parameter);
    }
}
