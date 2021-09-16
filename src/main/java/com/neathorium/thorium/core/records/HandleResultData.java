package com.neathorium.thorium.core.records;

import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;

import java.util.Objects;
import java.util.function.Function;

public class HandleResultData<ParameterType, ReturnType> {
    public final Function<ParameterType, ReturnType> caster;
    public final ParameterType parameter;
    public final ReturnType defaultValue;

    public HandleResultData(Function<ParameterType, ReturnType> caster, ParameterType parameter, ReturnType defaultValue) {
        this.caster = caster;
        this.parameter = parameter;
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean equals(Object o) {
        if (CoreUtilities.isEqual(this, o)) {
            return true;
        }

        if (NullableFunctions.isNull(o) || CoreUtilities.isNotEqual(getClass(), o.getClass())) {
            return false;
        }

        final var that = (HandleResultData<?, ?>) o;
        return (
            CoreUtilities.isEqual(caster, that.caster) &&
            CoreUtilities.isEqual(parameter, that.parameter) &&
            CoreUtilities.isEqual(defaultValue, that.defaultValue)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(caster, parameter, defaultValue);
    }
}
