package com.neathorium.thorium.core.extensions.namespaces;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class NullableFunctionsTests {
    public static Stream<Arguments> isNullProvider() {
        return Stream.of(
            Arguments.of("Null is null", null, true, "Null wasn't null"),
            Arguments.of("Basic object is not null", new Object(), false, "Basic object was null"),
            Arguments.of("Primitive integer 1 is not null", 1, false, "Primitive integer 1 was null"),
            Arguments.of("Empty String is not null", "", false, "Empty String was null"),
            Arguments.of("Single whitespace String is not null", " ", false, "Single whitespace String was null"),
            Arguments.of("Single (\"a\") String is not null", "a", false, "Single (\"a\") String was null")
        );
    }

    public static Stream<Arguments> isNotNullProvider() {
        return Stream.of(
            Arguments.of("Null is null", null, false, "Null wasn't null"),
            Arguments.of("Basic object is not null", new Object(), true, "Basic object was null"),
            Arguments.of("Primitive integer 1 is not null", 1, true, "Primitive integer 1 was null"),
            Arguments.of("Empty String is not null", "", true, "Empty String was null"),
            Arguments.of("Single whitespace String is not null", " ", true, "Single whitespace String was null"),
            Arguments.of("Single (\"a\") String is not null", "a", true, "Single (\"a\") String was null")
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("isNullProvider")
    @Tag("NullableFunctions")
    @Tag("isNull")
    public void isNullTest(String name, Object object, boolean expectedStatus, String errorMessage) {
        final var result = NullableFunctions.isNull(object);
        Assertions.assertEquals(result, expectedStatus, errorMessage);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("isNotNullProvider")
    @Tag("NullableFunctions")
    @Tag("isNotNull")
    public void isNotNullTest(String name, Object object, boolean expectedStatus, String errorMessage) {
        final var result = NullableFunctions.isNotNull(object);
        Assertions.assertEquals(result, expectedStatus, errorMessage);
    }
}
