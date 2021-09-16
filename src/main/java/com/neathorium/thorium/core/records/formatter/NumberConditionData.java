package com.neathorium.thorium.core.records.formatter;

import java.util.Objects;
import java.util.function.BiPredicate;

public class NumberConditionData {
    public final String nameof;
    public final String descriptor;
    public final String parameterName;
    public final BiPredicate<Integer, Integer> function;

    public NumberConditionData(String nameof, String descriptor, String parameterName, BiPredicate<Integer, Integer> function) {
        this.nameof = nameof;
        this.descriptor = descriptor;
        this.parameterName = parameterName;
        this.function = function;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (NumberConditionData) o;
        return (
            Objects.equals(nameof, that.nameof) &&
            Objects.equals(descriptor, that.descriptor) &&
            Objects.equals(parameterName, that.parameterName) &&
            Objects.equals(function, that.function)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameof, descriptor, parameterName, function);
    }
}
