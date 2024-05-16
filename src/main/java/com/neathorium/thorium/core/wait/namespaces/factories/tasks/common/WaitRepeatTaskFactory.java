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
import java.util.concurrent.atomic.AtomicReference;

public interface WaitRepeatTaskFactory {
    static <ReturnType> WaitRepeatTask<ReturnType> getWith(
        ScheduledExecutorService scheduler,
        WaitTaskCommonData<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>, Data<ExecutionResultData<ReturnType>>> commonData,
        AtomicReference<WaitTaskStateData<ExecutionStateData, Data<ExecutionResultData<ReturnType>>>> stateData
    ) {
        return new WaitRepeatTask<>(scheduler, commonData, stateData);
    }

    static <ReturnType> WaitRepeatTask<ReturnType> getWithDefaultScheduler(
        WaitTaskCommonData<ExecutionStateData, DataSupplier<ExecutionResultData<ReturnType>>, Data<ExecutionResultData<ReturnType>>> commonData,
        AtomicReference<WaitTaskStateData<ExecutionStateData, Data<ExecutionResultData<ReturnType>>>> stateData
    ) {
        return WaitRepeatTaskFactory.getWith(Executors.newSingleThreadScheduledExecutor(), commonData, stateData);
    }

    static <ReturnType> WaitRepeatTask<ReturnType> replaceStateData(
        WaitRepeatTask<ReturnType> task,
        AtomicReference<WaitTaskStateData<ExecutionStateData, Data<ExecutionResultData<ReturnType>>>> data
    ) {
        return WaitRepeatTaskFactory.getWith(task.SCHEDULER(), task.COMMON_DATA(), data);
    }
}
