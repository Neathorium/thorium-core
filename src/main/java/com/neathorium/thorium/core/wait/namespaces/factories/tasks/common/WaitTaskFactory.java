package com.neathorium.thorium.core.wait.namespaces.factories.tasks.common;

import com.neathorium.thorium.core.wait.records.tasks.common.WaitTaskCommonData;
import com.neathorium.thorium.core.wait.records.tasks.common.WaitTaskStateData;
import com.neathorium.thorium.core.wait.records.tasks.WaitTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public interface WaitTaskFactory {
    static <T, U, V> WaitTask<T, U, V> getWith(ScheduledExecutorService scheduler, WaitTaskCommonData<T, U, V> commonData, WaitTaskStateData<T, V> stateData) {
        return new WaitTask<>(scheduler, commonData, stateData);
    }

    static <T, U, V> WaitTask<T, U, V> getWithDefaultScheduler(WaitTaskCommonData<T, U, V> commonData, WaitTaskStateData<T, V> stateData) {
        return getWith(Executors.newSingleThreadScheduledExecutor(), commonData, stateData);
    }
}
