package com.neathorium.thorium.core.namespaces.validators;

import com.neathorium.thorium.core.constants.CommandRangeDataConstants;
import com.neathorium.thorium.core.constants.formatter.NumberConditionDataConstants;
import com.neathorium.thorium.core.extensions.DecoratedList;
import com.neathorium.thorium.core.extensions.interfaces.IEmptiable;
import com.neathorium.thorium.core.extensions.namespaces.predicates.AmountPredicates;
import com.neathorium.thorium.core.extensions.namespaces.predicates.BasicPredicates;
import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.extensions.namespaces.EmptiableFunctions;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;
import com.neathorium.thorium.core.namespaces.DataFactoryFunctions;
import com.neathorium.thorium.core.namespaces.DataFunctions;
import com.neathorium.thorium.core.namespaces.StringUtilities;
import com.neathorium.thorium.core.namespaces.exception.ExceptionFunctions;
import com.neathorium.thorium.core.namespaces.predicates.DataPredicates;
import com.neathorium.thorium.core.records.Data;
import com.neathorium.thorium.core.records.command.CommandRangeData;
import com.neathorium.thorium.core.records.executor.ExecutionResultData;
import com.neathorium.thorium.core.records.executor.ExecutionStateData;
import com.neathorium.thorium.core.records.formatter.NumberConditionData;
import com.neathorium.thorium.core.records.reflection.message.InvokeCommonMessageParametersData;
import com.neathorium.thorium.core.records.reflection.message.InvokeParameterizedMessageData;
import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.records.wait.WaitTimeData;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface CoreFormatter {
    static String getNamedErrorMessageOrEmpty(String name, String message) {
        final var nameof = isNotBlank(name) ? name : "getNamedErrorMessageOrEmpty: (Name was empty.) ";
        if (isBlank(message)) {
            return CoreFormatterConstants.EMPTY;
        }

        var parameterIssues = (StringUtilities.contains(message, CoreFormatterConstants.PARAMETER_ISSUES_LINE) ? CoreFormatterConstants.EMPTY : CoreFormatterConstants.PARAMETER_ISSUES_LINE);
        return nameof + CoreFormatterConstants.COLON_SPACE + parameterIssues + message;
    }

    static String getOptionMessage(boolean status) {
        return status ? CoreFormatterConstants.OPTION_EMPTY : CoreFormatterConstants.OPTION_NOT;
    }

    static String isParameterMessage(boolean condition, String parameterName, String descriptor) {
        return condition ? parameterName + " parameter was " + descriptor + CoreFormatterConstants.END_LINE : CoreFormatterConstants.EMPTY;
    }

    private static String isParameterNotMessage(boolean condition, String parameterName, String descriptor) {
        return !condition ? parameterName + " parameter wasn't " + descriptor + CoreFormatterConstants.END_LINE : CoreFormatterConstants.EMPTY;
    }

    static <T> String isNullMessageWithName(T object, String parameterName) {
        return isParameterMessage(NullableFunctions.isNull(object), parameterName, "null");
    }

    static <T> String isNullMessage(T object) {
        return isNullMessageWithName(object, "Object");
    }

    static <T> String isEmptyMessage(T[] object) {
        var message = isNullMessageWithName(object, "Object");
        if (isBlank(message)) {
            message += BasicPredicates.isZeroOrNonPositive(object.length) ? "Object is empty" : CoreFormatterConstants.EMPTY;
        }

        return getNamedErrorMessageOrEmpty("isEmptyMessage", message);
    }

    static String isInvalidOrFalseMessageWithName(Data data, String parameterName) {
        var message = isParameterMessage(DataPredicates.isInvalidOrFalse(data), parameterName, "false data");
        if (isNotBlank(message)) {
            message += data.message;
        }

        return getNamedErrorMessageOrEmpty("isInvalidOrFalseMessage", message);
    }

    static String isInvalidOrFalseMessage(Data data) {
        return isInvalidOrFalseMessageWithName(data, "Data");
    }

    static String isInvalidOrFalseMessageE(ExecutionResultData data) {
        return isNullMessageWithName(data.result, "Result Object");
    }

    private static String isValidNonFalseMessageWithName(Data data, String parameterName) {
        var message = isParameterNotMessage(DataPredicates.isValidNonFalse(data), parameterName, "valid, non-false data");
        if (isNotBlank(message)) {
            message += data.message;
        }

        return getNamedErrorMessageOrEmpty("isValidNonFalseMessageWithName", message);
    }

    private static String isValidNonFalseMessage(Data data) {
        return isInvalidOrFalseMessageWithName(data, "Data");
    }

    static String isFalseMessageWithName(Data data, String parameterName) {
        var message = isParameterMessage(DataPredicates.isFalse(data), parameterName, "false data");
        if (isNotBlank(message)) {
            message += data.message;
        }

        return getNamedErrorMessageOrEmpty("isFalseMessageWithName", message);
    }

    static String isFalseMessage(Data data) {
        return isFalseMessageWithName(data, "data");
    }

    static String isTrueMessageWithName(Data data, String parameterName) {
        var message = isParameterMessage(DataPredicates.isTrue(data), parameterName, "true data");
        if (isNotBlank(message)) {
            message += data.message;
        }

        return getNamedErrorMessageOrEmpty("isTrueMessageWithName", message);
    }

    static String isTrueMessage(Data data) {
        return isTrueMessageWithName(data, "data");
    }

    static String isBlankMessageWithName(String value, String parameterName) {
        return isParameterMessage(isBlank(value), isNotBlank(parameterName) ? parameterName : "Unspecified", "blank, empty or null");
    }

    static String isBlankMessage(String value) {
        return isBlankMessageWithName(value, "String value");
    }

    static String isFalseMessageWithName(boolean condition, String parameterName) {
        return isParameterMessage(CoreUtilities.isFalse(condition), parameterName, "false");
    }

    static String isInvalidMessage(boolean condition, String parameterName) {
        return isParameterMessage(CoreUtilities.isFalse(condition), parameterName, "invalid");
    }

    static String getConditionStatusMessage(boolean key) {
        return key ? "is" : "isn't";
    }

    static String getConditionMessage(String elementName, String descriptor, boolean option) {
        final var name = "getConditionMessage";
        final var errorMessage = (
            isBlankMessageWithName(elementName, "Element name") +
            isBlankMessageWithName(descriptor, "Descriptor")
        );
        return  (
            isNotBlank(errorMessage) ? (
                getNamedErrorMessageOrEmpty(name, errorMessage)
            ) : name +(CoreFormatterConstants.ELEMENT + getConditionStatusMessage(option) + " " + descriptor + CoreFormatterConstants.END_LINE)
        );
    }

    static String getElementValueMessage(String elementName, String descriptor, String value) {
        final var name = "getValueMessage";
        final var errorMessage = (
            isBlankMessageWithName(elementName, "Element name") +
            isBlankMessageWithName(descriptor, "Descriptor") +
            isNullMessageWithName(value, "Value")
        );
        return name + (
            isNotBlank(errorMessage) ? (
                getNamedErrorMessageOrEmpty(name, errorMessage)
            ) : name + (CoreFormatterConstants.ELEMENT + " " + elementName + " " + descriptor + " was (\"" + value +"\")"  + CoreFormatterConstants.END_LINE)
        );
    }

    static String getMethodFromMapMessage(String methodName, boolean status) {
        return "Method(" + methodName + ") " + getOptionMessage(status) + " found in map" + CoreFormatterConstants.END_LINE;
    }

    static String getExecutionTimeMessage(boolean success, String message, WaitTimeData data, Instant startTime, Instant stopTime) {
        final var localMessage = "\t" + (StringUtilities.endsWithCaseInsensitive(message, "\n") ? message : message + "\n");
        return (
            (success ? CoreFormatterConstants.WAITING_SUCCESSFUL : CoreFormatterConstants.WAITING_FAILED) +
            localMessage +
            "\tExecution ran from time(\"" + startTime.toString() + "\") to (\"" + stopTime.toString() + "\")" + CoreFormatterConstants.END_LINE +
            "\tDuration(\"" + data.duration.toMillis() + "\" milliseconds), actually ran for " + ChronoUnit.MILLIS.between(startTime, stopTime) + " milliseconds, with " + data.interval.toMillis() + " milliseconds interval" + CoreFormatterConstants.END_LINE
        );
    }

    static String getWaitInterruptMessage(String message) {
        return CoreFormatterConstants.WAITING_FAILED + "Thread interruption occurred, exception message" + CoreFormatterConstants.COLON_NEWLINE + message;
    }

    static String getWaitExpectedExceptionMessage(String message) {
        return CoreFormatterConstants.WAITING_FAILED + "Expected exception occurred, exception message" + CoreFormatterConstants.COLON_NEWLINE + message;
    }

    static String getWaitCancellationWithoutResultMessage(String message) {
        return CoreFormatterConstants.WAITING_FAILED + "Cancellation exception occurred with no result, exception message" + CoreFormatterConstants.COLON_NEWLINE + message;
    }

    static String getExecutionStepMessage(int index, String message) {
        return (index + 1) + ". " + message + CoreFormatterConstants.END_LINE;
    }

    static String getExecutionStatusInvalidMessage(ExecutionStateData data) {
        var message = isNullMessageWithName(data, "Execution State Data");
        if (isBlank(message)) {
            message += (
                isNullMessageWithName(data.executionMap, "Execution Map") +
                isNullMessageWithName(data.indices, "Indices")
            );
        }

        return getNamedErrorMessageOrEmpty("getExecutionStatusInvalidMessage", message);
    }

    static String getExecutionEndParametersInvalidMessage(ExecutionStateData state, String key, int index, int length) {
        return getNamedErrorMessageOrEmpty(
            "getExecutionEndParametersInvalidMessage",
            (
                getExecutionStatusInvalidMessage(state) +
                isBlankMessageWithName(key, "Last key from execution") +
                isNegativeMessageWithName(index, "Index") +
                isNegativeMessageWithName(length, "Length")
            )
        );
    }

    static String getNamedErrorMessageOrEmptyNoIssues(String name, String message) {
        final var nameof = isNotBlank(name) ? name : "getNamedErrorMessageOrEmpty: (Name was empty.) ";
        return isNotBlank(message) ? nameof + message : CoreFormatterConstants.EMPTY;
    }

    static String getExecutionEndMessage(ExecutionStateData state, String key, int index, int length) {
        final var errorMessage = getExecutionEndParametersInvalidMessage(state, key, index, length);
        if (isNotBlank(errorMessage)) {
            return errorMessage;
        }

        final var map = state.executionMap;
        final var valueSet = map.values();
        final var passedValueAmount = valueSet.stream().filter(DataPredicates::isValidNonFalse).count();
        final var valuesLength = valueSet.size();
        final var failedValueAmount = valuesLength - passedValueAmount;
        final var builder = new StringBuilder();
        final var values = valueSet.toArray(new Data<?>[0]);
        Data<?> step;
        if (BasicPredicates.isPositiveNonZero((int)failedValueAmount)) {
            for (var stepIndex = 0; stepIndex < valuesLength; ++stepIndex) {
                step = values[stepIndex];
                builder.append(getExecutionStepMessage(stepIndex, (DataPredicates.isValidNonFalse(step) ? "Passed" : "Failed") + CoreFormatterConstants.COLON_SPACE + DataFunctions.getFormattedMessage(step)));
            }
        } else {
            step = map.get(key);
            builder.append(getExecutionStepMessage(valuesLength - 1, (DataPredicates.isValidNonFalse(step) ? "Passed" : "Failed") + CoreFormatterConstants.COLON_SPACE + DataFunctions.getFormattedMessage(step)));
        }

        final var message = (
            ((index == length) ? "All" : "Some") + " steps were executed" + CoreFormatterConstants.COLON_SPACE +
            (BasicPredicates.isPositiveNonZero((int)failedValueAmount) ? (
                passedValueAmount + " passed, " + failedValueAmount + " failed"
            ) : ("All(" + passedValueAmount + ") passed")) + CoreFormatterConstants.END_LINE +
            "    " + builder.toString().replaceAll("\n", "\n    ")
        );

        return getNamedErrorMessageOrEmptyNoIssues("Execution end", message);
    }

    static String getExecutionEndMessageAggregate(ExecutionStateData state, String key, int index, int length) {
        final var errorMessage = getExecutionEndParametersInvalidMessage(state, key, index, length);
        if (isNotBlank(errorMessage)) {
            return errorMessage;
        }

        final var valueSet = state.executionMap.values();
        final var passedValueAmount = valueSet.stream().filter(DataPredicates::isValidNonFalse).count();
        final var failedValueAmount = length - passedValueAmount;
        final var builder = new StringBuilder();
        final var valuesLength = valueSet.size();
        final var values = valueSet.toArray(new Data<?>[0]);
        var stepIndex = 0;
        Data<?> step;
        for(; stepIndex < valuesLength; ++stepIndex) {
            step = values[stepIndex];
            builder.append(getExecutionStepMessage(stepIndex, (DataPredicates.isValidNonFalse(step) ? "Passed" : "Failed") + CoreFormatterConstants.COLON_SPACE + DataFunctions.getFormattedMessage(step)));
        }

        final var message = (
            ((index == length) ? "All" : "Some") + " steps were executed" + CoreFormatterConstants.COLON_SPACE +
            (BasicPredicates.isPositiveNonZero((int)failedValueAmount) ? (
                passedValueAmount + " passed, " + failedValueAmount + " failed"
            ) : ("All(" + passedValueAmount + ") passed")) + CoreFormatterConstants.END_LINE +
            "    " + builder.toString().replaceAll("\n", "\n    ")
        );

        return getNamedErrorMessageOrEmpty("getExecutionEndMessage", message);
    }

    static String getExecuteFragment(boolean status) {
        return status ? CoreFormatterConstants.SUCCESSFULLY_EXECUTE : CoreFormatterConstants.COULDNT_EXECUTE;
    }

    static String getScreenshotFileName(String path, String fileName) {
        final var localTime = Long.toString(Instant.now().toEpochMilli());
        final var uuid = UUID.randomUUID().toString();
        var localFilename = isNotBlank(fileName) ? fileName : CoreFormatterConstants.SCREENSHOT_NAME_START;
        return path + String.join(CoreFormatterConstants.SCREENSHOT_NAME_SEPARATOR, localTime, uuid, localFilename) + CoreFormatterConstants.EXTENSION;
    }

    static String getMethodFromListMessage(String methodName, boolean status) {
        return "Method(" + methodName + ") " + getOptionMessage(status) + " found" + CoreFormatterConstants.END_LINE;
    }

    static String getInputErrorMessage(String input) {
        return isBlankMessageWithName(input, "Input");
    }

    static <T> String isEmptyMessage(T data, String parameterName) {
        var message = isNullMessageWithName(data, parameterName);
        if (isBlank(message)) {
            //TODO Java13-14 instanceof + switch expression.
            var type = "";
            if (data instanceof List && EmptiableFunctions.isEmpty((List)data)) {
                type = "(List)";
            }

            if (data instanceof Map && EmptiableFunctions.isEmpty((Map)data)) {
                type = "(Map)";
            }

            if (isNotBlank(type)) {
                message += parameterName + type + " was empty" + CoreFormatterConstants.END_LINE;
            }
        }

        return getNamedErrorMessageOrEmpty("isEmptyMessage", message);
    }

    static <T> String isEmptyMessage(T data) {
        return isEmptyMessage(data, "Emptiable data");
    }


    static String getNumberConditionDataValidMessage(NumberConditionData data) {
        var message = isNullMessageWithName(data, "Number condition data");
        if (isBlank(message)) {
            message += (
                isNullMessageWithName(data.nameof, "Caller function's Name") +
                isBlankMessageWithName(data.descriptor, "Condition Descriptor") +
                isBlankMessageWithName(data.parameterName, "Parameter Name") +
                isNullMessageWithName(data.function, "Condition Function")
            );
        }

        return getNamedErrorMessageOrEmpty("getNumberConditionDataValidMessage", message);
    }

    private static String getNumberConditionCoreFormattedMessage(int number, int expected, String parameterName, String descriptor) {
        return parameterName + "(\"" + number + "\") was " + descriptor + " expected(\"" + expected +"\")" + CoreFormatterConstants.END_LINE;
    }

    static Data<String> isNumberConditionCore(int number, int expected, String parameterName, NumberConditionData data) {
        var message = getNumberConditionDataValidMessage(data);
        if (isBlank(message)) {
            message += isNullMessageWithName(parameterName, "Function parameter - parameter name");
        }

        if (isNotBlank(message)) {
            final var returnMessage = CoreFormatterConstants.PARAMETER_ISSUES_LINE + message;
            return DataFactoryFunctions.getInvalidWith(returnMessage, data.nameof, returnMessage);
        }

        final var status = data.function.test(number, expected);
        final var option = getOptionMessage(status);
        final var descriptor = option + data.descriptor;
        final var object = getNumberConditionCoreFormattedMessage(number, expected, (isBlank(parameterName) ? data.parameterName : parameterName), descriptor);
        var returnMessage = "Parameters were " + option + "okay" + CoreFormatterConstants.END_LINE;
        if (!status) {
            returnMessage += object;
        }

        return DataFactoryFunctions.getWith(object, status, data.nameof, returnMessage);
    }

    static Data<String> isEqualToExpected(int number, int expected, String parameterName) {
        return isNumberConditionCore(number, expected, parameterName, NumberConditionDataConstants.EQUAL_TO);
    }

    static Data<String> isLessThanExpected(int number, int expected, String parameterName) {
        return isNumberConditionCore(number, expected, parameterName, NumberConditionDataConstants.LESS_THAN);
    }

    static Data<String> isMoreThanExpected(int number, int expected, String parameterName) {
        return isNumberConditionCore(number, expected, parameterName, NumberConditionDataConstants.MORE_THAN);
    }

    static String isEqualToExpectedMessage(int number, int expected, String parameterName) {
        var message = "";
        var data = isEqualToExpected(number, expected, parameterName);
        if (DataPredicates.isInvalidOrFalse(data)) {
            message += data.object;
        }

        return getNamedErrorMessageOrEmpty("isEqualToExpectedMessage", message);
    }

    static String isLessThanExpectedMessage(int number, int expected, String parameterName) {
        var message = "";
        var data = isLessThanExpected(number, expected, parameterName);
        if (DataPredicates.isInvalidOrFalse(data)) {
            message += data.object;
        }

        return getNamedErrorMessageOrEmpty("isLessThanExpectedMessage", message);
    }

    static String isMoreThanExpectedMessage(int number, int expected, String parameterName) {
        var message = "";
        var data = isMoreThanExpected(number, expected, parameterName);
        if (DataPredicates.isInvalidOrFalse(data)) {
            message += data.object;
        }

        return getNamedErrorMessageOrEmpty("isMoreThanExpectedMessage", message);
    }

    static String getCommandRangeParameterMessage(CommandRangeData range) {
        var message = isNullMessageWithName(range, "Range object");
        if (isBlank(message)) {

            message += (
                isMoreThanExpectedMessage(range.min, 0, "Range minimum") +
                isLessThanExpectedMessage(range.max, 1000, "Range maximum") +
                isNullMessageWithName(range.rangeInvalidator, "Command Range validator function")
            );
        }

        return getNamedErrorMessageOrEmpty("getCommandRangeParameterMessage", message);
    }

    static String getCommandAmountRangeErrorMessage(int length, CommandRangeData range) {
        var message = getCommandRangeParameterMessage(range);
        if (isNotBlank(message)) {
            return message + "Cannot invalidate length(\"" + length + "\") on an invalid range" + CoreFormatterConstants.END_LINE;
        }

        final var status = range.rangeInvalidator.apply(range.min, length, range.max);
        if (!status) {
            return CoreFormatterConstants.EMPTY;
        }

        final var parameterName = "Actual Command amount";
        final var minData = isLessThanExpected(length, range.min, parameterName);
        if (!minData.status) {
            message += minData.object;
        }

        final var maxData = isMoreThanExpected(length, range.max, parameterName);
        if (!maxData.status) {
            message += maxData.object;
        }

        return message;
    }

    static String getValidCommandMessage(Function<?, ?>[] steps, CommandRangeData range) {
        var message = isNullMessageWithName(steps, "Steps");
        var length = 0;
        if (isBlank(message)) {
            length = steps.length;
            message += isMoreThanExpectedMessage(length, 0, "Steps Length");
        }
        if (isBlank(message)) {
            message += getCommandAmountRangeErrorMessage(length, range);
        }

        if (isBlank(message)) {
            var builder = new StringBuilder();
            var current = "";
            if (isBlank(message)) {
                for (var index = 0; index < length; ++index) {
                    current = isNullMessageWithName(steps[index], index + ". step");
                    if (isNotBlank(current)) {
                        builder.append(current);
                    }
                }
            }

            message += builder.toString();
        }

        return getNamedErrorMessageOrEmpty("getValidCommandMessage", message);
    }

    static String getCommandAmountRangeErrorMessage(int length, int min, int max) {
        var message = "";
        if (length < min) {
            message += "The commands' amount was below the minimum(" + min + ") limit";
        }
        if (length > max) {
            message += "The commands' amount was above the maximum(" + max +") limit";
        }

        return message;
    }

    static String getCommandAmountRangeErrorMessage(int length) {
        return getCommandAmountRangeErrorMessage(length, CommandRangeDataConstants.MINIMUM_COMMAND_LIMIT, CommandRangeDataConstants.MAXIMUM_COMMAND_LIMIT);
    }

    static String formatPrefixSuffixMessage(String prefix, Boolean status, String suffix) {
        return prefix + status + suffix;
    }

    static <T> String getInvokeMethodCoreMessage(Exception exception, String message, String returnType, String parameterTypes) {
        final var endLine = CoreFormatterConstants.END_LINE;
        return ExceptionFunctions.isException(exception) ? (
            String.join(
                endLine,
                message,
                "An Exception(" + exception.getClass() + ") has occurred",
                "Exception Message:\n" + exception.getMessage(),
                "Cause: " + exception.getCause(),
                "Method parameter types: " + parameterTypes,
                "Result is of type " + returnType
            ) + endLine
        ) : CoreFormatterConstants.EMPTY;
    }

    static String getInvokeMethodCommonMessage(InvokeCommonMessageParametersData data, Exception exception) {
        return (CoreUtilities.areNotNull(data, exception) && CoreUtilities.areNotBlank(data.MESSAGE, data.PARAMETER_TYPES, data.RETURN_TYPE)) ? (
            getInvokeMethodCoreMessage(exception, data.MESSAGE, data.PARAMETER_TYPES, data.RETURN_TYPE)
        ) : "Data parameter" + CoreFormatterConstants.WAS_NULL;
    }

    static String getInvokeMethodParameterizedMessage(InvokeParameterizedMessageData data, Exception exception) {
        if (CoreUtilities.areAnyNull(data, exception) || CoreUtilities.areAnyBlank(data.MESSAGE, data.PARAMETER_TYPES, data.RETURN_TYPE)) {
            return "Data parameter" + CoreFormatterConstants.WAS_NULL;
        }

        final var parameter = data.parameter;
        final var parameterMessage = (isNotBlank(parameter) ? "Parameter was specified: " + parameter : "Parameter wasn't specified") + CoreFormatterConstants.END_LINE;
        final var invokeMessage = getInvokeMethodCoreMessage(exception, data.MESSAGE, data.PARAMETER_TYPES, data.RETURN_TYPE);
        return isNotBlank(invokeMessage) ? invokeMessage + parameterMessage : CoreFormatterConstants.EMPTY;
    }

    static Function<Exception, String> getInvokeMethodCommonMessageFunction(InvokeCommonMessageParametersData data) {
        return exception -> CoreUtilities.areAnyNull(data, exception) ? getInvokeMethodCommonMessage(data, exception) : CoreFormatterConstants.PARAMETER_ISSUES;
    }

    static Function<Exception, String> getInvokeMethodParameterizedMessageFunction(InvokeParameterizedMessageData data) {
        return exception -> CoreUtilities.areAnyNull(data, exception) ? getInvokeMethodParameterizedMessage(data, exception) : "Data or exception" + CoreFormatterConstants.WAS_NULL;
    }

    static String isNullOrEmptyListMessageWithName(List<?> list, String parameterName) {
        var message = isNullMessageWithName(list, parameterName);
        final var name = (isBlank(parameterName) ? "List" : parameterName);
        if (isBlank(message)) {
            message += list.isEmpty() ? name + " was empty" + CoreFormatterConstants.END_LINE : CoreFormatterConstants.EMPTY;
        }

        return getNamedErrorMessageOrEmpty("isNullOrEmptyListMessage", message);
    }

    static String isNullOrEmptyListMessage(List<?> list) {
        return isNullOrEmptyListMessageWithName(list, "List");
    }

    static String getContainsIndexMessageWithName(List<?> list, int index, String parameterName) {
        var message = isNullOrEmptyListMessageWithName(list, parameterName);
        if (isBlank(message)) {
            if (!AmountPredicates.hasIndex(list::size, index)) {
                message += "List doesn't contain index: " + index;
            }
        }

        return getNamedErrorMessageOrEmpty("getContainsIndexMessageWithName", message);
    }

    static String getContainsIndexMessage(List<?> list, int index) {
        return getContainsIndexMessageWithName(list, index, "List");
    }

    static <T> String getListEmptyMessage(DecoratedList<T> list, String parameterName) {
        final var name = isBlank(parameterName) ? "List" : parameterName;
        var message = "";
        if (list.isNull()) {
            message += name + CoreFormatterConstants.WAS_NULL;
        }

        if (isBlank(message) && list.isEmpty()) {
            message += name + "was empty" + CoreFormatterConstants.END_LINE;
        }

        return isNotBlank(message) ? "getListEmptyMessage" + message : CoreFormatterConstants.EMPTY;
    }

    static <T> String getListNotEnoughMessage(DecoratedList<T> list, String parameterName, int expected) {
        final var name = isBlank(parameterName) ? "List" : parameterName;
        var message = "";
        if (list.isNull()) {
            message += name + CoreFormatterConstants.WAS_NULL;
        }

        if (isBlank(message) && !list.hasAtleast(expected)) {
            message += name + "length was less than " + expected + CoreFormatterConstants.END_LINE;
        }

        return isNotBlank(message) ? "getListEmptyMessage" + message : CoreFormatterConstants.EMPTY;
    }

    static Function<Boolean, String> isFormatterNullAndMessageBlank() {
        return status -> CoreFormatterConstants.EXECUTION_STATUS_COLON_SPACE + status + CoreFormatterConstants.END_LINE;
    }

    static Function<Boolean, String> isFormatterNull(String message) {
        return status -> CoreFormatterConstants.EXECUTION_STATUS_COLON_SPACE + status + CoreFormatterConstants.END_LINE + "Message: " + message;
    }

    static Function<Boolean, String> isMessageBlank(BiFunction<String, Boolean, String> formatter) {
        return status -> CoreFormatterConstants.EXECUTION_STATUS_COLON_SPACE + status + "Message was empty, please fix - result: " + formatter.apply(CoreFormatterConstants.EMPTY, status) + CoreFormatterConstants.END_LINE;
    }

    static Function<Boolean, String> isFormatterAndMessageValid(BiFunction<String, Boolean, String> formatter, String message) {
        return status -> formatter.apply(message, status);
    }

    static String isNegativeMessageWithName(int value, String parameterName) {
        final var name = isNotBlank(parameterName) ? parameterName : "Value parameter";
        final var status = BasicPredicates.isNegative(value);
        var message = "";
        if (status) {
            message += name + "(\"" + value +"\") is negative" + CoreFormatterConstants.END_LINE;
        }

        return getNamedErrorMessageOrEmpty("isNegativeMessage", message);
    }

    static String isNegativeMessage(int value) {
        return isNegativeMessageWithName(value, "Value parameter");
    }

    static String isNullOrEmptyMessageWithName(IEmptiable emptiable, String parameterName) {
        final var baseName = isNotBlank(parameterName) ? parameterName : "Emptiable";
        var message = isNullMessageWithName(emptiable, baseName);
        if (isBlank(message)) {
            if (emptiable.isEmpty()) {
                message += baseName + " was null or empty" + CoreFormatterConstants.END_LINE;
            }
        }

        return getNamedErrorMessageOrEmpty("isNullOrEmpty", message);
    }

    static String isNullOrEmptyMessage(IEmptiable emptiable) {
        return isNullOrEmptyMessageWithName(emptiable, "Emptiable");
    }

    static String getExecutionResultKey(String name, int index) {
        return name + "-" + index;
    }

    static String isEqualMessage(Object left, String leftDescriptor, Object right, String rightDescriptor) {
        var message = isNullMessageWithName(left, "Left Object") + isNullMessageWithName(right, "Right Object");
        if (isBlank(message) && !Objects.equals(left, right)) {
            message += (
                (
                    CoreUtilities.areAnyBlank(leftDescriptor, rightDescriptor) ? "The two objects" : (leftDescriptor + " and " + rightDescriptor)
                ) + " are equal" + CoreFormatterConstants.END_LINE
            );
        }

        return getNamedErrorMessageOrEmpty("isEqualMessage", message);
    }

    static String isEqualMessage(Object left, Object right) {
        return isEqualMessage(left, CoreFormatterConstants.EMPTY, right, CoreFormatterConstants.EMPTY);
    }

    static String getAreValidPadParametersMessage(String value, String parameterName, String pad) {
        return getNamedErrorMessageOrEmpty("getAreValidPadParametersMessage",  isNullMessage(value) + isNullMessageWithName(parameterName, "Parameter name value") + isNullMessageWithName(pad, "Pad value"));
    }

    private static String notPaddedCommon(String location, String pad) {
        return CoreFormatterConstants.VALUE_DOESNT + location + CoreFormatterConstants.WITH_PAD_VALUE + " \"" + pad + "\"" + CoreFormatterConstants.END_LINE;
    }

    static String isStartPaddedWith(String value, String parameterName, String pad) {
        var message = getAreValidPadParametersMessage(value, parameterName, pad);
        if (isBlank(message)) {
            message += !value.startsWith(pad) ? parameterName + " " + notPaddedCommon(CoreFormatterConstants.START, pad) : CoreFormatterConstants.EMPTY;
        }

        return getNamedErrorMessageOrEmpty("isStartPaddedWith", message);
    }

    static String isEndPaddedWith(String value, String parameterName, String pad) {
        var message = getAreValidPadParametersMessage(value, parameterName, pad);
        if (isBlank(message)) {
            message += !value.endsWith(pad) ? parameterName + " " + notPaddedCommon(CoreFormatterConstants.END, pad) : CoreFormatterConstants.EMPTY;
        }

        return getNamedErrorMessageOrEmpty("isEndPaddedWith", message);
    }

    static String isPadded(String value, String parameterName, String pad) {
        var message = getAreValidPadParametersMessage(value, parameterName, pad);
        if (isBlank(message)) {
            message += isBlankMessageWithName(value, parameterName);
        }

        if (isBlank(message)) {
            message += (
                isStartPaddedWith(value, parameterName, pad) +
                isEndPaddedWith(value, parameterName, pad)
            );
        }

        return getNamedErrorMessageOrEmpty("isPadded", message);
    }

    static <T> String getValidNonFalseAndValidContainedMessage(Data<T> data, Function<T, String> validator) {
        var message = isInvalidOrFalseMessage(data);
        if (isBlank(message)) {
            final var validatorMessage = validator.apply(data.object);
            if (isNotBlank(validatorMessage)) {
                message += validator.apply(data.object);
            }
        }

        return getNamedErrorMessageOrEmpty("getValidNonFalseAndValidContainedMessage", message);
    }

    static <T> String getValidNonFalseAndValidContainedMessage(Data<T> data, Predicate<T> validator) {
        var message = isInvalidOrFalseMessage(data);
        if (isBlank(message)) {
            message += isFalseMessageWithName(validator.test(data.object), "Validator test");
        }

        return getNamedErrorMessageOrEmpty("getValidNonFalseAndValidContainedMessage", message);
    }

    static <T> Function<Data<T>, String> getValidNonFalseAndValidContainedMessage(Function<T, String> validator) {
        return data -> getValidNonFalseAndValidContainedMessage(data, validator);
    }

    static <T> Function<Data<T>, String> getValidNonFalseAndValidContainedMessage(Predicate<T> validator) {
        return data -> getValidNonFalseAndValidContainedMessage(data, validator);
    }

    static <T> String isListTypeEqual(String object, String expected) {
        return isEqualMessage(object, CoreFormatterConstants.ACTUAL_LIST_TYPE, expected, CoreFormatterConstants.EXPECTED_LIST_TYPE);
    }

    static Function<String, String> isListTypeEqual(String expected) {
        return value -> CoreFormatter.isEqualMessage(value, expected);
    }

    static <T, U> String isValidTypedNonEmptyListMessage(Data<DecoratedList<?>> listData, Class<U> clazz) {
        var message = isNullMessage(clazz);
        if (isBlank(message)) {
            message += getValidNonFalseAndValidContainedMessage(
                listData,
                isListTypeEqual(clazz.getTypeName()).compose(DecoratedList::getType)
            );
        }

        if (isBlank(message)) {
            message += isNullOrEmptyListMessageWithName(listData.object, "List");
        }

        return getNamedErrorMessageOrEmpty("isOfTypeNonEmptyMessage", message);
    }

    static <T, U> Function<Data<DecoratedList<?>>, String> isValidTypedNonEmptyListMessage(Class<U> clazz) {
        return list -> isValidTypedNonEmptyListMessage(list, clazz);
    }

    static <T> String areInvalidParametersMessage(Collection<T> data, Predicate<T> validator) {
        var message = isEmptyMessage(data) + isNullMessageWithName(validator, "Validator");
        var sb = new StringBuilder();
        if (isBlank(message)) {
            var index = 0;
            for(var parameters : data) {
                sb.append(isInvalidMessage(validator.test(parameters), index + ". parameters data"));
            }
        }

        message += sb.toString();
        return getNamedErrorMessageOrEmpty("areInvalidParametersMessage", message);
    }

    static String getMethodMessageDataFormatted(String nameof, String message) {
        final var separator = ": ";
        return StringUtilities.startsWithCaseInsensitive(message, nameof + separator) ? message : nameof + separator + message;
    }
}
