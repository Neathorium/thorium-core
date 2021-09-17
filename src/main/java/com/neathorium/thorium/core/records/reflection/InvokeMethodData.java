package com.neathorium.thorium.core.records.reflection;

import com.neathorium.thorium.core.records.MethodParametersData;

import java.util.Objects;

public class InvokeMethodData {
    public final MethodParametersData parametersData;
    public final String nameof;

    public InvokeMethodData(MethodParametersData parametersData, String nameof) {
        this.parametersData = parametersData;
        this.nameof = nameof;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (InvokeMethodData) o;
        return Objects.equals(parametersData, that.parametersData) && Objects.equals(nameof, that.nameof);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parametersData, nameof);
    }
}
