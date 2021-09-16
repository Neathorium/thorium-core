package com.neathorium.thorium.core.extensions.records.asserts;

import com.neathorium.thorium.core.extensions.interfaces.functional.TriConsumer;
import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class BiAssertionHandlersData<T, R, MessageDependencyType, StatusDependencyType> {
    public final BiConsumer<T, R> ASSERTION;
    public final TriConsumer<BiConsumer<T, R>, T, R> ASSERTION_HANDLER;
    public final Function<MessageDependencyType, R> MESSAGE_HANDLER;
    public final Function<StatusDependencyType, T> OBJECT_HANDLER;

    public BiAssertionHandlersData(
        BiConsumer<T, R> assertion,
        TriConsumer<BiConsumer<T, R>, T, R> assertionHandler,
        Function<MessageDependencyType, R> messageHandler,
        Function<StatusDependencyType, T> objectHandler
    ) {
        this.ASSERTION = assertion;
        this.ASSERTION_HANDLER = assertionHandler;
        this.MESSAGE_HANDLER = messageHandler;
        this.OBJECT_HANDLER = objectHandler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (NullableFunctions.isNull(o) || CoreUtilities.isNotEqual(getClass(), o.getClass())) {
            return false;
        }

        final var that = (BiAssertionHandlersData<?, ?, ?, ?>) o;
        return (
            CoreUtilities.isEqual(ASSERTION, that.ASSERTION) &&
            CoreUtilities.isEqual(ASSERTION_HANDLER, that.ASSERTION_HANDLER) &&
            CoreUtilities.isEqual(MESSAGE_HANDLER, that.MESSAGE_HANDLER) &&
            CoreUtilities.isEqual(OBJECT_HANDLER, that.OBJECT_HANDLER)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(ASSERTION, ASSERTION_HANDLER, MESSAGE_HANDLER, OBJECT_HANDLER);
    }

    @Override
    public String toString() {
        return (
            "BiAssertionHandlersData{" +
            "ASSERTION=" + ASSERTION +
            ", ASSERTION_HANDLER=" + ASSERTION_HANDLER +
            ", MESSAGE_HANDLER=" + MESSAGE_HANDLER +
            ", OBJECT_HANDLER=" + OBJECT_HANDLER +
            '}'
        );
    }
}
