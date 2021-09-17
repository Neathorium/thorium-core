package com.neathorium.thorium.core.records.wait.tasks;

import com.neathorium.thorium.core.records.wait.WaitData;
import com.neathorium.thorium.core.records.wait.WaitTimeData;
import com.neathorium.thorium.core.records.wait.tasks.common.WaitTaskCommonData;

public class VoidWaitData<U, ReturnType> extends WaitData<Void, U, ReturnType> {
    public VoidWaitData(WaitTaskCommonData<Void, U, ReturnType> taskData, String conditionMessage, WaitTimeData timeData) {
        super(taskData, conditionMessage, timeData);
    }
}
