package com.neathorium.thorium.core.asserts.namespaces;

import com.neathorium.thorium.java.extensions.interfaces.functional.TriConsumer;

import java.util.function.BiConsumer;

public interface AssertionParameterOrderHandlers {
    static <ActualType, ExpectedType, AssertionMessageType> void handleJUnit5WithMessage(
        TriConsumer<ActualType, ExpectedType, AssertionMessageType> assertion,
        ActualType object,
        AssertionMessageType message,
        ExpectedType expected
    ) {
        assertion.accept(object, expected, message);
    }

    static <ActualType, ExpectedType, AssertionMessageType> void handleJUnitWithMessage(
        TriConsumer<ActualType, ExpectedType, AssertionMessageType> assertion,
        ActualType object,
        AssertionMessageType message,
        ExpectedType expected
    ) {
        assertion.accept(object, expected, message);
    }

    static <ActualType, ExpectedType> void handleWithMessage(
        BiConsumer<ActualType, ExpectedType> assertion,
        ActualType object,
        ExpectedType expected
    ) {
        assertion.accept(object, expected);
    }

    static <ActualType, ExpectedType> void handleWithMessage(
        BiConsumer<ActualType, String> assertion,
        ActualType object,
        String message
    ) {
        assertion.accept(object, message);
    }


    static <ActualType, ExpectedType, AssertionMessageType> void handleTestNGWithMessage(
        TriConsumer<ExpectedType, ActualType, AssertionMessageType> assertion,
        ActualType object,
        AssertionMessageType message,
        ExpectedType expected
    ) {
        assertion.accept(expected, object, message);
    }


}
