package com.neathorium.thorium.core.wait.namespaces.factories.tasks.common;

import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.wait.records.tasks.common.WaitTaskStateData;
import com.neathorium.thorium.java.extensions.interfaces.functional.QuadFunction;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

public interface WaitTaskStateDataFactory {
    private static <T, V> WaitTaskStateData<T, V> getWithCore(
        QuadFunction<Data<V>, T, AtomicInteger, Integer, WaitTaskStateData<T, V>> constructor,
        BiFunction<String, Throwable, IllegalArgumentException> exceptionConstructor,
        Data<V> data,
        T dependency,
        AtomicInteger counter,
        int limit
    ) {
        return constructor.apply(data, dependency, counter, limit);
    }

    static <T, V> WaitTaskStateData<T, V> getWith(Data<V> data, T dependency, AtomicInteger counter, int limit) {
        return WaitTaskStateDataFactory.getWithCore(
            WaitTaskStateData::new,
            IllegalArgumentException::new,
            data,
            dependency,
            counter,
            limit
        );

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
        return getWith(data, stateData.DEPENDENCY(), stateData.COUNTER(), stateData.LIMIT());
    }

    static <T, V> WaitTaskStateData<T, V> replaceDataAndDependency(WaitTaskStateData<T, V> stateData, Data<V> data, T dependency) {
        return getWith(data, dependency, stateData.COUNTER(), stateData.LIMIT());
    }
}
