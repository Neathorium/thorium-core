package com.neathorium.thorium.core.abstracts.reflection;

import java.util.Objects;
import java.util.function.Predicate;

public abstract class InvokerBaseFunctionalData<HandlerType, ConstructorResultType> {
    public final ConstructorResultType constructor;
    public final Predicate<HandlerType> guard;

    public InvokerBaseFunctionalData(
        ConstructorResultType constructor,
        Predicate<HandlerType> guard
    ) {
        this.constructor = constructor;
        this.guard = guard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (InvokerBaseFunctionalData<?, ?>) o;
        return Objects.equals(constructor, that.constructor) && Objects.equals(guard, that.guard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(constructor, guard);
    }
}

