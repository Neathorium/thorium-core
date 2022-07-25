package com.neathorium.thorium.core.wait.namespaces.factories.tasks.common;

import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.data.interfaces.DataSupplier;
import com.neathorium.thorium.core.records.executor.ExecutionResultData;
import com.neathorium.thorium.core.records.executor.ExecutionStateData;
import com.neathorium.thorium.core.wait.records.tasks.common.WaitTaskCommonData;
import com.neathorium.thorium.core.wait.records.tasks.common.WaitTaskStateData;
import com.neathorium.thorium.core.wait.records.tasks.WaitRepeatTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public interface WaitRepeatTaskFactory {
    static <ReturnType> WaitRepeatTask<ReturnType> getWith(
        ScheduledExecutorService scheduler,
        WaitTaskCommonData<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>, Data<ExecutionResultData<ReturnType>>> commonData,
        WaitTaskStateData<ExecutionStateData, Data<ExecutionResultData<ReturnType>>> stateData
    ) {
        return new WaitRepeatTask<>(scheduler, commonData, stateData);
    }

    static <ReturnType> WaitRepeatTask<ReturnType> getWithDefaultScheduler(
        WaitTaskCommonData<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>, Data<ExecutionResultData<ReturnType>>> commonData,
        WaitTaskStateData<ExecutionStateData, Data<ExecutionResultData<ReturnType>>> stateData
    ) {
        return getWith(Executors.newSingleThreadScheduledExecutor(), commonData, stateData);
    }
}
