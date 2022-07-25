package com.neathorium.thorium.core.abstracts.casting;

import com.neathorium.thorium.java.extensions.namespaces.predicates.EqualsPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;

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

        if (NullablePredicates.isNull(o) || EqualsPredicates.isNotEqual(getClass(), o.getClass())) {
            return false;
        }

        final var that = (AbstractCastData<?, ?>) o;
        return (
            EqualsPredicates.isEqual(DEFAULT_VALUE, that.DEFAULT_VALUE) &&
            EqualsPredicates.isEqual(CASTER, that.CASTER)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(DEFAULT_VALUE, CASTER);
    }
}
