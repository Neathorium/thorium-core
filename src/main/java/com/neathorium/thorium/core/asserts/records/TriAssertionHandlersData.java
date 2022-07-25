package com.neathorium.thorium.core.asserts.records;

import com.neathorium.thorium.java.extensions.interfaces.functional.QuadConsumer;
import com.neathorium.thorium.java.extensions.interfaces.functional.TriConsumer;

import java.util.function.Function;

public record TriAssertionHandlersData<T, R, AssertionMessageType, MessageDependencyType, StatusDependencyType>(
    TriConsumer<T, R, AssertionMessageType> ASSERTION,
    QuadConsumer<TriConsumer<T, R, AssertionMessageType>, T, AssertionMessageType, R> ASSERTION_HANDLER,
    Function<MessageDependencyType, String> MESSAGE_HANDLER,
    Function<StatusDependencyType, T> OBJECT_HANDLER
) {
}
