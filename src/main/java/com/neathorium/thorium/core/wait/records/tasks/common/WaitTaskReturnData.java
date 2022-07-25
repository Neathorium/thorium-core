package com.neathorium.thorium.core.wait.records.tasks.common;



import com.neathorium.thorium.java.extensions.namespaces.predicates.EqualsPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;

import java.util.Objects;

public class WaitTaskReturnData<V> {
    public final V result;
    public final boolean status;

    public WaitTaskReturnData(V result, boolean status) {
        this.result = result;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (NullablePredicates.isNull(o) || EqualsPredicates.isNotEqual(getClass(), o.getClass())) {
            return false;
        }

        final var that = (WaitTaskReturnData<?>) o;
        return (
            EqualsPredicates.isEqual(status, that.status) &&
            EqualsPredicates.isEqual(result, that.result)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, status);
    }

    @Override
    public String toString() {
        return (
            "WaitTaskReturnData{" +
            "result=" + result +
            ", status=" + status +
            '}'
        );
    }
}
