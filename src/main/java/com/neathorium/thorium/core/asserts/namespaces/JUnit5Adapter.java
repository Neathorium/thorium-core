package com.neathorium.thorium.core.asserts.namespaces;

import com.neathorium.thorium.core.data.namespaces.DataFunctions;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.asserts.records.BiAssertionHandlersData;
import com.neathorium.thorium.java.extensions.interfaces.functional.TriConsumer;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface JUnit5Adapter {
    static <Actual, Expected> Consumer<Data<Actual>> doAssert(TriConsumer<Expected, Actual, String> assertion, Expected expected, String message) {
        return data -> assertion.accept(expected, DataFunctions.getObject(data), message);
    }

    static <Actual, Expected> Consumer<Data<Actual>> doAssert(TriConsumer<Expected, Actual, String> assertion, Expected expected) {
        return data -> assertion.accept(expected, DataFunctions.getObject(data), DataFunctions.getFormattedMessage(data));
    }

    static <Actual> Consumer<Data<Actual>> doAssert(BiConsumer<Actual, String> assertion, String message) {
        return data -> AssertionParameterOrderHandlers.handleWithMessage(assertion, DataFunctions.getObject(data), message);
    }

    static <Actual> Consumer<Data<Actual>> doAssert(BiConsumer<Actual, String> assertion) {
        final var assertionData = new BiAssertionHandlersData<Actual, String, Data<?>, Data<Actual>>(assertion, AssertionParameterOrderHandlers::handleWithMessage, DataFunctions::getFormattedMessage, DataFunctions::getObject);
        return data -> assertionData.ASSERTION_HANDLER().accept(assertionData.ASSERTION(), assertionData.OBJECT_HANDLER().apply(data), assertionData.MESSAGE_HANDLER().apply(data));
    }

    static <Actual, Expected> Consumer<Data<Actual>> doAssertSupplier(TriConsumer<Expected, Actual, Supplier<String>> assertion, Expected expected, Supplier<String> message) {
        return data -> assertion.accept(expected, DataFunctions.getObject(data), message);
    }

    static <Actual, Expected> Consumer<Data<Actual>> doAssertSupplier(TriConsumer<Expected, Actual, Supplier<String>> assertion, Expected expected) {
        return data -> assertion.accept(expected, DataFunctions.getObject(data), () -> DataFunctions.getFormattedMessage(data));
    }

    static <Actual> Consumer<Data<Actual>> doAssertSupplier(BiConsumer<Actual, Supplier<String>> assertion, Supplier<String> message) {
        return data -> assertion.accept(DataFunctions.getObject(data), message);
    }

    static <Actual> Consumer<Data<Actual>> doAssertSupplier(BiConsumer<Actual, Supplier<String>> assertion) {
        return data -> assertion.accept(DataFunctions.getObject(data), () -> DataFunctions.getFormattedMessage(data));
    }

    static <Actual, Expected> Consumer<Data<Actual>> doAssert(BiConsumer<Expected, Actual> assertion, Expected expected) {
        return data -> assertion.accept(expected, DataFunctions.getObject(data));
    }

    static <Actual> Consumer<Data<Actual>> doAssert(Consumer<Actual> assertion) {
        return data -> assertion.accept(DataFunctions.getObject(data));
    }
}
