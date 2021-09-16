package com.neathorium.thorium.core.records.command;

import com.neathorium.thorium.core.extensions.interfaces.functional.TriFunction;
import com.neathorium.thorium.core.namespaces.validators.Range;

import java.util.Objects;

public class CommandRangeData {
    public final TriFunction<Integer, Integer, Integer, Boolean> rangeInvalidator;
    public final int min;
    public final int max;

    public CommandRangeData(TriFunction<Integer, Integer, Integer, Boolean> rangeInvalidator, int min, int max) {
        this.rangeInvalidator = rangeInvalidator;
        this.min = min;
        this.max = max;
    }

    public CommandRangeData(int min, int max) {
        this.rangeInvalidator = Range::isOutOfRange;
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (CommandRangeData) o;
        return min == that.min && max == that.max && Objects.equals(rangeInvalidator, that.rangeInvalidator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rangeInvalidator, min, max);
    }
}
