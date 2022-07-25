package com.neathorium.thorium.core.wait.records.tasks.common;

import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.java.extensions.namespaces.predicates.EqualsPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;

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
        if (this == o) {
            return true;
        }

        if (NullablePredicates.isNull(o) || EqualsPredicates.isNotEqual(getClass(), o.getClass())) {
            return false;
        }

        final var that = (WaitTaskStateData<?, ?>) o;
        return (
            EqualsPredicates.isEqual(limit, that.limit) &&
            EqualsPredicates.isEqual(data, that.data) &&
            EqualsPredicates.isEqual(dependency, that.dependency) &&
            EqualsPredicates.isEqual(counter, that.counter)
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
