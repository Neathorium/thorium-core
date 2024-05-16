package com.neathorium.thorium.core.wait.records;

import com.neathorium.thorium.core.wait.interfaces.IWaitData;
import com.neathorium.thorium.core.wait.records.tasks.common.WaitTaskCommonData;

public record WaitData<DependencyType, ReturnType, ConditionType>(
    WaitTaskCommonData<DependencyType, ReturnType, ConditionType> TASK_DATA,
    String CONDITION_MESSAGE,
    WaitTimeData TIME_DATA
) implements IWaitData<DependencyType, ReturnType, ConditionType> {}
