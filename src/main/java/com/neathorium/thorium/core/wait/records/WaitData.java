package com.neathorium.thorium.core.wait.records;

import com.neathorium.thorium.core.wait.records.tasks.common.WaitTaskCommonData;
import com.neathorium.thorium.java.extensions.namespaces.predicates.EqualsPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;

import java.util.Objects;

public sealed class WaitData<T, U, V> permits VoidWaitData {
    public final WaitTaskCommonData<T, U, V> taskData;
    public final String conditionMessage;
    public final WaitTimeData timeData;

    public WaitData(WaitTaskCommonData<T, U, V> taskData, String conditionMessage, WaitTimeData timeData) {
        this.taskData = taskData;
        this.conditionMessage = conditionMessage;
        this.timeData = timeData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (NullablePredicates.isNull(o) || EqualsPredicates.isNotEqual(getClass(), o.getClass())) {
            return false;
        }

        final var that = (WaitData<?, ?, ?>) o;
        return (
            EqualsPredicates.isEqual(taskData, that.taskData) &&
            EqualsPredicates.isEqual(conditionMessage, that.conditionMessage) &&
            EqualsPredicates.isEqual(timeData, that.timeData)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskData, conditionMessage, timeData);
    }

    @Override
    public String toString() {
        return (
            "WaitData{" +
            "taskData=" + taskData +
            ", conditionMessage='" + conditionMessage + '\'' +
            ", timeData=" + timeData +
            '}'
        );
    }
}
