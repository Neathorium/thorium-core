package com.neathorium.thorium.core.wait.interfaces;

import com.neathorium.thorium.core.wait.records.WaitTimeData;
import com.neathorium.thorium.core.wait.records.tasks.common.WaitTaskCommonData;

public interface IWaitData<DependencyType, ReturnType, ConditionType> {
    WaitTaskCommonData<DependencyType, ReturnType, ConditionType> TASK_DATA();
    String CONDITION_MESSAGE();
    WaitTimeData TIME_DATA();
}
