package com.neathorium.thorium.core.formatter;

import com.neathorium.thorium.core.constants.CommandRangeDataConstants;
import com.neathorium.thorium.core.constants.formatter.NumberConditionDataConstants;
import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.data.namespaces.DataFunctions;
import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Objects;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class FormatterTests {
    public static Stream<Arguments> isLessThanExpectedProvider() {
        final var baseMessage = "isLessThanExpected: Parameters were ";
        return Stream.of(
            Arguments.of("0 less than 1, parameter x", 0, 1, "x", true, baseMessage + "okay.\n"),
            Arguments.of("0 less than 1, parameter name empty", 0, 1, "", true, baseMessage + "okay.\n"),
            Arguments.of("0 less than 1, parameter name a single space", 0, 1, " ", true, baseMessage + "okay.\n"),
            Arguments.of("-1 less than 0, parameter x", -1, 0, "x", true, baseMessage + "okay.\n"),
            Arguments.of("-1 less than 0, parameter name empty", -1, 0, "", true, baseMessage + "okay.\n"),
            Arguments.of("-1 less than 0, parameter name a single space", -1, 0, " ", true, baseMessage + "okay.\n"),
            Arguments.of("1 isn't less than 0, parameter x", 1, 0, "x", false, baseMessage + "not okay.\nx(\"1\") was not less than expected(\"0\").\n"),
            Arguments.of("0 equals 0, result is false, parameter x", 0, 0, "x", false, baseMessage + "not okay.\nx(\"0\") was not less than expected(\"0\").\n"),
            Arguments.of("1 isn't less than 0, parameter name empty", 1, 0, "", false, baseMessage + "not okay.\nNumber(\"1\") was not less than expected(\"0\").\n"),
            Arguments.of("1 isn't less than 0, parameter name a single space", 1, 0, " ", false, baseMessage + "not okay.\nNumber(\"1\") was not less than expected(\"0\").\n"),
            Arguments.of("0 less than 1, parameter name null", 0, 1, null, false, "isLessThanExpected: There were parameter issue(s):\nFunction parameter - parameter name parameter was null.\n")
        );
    }

    public static Stream<Arguments> isLessThanExpectedMessageTestProvider() {
        final var baseMessage = "isLessThanExpectedMessage: " + CoreFormatterConstants.PARAMETER_ISSUES_LINE;
        return Stream.of(
            Arguments.of("0 less than 1, parameter x", 0, 1, "x", ""),
            Arguments.of("0 less than 1, parameter name empty", 0, 1, "", ""),
            Arguments.of("0 less than 1, parameter name a single space", 0, 1, " ", ""),
            Arguments.of("-1 less than 0, parameter x", -1, 0, "x", ""),
            Arguments.of("-1 less than 0, parameter name empty", -1, 0, "", ""),
            Arguments.of("-1 less than 0, parameter name a single space", -1, 0, " ", ""),
            Arguments.of("1 isn't less than 0, parameter x", 1, 0, "x", baseMessage + "x(\"1\") was not less than expected(\"0\").\n"),
            Arguments.of("0 equals 0, result is false, parameter x", 0, 0, "x", baseMessage + "x(\"0\") was not less than expected(\"0\").\n"),
            Arguments.of("1 isn't less than 0, parameter name empty", 1, 0, "", baseMessage + "Number(\"1\") was not less than expected(\"0\").\n"),
            Arguments.of("1 isn't less than 0, parameter name a single space", 1, 0, " ", baseMessage + "Number(\"1\") was not less than expected(\"0\").\n"),
            Arguments.of("0 less than 1, parameter name null", 0, 1, null, baseMessage + "Function parameter - parameter name parameter was null.\n")
        );
    }

    @DisplayName("Default command range")
    @Test
    void defaultCommandRangeTest() {
        final var result = CoreFormatter.getCommandRangeParameterMessage(CommandRangeDataConstants.DEFAULT_RANGE);
        Assertions.assertTrue(isBlank(result), result);
    }

    @DisplayName("isNumberConditionCore with Default Executor Range")
    @Test
    void isNumberConditionCoreWithDefaultCommandRangeTest() {
        final var min = CommandRangeDataConstants.DEFAULT_RANGE.min;
        final var result = CoreFormatter.isNumberConditionCore(min, 0, "Range minimum", NumberConditionDataConstants.MORE_THAN);
        Assertions.assertTrue(result.STATUS(), result.OBJECT() + " Message:  " + result.MESSAGE());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("isLessThanExpectedProvider")
    void isLessThanExpectedTest(String name, int number, int expected, String parameterName, boolean expectedStatus, String expectedMessage) {
        final var result = CoreFormatter.isLessThanExpected(number, expected, parameterName);
        final var message = DataFunctions.getFormattedMessage(result);
        Assertions.assertTrue((Objects.equals(result.STATUS(), expectedStatus) && Objects.equals(message, expectedMessage)), message);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("isLessThanExpectedMessageTestProvider")
    void isLessThanExpectedMessageTest(String name, int number, int expected, String parameterName, String expectedMessage) {
        final var result = CoreFormatter.isLessThanExpectedMessage(number, expected, parameterName);
        Assertions.assertEquals(expectedMessage, result, "Result didn't equal expected message" + CoreFormatterConstants.END_LINE);
    }
}
