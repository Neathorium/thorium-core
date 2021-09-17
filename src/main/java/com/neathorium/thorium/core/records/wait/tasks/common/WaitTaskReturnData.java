package com.neathorium.thorium.core.records.wait.tasks.common;

import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;

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
        if (CoreUtilities.isEqual(this, o)) {
            return true;
        }

        if (NullableFunctions.isNull(o) || CoreUtilities.isNotEqual(getClass(), o.getClass())) {
            return false;
        }

        final var that = (WaitTaskReturnData<?>) o;
        return CoreUtilities.isEqual(status, that.status) && CoreUtilities.isEqual(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, status);
    }
}
