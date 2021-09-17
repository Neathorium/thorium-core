package com.neathorium.thorium.core.implementations.reflection;

import com.neathorium.thorium.core.extensions.interfaces.functional.boilers.MethodFunction;
import com.neathorium.thorium.core.namespaces.InvokeFunctions;
import com.neathorium.thorium.core.records.reflection.InvokerParameterizedParametersFieldData;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Function;

public class InvokerParameterizedData<ParameterType> implements MethodFunction<Function<ParameterType, Object>> {
    public final InvokerParameterizedParametersFieldData<ParameterType> parameterData;

    public InvokerParameterizedData(InvokerParameterizedParametersFieldData<ParameterType> parameterData) {
        this.parameterData = parameterData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (InvokerParameterizedData<?>) o;
        return Objects.equals(parameterData, that.parameterData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameterData);
    }

    @Override
    public Function<ParameterType, Object> apply(Method method) {
        if (NullableFunctions.isNull(method)) {
            //TODO: Data message.
            return InvokeFunctions.regularDefault();
        }

        final var parameters = parameterData.parameters;
        if (!parameterData.validator.test(parameters)) {
            // TODO: Data message.
            //throw new InvalidParameterException("Data parameter value field(s) didn't pass validation" + Strings.END_LINE);
            return InvokeFunctions.regularDefault();
        }

        return base -> parameterData.handler.apply(method, base, parameters);
    }
}
