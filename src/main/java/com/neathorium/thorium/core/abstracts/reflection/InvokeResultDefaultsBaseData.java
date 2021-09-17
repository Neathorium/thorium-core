package com.neathorium.thorium.core.abstracts.reflection;

import com.neathorium.thorium.core.records.reflection.message.InvokeCommonMessageParametersData;

import java.util.Objects;
import java.util.function.Function;

public abstract class InvokeResultDefaultsBaseData<CastParameterType, ReturnType> {
    public final Function<Exception, Function<InvokeCommonMessageParametersData, String>> messageHandler;
    public final Function<CastParameterType, ReturnType> castHandler;

    public InvokeResultDefaultsBaseData(Function<Exception, Function<InvokeCommonMessageParametersData, String>> messageHandler, Function<CastParameterType, ReturnType> castHandler) {
        this.messageHandler = messageHandler;
        this.castHandler = castHandler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (InvokeResultDefaultsBaseData<?, ?>) o;
        return Objects.equals(messageHandler, that.messageHandler) && Objects.equals(castHandler, that.castHandler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageHandler, castHandler);
    }
}