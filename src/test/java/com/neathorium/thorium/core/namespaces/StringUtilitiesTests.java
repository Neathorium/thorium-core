package com.neathorium.thorium.core.namespaces;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class StringUtilitiesTests {
    public static Stream<Arguments> containsProvider() {
        return Stream.of(
            Arguments.of("Null doesn't contain null in String terms", null, null, false, "Null contains null"),
            Arguments.of("Null doesn't contain empty string(\"\") in String terms", null, "", false, "Null contains empty string(\"\")"),
            Arguments.of("Empty string(\"\") doesn't contain null in String terms", "", null, false, "Empty string(\"\") contains null"),
            Arguments.of("Empty string(\"\") doesn't contain empty string(\"\") in String terms", "", "", false, "Empty string(\"\") contains empty string(\"\")"),
            Arguments.of("Empty string(\"\") doesn't contain single space string(\" \") in String terms", "", " ", false, "Empty string(\"\") contains single space string(\" \")"),
            Arguments.of("Single space string(\" \") contains single space string(\" \") in String terms", " ", " ", true, "Single space string(\"\") doesn't contain single space string(\" \")"),
            Arguments.of("Double space string(\"  \") contains single space string(\" \") in String terms", " ", " ", true, "Double space string(\"\") doesn't contain single space string(\" \")"),
            Arguments.of("String(\"Johnny applesauce\") contains single a string(\"a\") in String terms", " ", " ", true, "String(\"Johnny applesauce\") doesn't contain single a string(\"a\")")
        );
    }

    public static Stream<Arguments> uncontainsProvider() {
        return Stream.of(
            Arguments.of("Null doesn't contain null in String terms", null, null, false, "Null contains null"),
            Arguments.of("Null doesn't contain empty string(\"\") in String terms", null, "", false, "Null contains empty string(\"\")"),
            Arguments.of("Empty string(\"\") doesn't contain null in String terms", "", null, false, "Empty string(\"\") contains null"),
            Arguments.of("Empty string(\"\") doesn't contain empty string(\"\") in String terms", "", "", false, "Empty string(\"\") contains empty string(\"\")"),
            Arguments.of("Single space string(\" \") contains single space string(\" \") in String terms", " ", " ", false, "Single space string(\"\") doesn't contain single space string(\" \")"),
            Arguments.of("Double space string(\"  \") contains single space string(\" \") in String terms", " ", " ", false, "Double space string(\"\") doesn't contain single space string(\" \")"),
            Arguments.of("String(\"Johnny applesauce\") contains single a string(\"a\") in String terms", " ", " ", false, "String(\"Johnny applesauce\") doesn't contain single a string(\"a\")"),
            Arguments.of("Empty string(\"\") doesn't contain single space string(\" \") in String terms", "", " ", true, "Empty string(\"\") contains single space string(\" \")")
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("containsProvider")
    @Tag("StringUtilities")
    @Tag("contains")
    public void isNullTest(String name, String object, String expected, boolean expectedStatus, String errorMessage) {
        final var result = StringUtilities.contains(object, expected);
        Assertions.assertEquals(result, expectedStatus, errorMessage);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("uncontainsProvider")
    @Tag("StringUtilities")
    @Tag("uncontains")
    public void isNotNullTest(String name, String object, String expected, boolean expectedStatus, String errorMessage) {
        final var result = StringUtilities.uncontains(object, expected);
        Assertions.assertEquals(result, expectedStatus, errorMessage);
    }
}
