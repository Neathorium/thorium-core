package com.neathorium.thorium.core.records.wait;

import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;
import com.neathorium.thorium.core.records.wait.tasks.common.WaitTaskCommonData;

import java.util.Objects;

public class WaitData<T, U, V> {
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
        if (CoreUtilities.isEqual(this, o)) {
            return true;
        }

        if (NullableFunctions.isNull(o) || CoreUtilities.isNotEqual(getClass(), o.getClass())) {
            return false;
        }

        final var that = (WaitData<?, ?, ?>) o;
        return (
            CoreUtilities.isEqual(taskData, that.taskData) &&
            CoreUtilities.isEqual(conditionMessage, that.conditionMessage) &&
            CoreUtilities.isEqual(timeData, that.timeData)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskData, conditionMessage, timeData);
    }
}
