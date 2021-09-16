package com.neathorium.thorium.core.records.wait.tasks.common;

import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;
import com.neathorium.thorium.core.records.Data;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class WaitTaskStateData<T, V> {
    public Data<V> data;
    public T dependency;
    public AtomicInteger counter;
    public int limit;

    public WaitTaskStateData(Data<V> data, T dependency, AtomicInteger counter, int limit) {
        this.data = data;
        this.dependency = dependency;
        this.counter = counter;
        this.limit = limit;
    }

    @Override
    public boolean equals(Object o) {
        if (CoreUtilities.isEqual(this, o)) {
            return true;
        }

        if (NullableFunctions.isNull(o) || CoreUtilities.isNotEqual(getClass(), o.getClass())) {
            return false;
        }

        final var that = (WaitTaskStateData<?, ?>) o;
        return (
            CoreUtilities.isEqual(limit, that.limit) &&
            CoreUtilities.isEqual(data, that.data) &&
            CoreUtilities.isEqual(dependency, that.dependency) &&
            CoreUtilities.isEqual(counter, that.counter)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, dependency, counter, limit);
    }

    @Override
    public String toString() {
        return (
            "WaitTaskStateData{" +
            "data=" + data +
            ", dependency=" + dependency +
            ", times=" + counter +
            ", limit=" + limit +
            '}'
        );
    }
}
