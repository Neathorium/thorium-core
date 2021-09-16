package com.neathorium.thorium.core.extensions.namespaces.asserts;

import com.neathorium.thorium.core.extensions.interfaces.functional.TriConsumer;
import com.neathorium.thorium.core.extensions.records.asserts.BiAssertionHandlersData;
import com.neathorium.thorium.core.extensions.records.asserts.TriAssertionHandlersData;
import com.neathorium.thorium.core.namespaces.DataFunctions;
import com.neathorium.thorium.core.records.Data;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface JUnit5StatusAdapter {
    static void doAssertSupplier(TriConsumer<Boolean, Boolean, Supplier<String>> assertion, boolean status, Supplier<String> message, boolean expected) {
        assertion.accept(expected, status, message);
    }

    static <StatusDependencyType, MessageDependencyType> void doAssert(
        TriAssertionHandlersData<Boolean, Boolean, String, MessageDependencyType, StatusDependencyType> handlers,
        MessageDependencyType messageDependency,
        StatusDependencyType statusDependency,
        boolean expectedStatus
    ) {
        handlers.ASSERTION_HANDLER.accept(handlers.ASSERTION, handlers.OBJECT_HANDLER.apply(statusDependency), handlers.MESSAGE_HANDLER.apply(messageDependency), expectedStatus);
    }

    static <DependencyType> void doAssert(
        BiAssertionHandlersData<Boolean, String, DependencyType, DependencyType> handlers,
        DependencyType dependency
    ) {
        handlers.ASSERTION_HANDLER.accept(handlers.ASSERTION, handlers.OBJECT_HANDLER.apply(dependency), handlers.MESSAGE_HANDLER.apply(dependency));
    }

    static <DependencyType> void doAssert(
        TriAssertionHandlersData<Boolean, Boolean, String, DependencyType, DependencyType> handlers,
        DependencyType dependency,
        boolean expectedStatus
    ) {
        doAssert(handlers, dependency, dependency, expectedStatus);
    }

    static <StatusExpectedType> void doAssert(
        TriConsumer<Boolean, Boolean, String> assertion,
        Predicate<StatusExpectedType> statusHandler,
        StatusExpectedType statusDependency,
        String message,
        boolean expectedStatus
    ) {
        AssertionParameterOrderHandlers.handleJUnit5WithMessage(assertion, statusHandler.test(statusDependency), message, expectedStatus);
    }

    static <MessageExpectedType> void doAssert(
        TriConsumer<Boolean, Boolean, String> assertion,
        Function<MessageExpectedType, String> messageHandler,
        MessageExpectedType messageDependency,
        boolean status,
        boolean expectedStatus
    ) {
        AssertionParameterOrderHandlers.handleJUnit5WithMessage(assertion, status, messageHandler.apply(messageDependency), expectedStatus);
    }

    static Consumer<Data> doAssert(TriConsumer<Boolean, Boolean, String> assertion, boolean expected, String message) {
        return data -> doAssert(assertion, DataFunctions::getStatus, data, message, expected);
    }

    static Consumer<Data> doAssert(TriConsumer<Boolean, Boolean, String> assertion, boolean expected) {
        final var assertData = new TriAssertionHandlersData<Boolean, Boolean, String, Data<?>, Data<?>>(assertion, AssertionParameterOrderHandlers::handleJUnit5WithMessage, DataFunctions::getFormattedMessage, DataFunctions::getStatus);
        return data -> doAssert(assertData, data, expected);
    }

    static <DependencyType> void doAssert(
        BiConsumer<Boolean, String> assertion,
        Function<DependencyType, String> messageHandler,
        DependencyType dependency,
        boolean status
    ) {
        AssertionParameterOrderHandlers.handleWithMessage(assertion, status, messageHandler.apply(dependency));
    }

    static <DependencyType> void doAssert(
        BiConsumer<Boolean, String> assertion,
        Predicate<DependencyType> statusHandler,
        DependencyType dependency,
        String message
    ) {
        AssertionParameterOrderHandlers.handleWithMessage(assertion, statusHandler.test(dependency), message);
    }

    static Consumer<Data> doAssert(BiConsumer<Boolean, String> assertion) {
        final var assertionData = new BiAssertionHandlersData<Boolean, String, Data<?>, Data<?>>(assertion, AssertionParameterOrderHandlers::handleWithMessage, DataFunctions::getFormattedMessage, DataFunctions::getStatus);
        return data -> doAssert(assertionData, data);
    }

    static Consumer<Data> doAssert(BiConsumer<Boolean, String> assertion, String message) {
        return data -> doAssert(assertion, DataFunctions::getStatus, data, message);
    }

    static Consumer<Data> doAssertSupplier(TriConsumer<Boolean, Boolean, Supplier<String>> assertion, Boolean expected) {
        return data -> doAssertSupplier(assertion, DataFunctions.getStatus(data), () -> DataFunctions.getFormattedMessage(data), expected);
    }

    static Consumer<Data> doAssertSupplier(BiConsumer<Boolean, Supplier<String>> assertion, Supplier<String> message) {
        return data -> assertion.accept(DataFunctions.getStatus(data), message);
    }

    static Consumer<Data> doAssertSupplier(BiConsumer<Boolean, Supplier<String>> assertion) {
        return data -> assertion.accept(DataFunctions.getStatus(data), () -> DataFunctions.getFormattedMessage(data));
    }

    static Consumer<Data> doAssert(BiConsumer<Boolean, Boolean> assertion, Boolean expected) {
        return data -> assertion.accept(expected, DataFunctions.getStatus(data));
    }

    static Consumer<Data> doAssert(Consumer<Boolean> assertion) {
        return data -> assertion.accept(DataFunctions.getStatus(data));
    }
}
