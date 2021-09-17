package com.neathorium.thorium.core.extensions.namespaces.concurrent;

import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public interface CompletableFutureExtensions {
    private static CompletableFuture<?> allOfTerminateOnFailure(Function<CompletableFuture<?>[], CompletableFuture<?>> handler, CompletableFuture<?>... tasks) {
        final var failure = new CompletableFuture<>();
        for (var task : tasks) {
            task.exceptionally(ex -> {
                failure.completeExceptionally(ex);
                return null;
            });
        }

        return CompletableFuture.anyOf(failure, handler.apply(tasks));
    }

    private static CompletableFuture<?> anyOfTerminateOnFailureCore(Function<CompletableFuture<?>[], CompletableFuture<?>> handler, CompletableFuture<?>... tasks) {
        final var failure = new CompletableFuture<>();
        for (var task : tasks) {
            task.whenComplete((a, ex) -> {
                failure.complete(null);
                for (var task2: tasks) {
                    if (CoreUtilities.isFalse(task2.isDone())) {
                        task2.complete(null);
                    }
                }
                return;
            });
        }

        return CompletableFuture.anyOf(failure, handler.apply(tasks));
    }

    static CompletableFuture<?> allOfTerminateOnFailureTimed(int duration, CompletableFuture<?>... tasks) {
        final Function<CompletableFuture<?>[], CompletableFuture<?>> allOf = CompletableFuture::allOf;
        return allOfTerminateOnFailure(allOf.andThen(task -> task.orTimeout(duration, TimeUnit.MILLISECONDS)), tasks);
    }

    static CompletableFuture<?> allOfTerminateOnFailure(CompletableFuture<?>... tasks) {
        return allOfTerminateOnFailure(CompletableFuture::allOf, tasks);
    }

    static CompletableFuture<?> anyOfTerminateOnFailureTimed(int duration, CompletableFuture<?>... tasks) {
        final Function<CompletableFuture<?>[], CompletableFuture<?>> anyOf = CompletableFuture::anyOf;
        return anyOfTerminateOnFailureCore(anyOf.andThen(task -> task.orTimeout(duration, TimeUnit.MILLISECONDS)), tasks);
    }

    static CompletableFuture<?> anyOfTerminateOnFailure(Function<CompletableFuture<?>[], CompletableFuture<?>> handler, CompletableFuture<?>... tasks) {
        return anyOfTerminateOnFailureCore(CompletableFuture::anyOf, tasks);
    }
}
