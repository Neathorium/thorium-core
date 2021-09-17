package com.neathorium.thorium.core.abstracts;

import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;

import java.util.Objects;
import java.util.function.Function;

public abstract class AbstractCastData<T, U> {
    public final T DEFAULT_VALUE;
    public final Function<Object, U> CASTER;

    public AbstractCastData(T defaultValue, Function<Object, U> caster) {
        this.DEFAULT_VALUE = defaultValue;
        this.CASTER = caster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (NullableFunctions.isNull(o) || CoreUtilities.isNotEqual(getClass(), o.getClass())) {
            return false;
        }

        final var that = (AbstractCastData<?, ?>) o;
        return (
            CoreUtilities.isEqual(DEFAULT_VALUE, that.DEFAULT_VALUE) &&
            CoreUtilities.isEqual(CASTER, that.CASTER)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(DEFAULT_VALUE, CASTER);
    }
}
