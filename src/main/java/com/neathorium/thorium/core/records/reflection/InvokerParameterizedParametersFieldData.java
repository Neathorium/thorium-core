package com.neathorium.thorium.core.records.reflection;

import com.neathorium.thorium.core.extensions.interfaces.functional.TriFunction;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

public class InvokerParameterizedParametersFieldData<ParameterType> {
    public final Object[] parameters;
    public final Predicate<Object[]> validator;
    public final TriFunction<Method, ParameterType, Object[], Object> handler;

    public InvokerParameterizedParametersFieldData(Object[] parameters, Predicate<Object[]> validator, TriFunction<Method, ParameterType, Object[], Object> handler) {
        this.parameters = parameters;
        this.validator = validator;
        this.handler = handler;
    }

    public InvokerParameterizedParametersFieldData(Object[] parameters, InvokeParametersFieldDefaultsData<ParameterType> data) {
        this(parameters, data.validator, data.handler);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (InvokerParameterizedParametersFieldData<?>) o;
        return Arrays.equals(parameters, that.parameters) && Objects.equals(validator, that.validator) && Objects.equals(handler, that.handler);
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(validator, handler) + Arrays.hashCode(parameters);
    }
}
