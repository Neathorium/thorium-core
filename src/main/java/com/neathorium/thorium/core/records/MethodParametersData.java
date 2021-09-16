package com.neathorium.thorium.core.records;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.BiPredicate;

public class MethodParametersData {
    public final String methodName;
    public final BiPredicate<Method, String> validator;
    public final MethodSourceData sourceData;

    public MethodParametersData(String methodName, BiPredicate<Method, String> validator, MethodSourceData sourceData) {
        this.methodName = methodName;
        this.validator = validator;
        this.sourceData = sourceData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (MethodParametersData) o;
        return Objects.equals(methodName, that.methodName) && Objects.equals(validator, that.validator) && Objects.equals(sourceData, that.sourceData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(methodName, validator, sourceData);
    }
}
