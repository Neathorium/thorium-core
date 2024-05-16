package com.neathorium.thorium.core.wait.records.tasks;

import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.data.interfaces.DataSupplier;
import com.neathorium.thorium.core.records.executor.ExecutionResultData;
import com.neathorium.thorium.core.records.executor.ExecutionStateData;
import com.neathorium.thorium.core.wait.interfaces.IWaitTask;
import com.neathorium.thorium.core.wait.records.tasks.common.WaitTaskCommonData;
import com.neathorium.thorium.core.wait.records.tasks.common.WaitTaskStateData;


import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicReference;

public record WaitRepeatTask<ConditionType>(
    ScheduledExecutorService SCHEDULER,
    WaitTaskCommonData<ExecutionStateData, DataSupplier<ExecutionResultData<ConditionType>>, Data<ExecutionResultData<ConditionType>>> COMMON_DATA,
    AtomicReference<WaitTaskStateData<ExecutionStateData, Data<ExecutionResultData<ConditionType>>>> STATE_DATA
) implements IWaitTask<ExecutionStateData, DataSupplier<ExecutionResultData<ConditionType>>, Data<ExecutionResultData<ConditionType>>> {}
