package com.neathorium.thorium.core.records;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MethodSourceData {
    public final HashMap<String, MethodData> methodMap;
    public final List<Method> list;
    public final Data<MethodData> defaultValue;

    public MethodSourceData(HashMap<String, MethodData> methodMap, List<Method> list, Data<MethodData> defaultValue) {
        this.methodMap = methodMap;
        this.list = list;
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (MethodSourceData) o;
        return Objects.equals(methodMap, that.methodMap) && Objects.equals(list, that.list) && Objects.equals(defaultValue, that.defaultValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(methodMap, list, defaultValue);
    }
}
