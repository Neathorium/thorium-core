package com.neathorium.thorium.core.wait.records.tasks;

import com.neathorium.thorium.core.wait.records.tasks.common.WaitTaskCommonData;
import com.neathorium.thorium.core.wait.records.tasks.common.WaitTaskStateData;
import com.neathorium.thorium.java.extensions.namespaces.predicates.EqualsPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;

import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;

public sealed class WaitTask<T, U, V> permits WaitRepeatTask {
    public final ScheduledExecutorService SCHEDULER;
    public final WaitTaskCommonData<T, U, V> COMMON_DATA;
    public WaitTaskStateData<T, V> STATE_DATA;

    public WaitTask(ScheduledExecutorService scheduler, WaitTaskCommonData<T, U, V> commonData, WaitTaskStateData<T, V> stateData) {
        this.SCHEDULER = scheduler;
        this.COMMON_DATA = commonData;
        this.STATE_DATA = stateData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (NullablePredicates.isNull(o) || EqualsPredicates.isNotEqual(getClass(), o.getClass())) {
            return false;
        }

        final var that = (WaitTask<?, ?, ?>) o;
        return (
            EqualsPredicates.isEqual(SCHEDULER, that.SCHEDULER) &&
            EqualsPredicates.isEqual(COMMON_DATA, that.COMMON_DATA) &&
            EqualsPredicates.isEqual(STATE_DATA, that.STATE_DATA)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(SCHEDULER, COMMON_DATA, STATE_DATA);
    }

    @Override
    public String toString() {
        return (
            "WaitTask{" +
            "SCHEDULER=" + SCHEDULER +
            ", COMMON_DATA=" + COMMON_DATA +
            ", STATE_DATA=" + STATE_DATA +
            '}'
        );
    }
}
