package com.neathorium.thorium.core.wait.records.tasks;

import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.data.interfaces.DataSupplier;
import com.neathorium.thorium.core.records.executor.ExecutionResultData;
import com.neathorium.thorium.core.records.executor.ExecutionStateData;
import com.neathorium.thorium.core.wait.records.tasks.common.WaitTaskCommonData;
import com.neathorium.thorium.core.wait.records.tasks.common.WaitTaskStateData;
import com.neathorium.thorium.core.wait.records.tasks.WaitTask;

import java.util.concurrent.ScheduledExecutorService;

public final class WaitRepeatTask<V> extends WaitTask<ExecutionStateData, DataSupplier<ExecutionResultData<V>>, Data<ExecutionResultData<V>>> {
    public WaitRepeatTask(
        ScheduledExecutorService scheduler,
        WaitTaskCommonData<ExecutionStateData, DataSupplier<ExecutionResultData<V>>, Data<ExecutionResultData<V>>> commonData,
        WaitTaskStateData<ExecutionStateData, Data<ExecutionResultData<V>>> stateData
    ) {
        super(scheduler, commonData, stateData);
    }
}
