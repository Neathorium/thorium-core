package com.neathorium.thorium.core.namespaces.task;

import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.data.interfaces.DataSupplier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public interface TaskFunctions {
    private static CompletableFuture<? extends Data<?>> getTask(DataSupplier<?> step) {
        return CompletableFuture.supplyAsync(step.getSupplier());
    }

    private static CompletableFuture<? extends Data<?>> getTimedTask(DataSupplier<?> step, int duration) {
        return getTask(step).orTimeout(duration, TimeUnit.MILLISECONDS);
    }

    static Function<DataSupplier<?>, CompletableFuture<? extends Data<?>>> getTimedTask(int duration) {
        return step -> getTimedTask(step, duration);
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

    static List<CompletableFuture<? extends Data<?>>> getTaskListWithTimeouts(int duration, DataSupplier<?>... steps) {
        return getTaskList(TaskFunctions.getTimedTask(duration), steps);
    }

    static List<CompletableFuture<? extends Data<?>>> getTaskList(DataSupplier<?>... steps) {
        return getTaskList(TaskFunctions::getTask, steps);
    }

    static CompletableFuture<?>[] getTaskArray(List<CompletableFuture<? extends Data<?>>> list) {
        return list.toArray(new CompletableFuture[0]);
    }
}
