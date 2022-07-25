package com.neathorium.thorium.core.wait.records;

import com.neathorium.thorium.core.wait.records.tasks.common.WaitTaskCommonData;

public final class VoidWaitData<U, ReturnType> extends WaitData<Void, U, ReturnType> {
    public VoidWaitData(WaitTaskCommonData<Void, U, ReturnType> taskData, String conditionMessage, WaitTimeData timeData) {
        super(taskData, conditionMessage, timeData);
    }
}
