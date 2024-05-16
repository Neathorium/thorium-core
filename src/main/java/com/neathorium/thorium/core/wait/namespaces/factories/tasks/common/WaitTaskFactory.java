package com.neathorium.thorium.core.wait.namespaces.factories.tasks.common;

import com.neathorium.thorium.core.wait.records.tasks.common.WaitTaskCommonData;
import com.neathorium.thorium.core.wait.records.tasks.common.WaitTaskStateData;
import com.neathorium.thorium.core.wait.records.tasks.WaitTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicReference;

public interface WaitTaskFactory {
    static <T, U, V> WaitTask<T, U, V> getWith(ScheduledExecutorService scheduler, WaitTaskCommonData<T, U, V> commonData, AtomicReference<WaitTaskStateData<T, V>> stateData) {
        return new WaitTask<>(scheduler, commonData, stateData);
    }

    static <T, U, V> WaitTask<T, U, V> getWithDefaultScheduler(WaitTaskCommonData<T, U, V> commonData, AtomicReference<WaitTaskStateData<T, V>> stateData) {
        final var factory = Thread.ofVirtual().factory();
        final var scheduledExecutorService = Executors.newScheduledThreadPool(0, factory);
        return WaitTaskFactory.getWith(scheduledExecutorService, commonData, stateData);
    }

    static <T, U, V> WaitTask<T, U, V> replaceStateData(
        WaitTask<T, U, V>  task,
        AtomicReference<WaitTaskStateData<T, V>> data
    ) {
        return WaitTaskFactory.getWith(task.SCHEDULER(), task.COMMON_DATA(), data);
    }
}
