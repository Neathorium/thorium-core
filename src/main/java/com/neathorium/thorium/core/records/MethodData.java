package com.neathorium.thorium.core.records;

import java.lang.reflect.Method;
import java.util.Objects;

public class MethodData {
    public final Method method;
    public final String methodParameterTypes;
    public final String returnType;

    public MethodData(Method method, String methodParameterTypes, String returnType) {
        this.method = method;
        this.methodParameterTypes = methodParameterTypes;
        this.returnType = returnType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (MethodData) o;
        return Objects.equals(method, that.method) && Objects.equals(methodParameterTypes, that.methodParameterTypes) && Objects.equals(returnType, that.returnType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, methodParameterTypes, returnType);
    }
}
