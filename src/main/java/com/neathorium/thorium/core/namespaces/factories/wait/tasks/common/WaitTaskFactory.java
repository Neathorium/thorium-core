package com.neathorium.thorium.core.namespaces.factories.wait.tasks.common;

import com.neathorium.thorium.core.records.wait.tasks.common.WaitTaskCommonData;
import com.neathorium.thorium.core.records.wait.tasks.common.WaitTaskStateData;
import com.neathorium.thorium.core.records.wait.tasks.regular.WaitTask;

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
