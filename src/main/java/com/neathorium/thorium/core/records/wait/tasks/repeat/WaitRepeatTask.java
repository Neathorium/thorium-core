package com.neathorium.thorium.core.records.wait.tasks.repeat;

import com.neathorium.thorium.core.extensions.interfaces.functional.boilers.DataSupplier;
import com.neathorium.thorium.core.records.Data;
import com.neathorium.thorium.core.records.executor.ExecutionResultData;
import com.neathorium.thorium.core.records.executor.ExecutionStateData;
import com.neathorium.thorium.core.records.wait.tasks.regular.WaitTask;
import com.neathorium.thorium.core.records.wait.tasks.common.WaitTaskCommonData;
import com.neathorium.thorium.core.records.wait.tasks.common.WaitTaskStateData;

import java.util.concurrent.ScheduledExecutorService;

public class WaitRepeatTask<V> extends WaitTask<ExecutionStateData, DataSupplier<ExecutionResultData<V>>, Data<ExecutionResultData<V>>> {
    public WaitRepeatTask(
        ScheduledExecutorService scheduler,
        WaitTaskCommonData<ExecutionStateData, DataSupplier<ExecutionResultData<V>>, Data<ExecutionResultData<V>>> commonData,
        WaitTaskStateData<ExecutionStateData, Data<ExecutionResultData<V>>> stateData
    ) {
        super(scheduler, commonData, stateData);
    }
}
