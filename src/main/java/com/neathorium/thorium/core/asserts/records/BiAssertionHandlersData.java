package com.neathorium.thorium.core.asserts.records;

import com.neathorium.thorium.java.extensions.interfaces.functional.TriConsumer;

import java.util.function.BiConsumer;
import java.util.function.Function;

public record BiAssertionHandlersData<T, R, MessageDependencyType, StatusDependencyType>(
    BiConsumer<T, R> ASSERTION,
    TriConsumer<BiConsumer<T, R>, T, R> ASSERTION_HANDLER,
    Function<MessageDependencyType, R> MESSAGE_HANDLER,
    Function<StatusDependencyType, T> OBJECT_HANDLER
) {
}
