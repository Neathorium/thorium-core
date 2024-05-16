package com.neathorium.thorium.core.namespaces.task;

import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.data.interfaces.DataSupplier;
import com.neathorium.thorium.core.wait.records.WaitTimeEntryData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public interface TaskFunctions {
    private static CompletableFuture<? extends Data<?>> getTask(DataSupplier<?> step) {
        return CompletableFuture.supplyAsync(step.getSupplier());
    }

    private static CompletableFuture<? extends Data<?>> getTimedTask(DataSupplier<?> step, WaitTimeEntryData entryData) {
        return TaskFunctions.getTask(step).orTimeout(entryData.LENGTH(), entryData.TIME_UNIT());
    }

    static Function<DataSupplier<?>, CompletableFuture<? extends Data<?>>> getTimedTask(WaitTimeEntryData entryData) {
        return step -> TaskFunctions.getTimedTask(step, entryData);
    }

    static List<CompletableFuture<? extends Data<?>>> addToList(
        List<CompletableFuture<? extends Data<?>>> list,
        Function<DataSupplier<?>, CompletableFuture<? extends Data<?>>> handler,
        DataSupplier<?> step
    ) {
        list.add(handler.apply(step));
        return list;
    }

    static List<CompletableFuture<? extends Data<?>>> getTaskList(Function<DataSupplier<?>, CompletableFuture<? extends Data<?>>> handler, DataSupplier<?>... steps) {
        final var tasks = new ArrayList<CompletableFuture<? extends Data<?>>>(steps.length);
        for (var task : steps) {
            addToList(tasks, handler, task);
        }

        return tasks;
    }

    static List<CompletableFuture<? extends Data<?>>> getTaskListWithTimeouts(WaitTimeEntryData duration, DataSupplier<?>... steps) {
        return TaskFunctions.getTaskList(TaskFunctions.getTimedTask(duration), steps);
    }

    static List<CompletableFuture<? extends Data<?>>> getTaskList(DataSupplier<?>... steps) {
        return TaskFunctions.getTaskList(TaskFunctions::getTask, steps);
    }

    static CompletableFuture<?>[] getTaskArray(List<CompletableFuture<? extends Data<?>>> list) {
        return list.toArray(new CompletableFuture[0]);
    }
}
