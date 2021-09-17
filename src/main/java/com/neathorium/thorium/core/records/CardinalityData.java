package com.neathorium.thorium.core.records;

import java.util.Objects;

public class CardinalityData {
    public final boolean finalValue;
    public final boolean guardValue;
    public final boolean invert;

    public CardinalityData(boolean finalValue, boolean guardValue, boolean invert) {
        this.finalValue = finalValue;
        this.guardValue = guardValue;
        this.invert = invert;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (CardinalityData) o;
        return finalValue == that.finalValue && guardValue == that.guardValue && invert == that.invert;
    }

    @Override
    public int hashCode() {
        return Objects.hash(finalValue, guardValue, invert);
    }
}
