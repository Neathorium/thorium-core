package com.neathorium.thorium.core.records;

import java.util.Objects;
import java.util.function.Predicate;

public class ExecuteCommonData<T> {
    public final T parameter;
    public final Predicate<T> validator;

    public ExecuteCommonData(T parameter, Predicate<T> validator) {
        this.parameter = parameter;
        this.validator = validator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (ExecuteCommonData<?>) o;
        return Objects.equals(parameter, that.parameter) && Objects.equals(validator, that.validator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameter, validator);
    }
}
