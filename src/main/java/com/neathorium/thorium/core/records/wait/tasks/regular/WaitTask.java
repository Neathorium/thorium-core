package com.neathorium.thorium.core.records.wait.tasks.regular;

import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;
import com.neathorium.thorium.core.records.wait.tasks.common.WaitTaskCommonData;
import com.neathorium.thorium.core.records.wait.tasks.common.WaitTaskStateData;

import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;

public class WaitTask<T, U, V> {
    public final ScheduledExecutorService scheduler;
    public final WaitTaskCommonData<T, U, V> commonData;
    public WaitTaskStateData<T, V> stateData;

    public WaitTask(ScheduledExecutorService scheduler, WaitTaskCommonData<T, U, V> commonData, WaitTaskStateData<T, V> stateData) {
        this.scheduler = scheduler;
        this.commonData = commonData;
        this.stateData = stateData;
    }

    @Override
    public boolean equals(Object o) {
        if (CoreUtilities.isEqual(this, o)) {
            return true;
        }

        if (NullableFunctions.isNull(o) || CoreUtilities.isNotEqual(getClass(), o.getClass())) {
            return false;
        }

        final var that = (WaitTask<?, ?, ?>) o;
        return (
            CoreUtilities.isEqual(scheduler, that.scheduler) &&
            CoreUtilities.isEqual(commonData, that.commonData) &&
            CoreUtilities.isEqual(stateData, that.stateData)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduler, commonData, stateData);
    }
}
