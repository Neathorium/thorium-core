package com.neathorium.thorium.core.namespaces.factories.wait.tasks.common;

import com.neathorium.thorium.core.records.Data;
import com.neathorium.thorium.core.records.wait.tasks.common.WaitTaskStateData;

import java.util.concurrent.atomic.AtomicInteger;

public interface WaitTaskStateDataFactory {
    static <T, V> WaitTaskStateData<T, V> getWith(Data<V> data, T dependency, AtomicInteger counter, int limit) {
        return new WaitTaskStateData<>(data, dependency, counter, limit);
    }

    static <T, V> WaitTaskStateData<T, V> getWithDefaultLimit(Data<V> data, T dependency, AtomicInteger counter) {
        return getWith(data, dependency, counter, 1);
    }

    static <T, V> WaitTaskStateData<T, V> getWithDefaultCounter(Data<V> data, T dependency, int limit) {
        return getWith(data, dependency, new AtomicInteger(), limit);
    }

    static <T, V> WaitTaskStateData<T, V> getWithDefaultCounterAndLimit(Data<V> data, T dependency) {
        return getWithDefaultCounter(data, dependency, 1);
    }

    static <T, V> WaitTaskStateData<T, V> getWithNoRepeating(Data<V> data, T dependency) {
        return getWithDefaultCounter(data, dependency, -1);
    }

    static <T, V> WaitTaskStateData<T, V> replaceData(WaitTaskStateData<T, V> stateData, Data<V> data) {
        return getWith(data, stateData.dependency, stateData.counter, stateData.limit);
    }

    static <T, V> WaitTaskStateData<T, V> replaceDataAndDependency(WaitTaskStateData<T, V> stateData, Data<V> data, T dependency) {
        return getWith(data, dependency, stateData.counter, stateData.limit);
    }
}
