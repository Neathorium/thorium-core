package com.neathorium.thorium.core.executor.namespaces;

import com.neathorium.thorium.core.constants.ExecutorConstants;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.namespaces.predicates.ExecutorPredicates;
import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;
import com.neathorium.thorium.core.records.SimpleMessageData;
import com.neathorium.thorium.core.records.executor.ExecuteParametersData;
import com.neathorium.thorium.core.records.executor.ExecutionStateData;
import com.neathorium.thorium.core.records.executor.ExecutorFunctionData;
import com.neathorium.thorium.java.extensions.interfaces.functional.QuadFunction;
import com.neathorium.thorium.java.extensions.interfaces.functional.QuadPredicate;
import com.neathorium.thorium.java.extensions.interfaces.functional.TriPredicate;
import com.neathorium.thorium.java.extensions.interfaces.functional.boilers.IGetMessage;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public interface ExecutorFunctionDataFactory {
    static ExecutorFunctionData getWith(
        IGetMessage messageData,
        TriPredicate<Data<?>, Integer, Integer> breakCondition,
        QuadPredicate<ExecutionStateData, Integer, Integer, Integer> endCondition,
        QuadFunction<ExecutionStateData, String, Integer, Integer, String> endMessageHandler,
        BiFunction<String, Integer, String> keyFormatter,
        Predicate<Data<?>> filterCondition
    ) {
        return new ExecutorFunctionData(
            NullablePredicates.isNotNull(messageData) ? messageData : new SimpleMessageData(),
            NullablePredicates.isNotNull(breakCondition) ? breakCondition : ExecutorPredicates::isExecuting,
            NullablePredicates.isNotNull(endCondition) ? endCondition : ExecutorFunctionDataFunctions::isAllDone,
            NullablePredicates.isNotNull(endMessageHandler) ? endMessageHandler : CoreFormatter::getExecutionEndMessage,
            NullablePredicates.isNotNull(keyFormatter) ? keyFormatter : CoreFormatter::getExecutionResultKey,
            NullablePredicates.isNotNull(filterCondition) ? filterCondition : NullablePredicates::isNotNull
        );
    }

    static ExecutorFunctionData getWithDefaultBreakCondition(
        IGetMessage messageData,
        QuadPredicate<ExecutionStateData, Integer, Integer, Integer> endCondition,
        QuadFunction<ExecutionStateData, String, Integer, Integer, String> endMessageHandler,
        BiFunction<String, Integer, String> keyFormatter,
        Predicate<Data<?>> filterCondition
    ) {
        return ExecutorFunctionDataFactory.getWith(messageData, ExecutorPredicates::isExecuting, endCondition, endMessageHandler, keyFormatter, filterCondition);
    }

    static ExecutorFunctionData getWithDefaultEndCondition(
        IGetMessage messageData,
        TriPredicate<Data<?>, Integer, Integer> breakCondition,
        QuadFunction<ExecutionStateData, String, Integer, Integer, String> endMessageHandler,
        BiFunction<String, Integer, String> keyFormatter,
        Predicate<Data<?>> filterCondition
    ) {
        return ExecutorFunctionDataFactory.getWith(messageData, breakCondition, ExecutorFunctionDataFunctions::isAllDone, endMessageHandler, keyFormatter, filterCondition);
    }

    static ExecutorFunctionData getWithDefaultEndMessageHandler(
        IGetMessage messageData,
        TriPredicate<Data<?>, Integer, Integer> breakCondition,
        QuadPredicate<ExecutionStateData, Integer, Integer, Integer> endCondition,
        BiFunction<String, Integer, String> keyFormatter,
        Predicate<Data<?>> filterCondition
    ) {
        return ExecutorFunctionDataFactory.getWith(messageData, breakCondition, endCondition, CoreFormatter::getExecutionEndMessage, keyFormatter, filterCondition);
    }

    static ExecutorFunctionData getWithDefaultFilterCondition(
        IGetMessage messageData,
        TriPredicate<Data<?>, Integer, Integer> breakCondition,
        QuadPredicate<ExecutionStateData, Integer, Integer, Integer> endCondition,
        QuadFunction<ExecutionStateData, String, Integer, Integer, String> endMessageHandler,
        BiFunction<String, Integer, String> keyFormatter
    ) {
        return ExecutorFunctionDataFactory.getWith(messageData, breakCondition, endCondition, endMessageHandler, keyFormatter, NullablePredicates::isNotNull);
    }

    static ExecutorFunctionData getWithDefaultEndMessageHandlerFilterCondition(
        IGetMessage messageData,
        TriPredicate<Data<?>, Integer, Integer> breakCondition,
        QuadPredicate<ExecutionStateData, Integer, Integer, Integer> endCondition,
        BiFunction<String, Integer, String> keyFormatter
    ) {
        return ExecutorFunctionDataFactory.getWith(messageData, breakCondition, endCondition, CoreFormatter::getExecutionEndMessage, keyFormatter, NullablePredicates::isNotNull);
    }

    static ExecutorFunctionData getWithDefaultEndConditionFilterCondition(
        IGetMessage messageData,
        TriPredicate<Data<?>, Integer, Integer> breakCondition,
        QuadFunction<ExecutionStateData, String, Integer, Integer, String> endMessageHandler,
        BiFunction<String, Integer, String> keyFormatter
    ) {
        return ExecutorFunctionDataFactory.getWith(messageData, breakCondition, ExecutorFunctionDataFunctions::isAllDone, endMessageHandler, keyFormatter, NullablePredicates::isNotNull);
    }

    static ExecutorFunctionData getWithDefaultEndConditionEndMessageHandler(
        IGetMessage messageData,
        TriPredicate<Data<?>, Integer, Integer> breakCondition,
        Predicate<Data<?>> filterCondition,
        BiFunction<String, Integer, String> keyFormatter
    ) {
        return ExecutorFunctionDataFactory.getWith(messageData, breakCondition, ExecutorFunctionDataFunctions::isAllDone, CoreFormatter::getExecutionEndMessage, keyFormatter, filterCondition);
    }

    static ExecutorFunctionData getWithDefaultBreakConditionFilterCondition(
        IGetMessage messageData,
        QuadPredicate<ExecutionStateData, Integer, Integer, Integer> endCondition,
        QuadFunction<ExecutionStateData, String, Integer, Integer, String> endMessageHandler,
        BiFunction<String, Integer, String> keyFormatter
    ) {
        return ExecutorFunctionDataFactory.getWith(messageData, ExecutorPredicates::isExecuting, endCondition, endMessageHandler, keyFormatter, NullablePredicates::isNotNull);
    }

    static ExecutorFunctionData getWithDefaultBreakConditionEndMessageHandler(
        IGetMessage messageData,
        QuadPredicate<ExecutionStateData, Integer, Integer, Integer> endCondition,
        Predicate<Data<?>> filterCondition,
        BiFunction<String, Integer, String> keyFormatter
    ) {
        return ExecutorFunctionDataFactory.getWith(messageData, ExecutorPredicates::isExecuting, endCondition, CoreFormatter::getExecutionEndMessage, keyFormatter, filterCondition);
    }

    static ExecutorFunctionData getWithDefaultBreakConditionEndCondition(
        IGetMessage messageData,
        QuadFunction<ExecutionStateData, String, Integer, Integer, String> endMessageHandler,
        Predicate<Data<?>> filterCondition,
        BiFunction<String, Integer, String> keyFormatter
    ) {
        return ExecutorFunctionDataFactory.getWith(messageData, ExecutorPredicates::isExecuting, ExecutorFunctionDataFunctions::isAllDone, endMessageHandler, keyFormatter, filterCondition);
    }

    static ExecutorFunctionData getWithSpecificMessageDataAndBreakCondition(IGetMessage messageData, TriPredicate<Data<?>, Integer, Integer> breakCondition, ExecuteParametersData data) {
        return ExecutorFunctionDataFactory.getWith(messageData, breakCondition, data.endCondition, data.messageHandler, CoreFormatter::getExecutionResultKey, NullablePredicates::isNotNull);
    }

    static ExecutorFunctionData getWithSpecificMessageDataAndBreakCondition(IGetMessage messageData, TriPredicate<Data<?>, Integer, Integer> breakCondition) {
        return ExecutorFunctionDataFactory.getWithSpecificMessageDataAndBreakCondition(messageData, breakCondition, ExecutorConstants.DEFAULT_EXECUTION_DATA);
    }

    static ExecutorFunctionData getWithSpecificMessageAndBreakCondition(String message, TriPredicate<Data<?>, Integer, Integer> breakCondition) {
        return ExecutorFunctionDataFactory.getWithSpecificMessageDataAndBreakCondition(new SimpleMessageData(message), breakCondition, ExecutorConstants.DEFAULT_EXECUTION_DATA);
    }

    static ExecutorFunctionData getWithSpecificMessageDataAndEndCondition(IGetMessage messageData, QuadPredicate<ExecutionStateData, Integer, Integer, Integer> endCondition) {
        return ExecutorFunctionDataFactory.getWith(messageData, ExecutorPredicates::isExecuting, endCondition, CoreFormatter::getExecutionEndMessage, CoreFormatter::getExecutionResultKey, NullablePredicates::isNotNull);
    }

    static ExecutorFunctionData getWithSpecificMessageDataAndEndMessageHandler(IGetMessage messageData, QuadFunction<ExecutionStateData, String, Integer, Integer, String> endMessageHandler) {
        return ExecutorFunctionDataFactory.getWith(messageData, ExecutorPredicates::isExecuting, ExecutorFunctionDataFunctions::isAllDone, endMessageHandler, CoreFormatter::getExecutionResultKey, NullablePredicates::isNotNull);
    }

    static ExecutorFunctionData getWithSpecificMessageDataAndFilterCondition(IGetMessage messageData, Predicate<Data<?>> filterCondition) {
        return ExecutorFunctionDataFactory.getWith(messageData, ExecutorPredicates::isExecuting, ExecutorFunctionDataFunctions::isAllDone, CoreFormatter::getExecutionEndMessage, CoreFormatter::getExecutionResultKey, filterCondition);
    }

    static ExecutorFunctionData getWithExecuteParametersDataAndDefaultExitCondition(IGetMessage messageData, ExecuteParametersData data) {
        return ExecutorFunctionDataFactory.getWith(messageData, ExecutorPredicates::isExecuting, data.endCondition, data.messageHandler, CoreFormatter::getExecutionResultKey, NullablePredicates::isNotNull);
    }

    static ExecutorFunctionData getWithSpecificMessageData(IGetMessage messageData) {
        return ExecutorFunctionDataFactory.getWithExecuteParametersDataAndDefaultExitCondition(messageData, ExecutorConstants.DEFAULT_EXECUTION_DATA);
    }

    static ExecutorFunctionData getWithSpecificMessage(String message) {
        return ExecutorFunctionDataFactory.getWithExecuteParametersDataAndDefaultExitCondition(new SimpleMessageData(message), ExecutorConstants.DEFAULT_EXECUTION_DATA);
    }

    static ExecutorFunctionData getWithDefaultMessageData(
        TriPredicate<Data<?>, Integer, Integer> breakCondition,
        QuadPredicate<ExecutionStateData, Integer, Integer, Integer> endCondition,
        QuadFunction<ExecutionStateData, String, Integer, Integer, String> endMessageHandler,
        Predicate<Data<?>> filterCondition
    ) {
        return ExecutorFunctionDataFactory.getWith(new SimpleMessageData(), breakCondition, endCondition, endMessageHandler, CoreFormatter::getExecutionResultKey, filterCondition);
    }

    static ExecutorFunctionData getWithSpecificBreakCondition(TriPredicate<Data<?>, Integer, Integer> breakCondition) {
        return ExecutorFunctionDataFactory.getWithDefaultMessageData(breakCondition, ExecutorFunctionDataFunctions::isAllDone, CoreFormatter::getExecutionEndMessage, NullablePredicates::isNotNull);
    }

    static ExecutorFunctionData getWithSpecificEndCondition(QuadPredicate<ExecutionStateData, Integer, Integer, Integer> endCondition) {
        return ExecutorFunctionDataFactory.getWithDefaultMessageData(ExecutorPredicates::isExecuting, endCondition, CoreFormatter::getExecutionEndMessage, NullablePredicates::isNotNull);
    }

    static ExecutorFunctionData getWithSpecificEndMessageHandler(QuadFunction<ExecutionStateData, String, Integer, Integer, String> endMessageHandler) {
        return ExecutorFunctionDataFactory.getWithDefaultMessageData(ExecutorPredicates::isExecuting, ExecutorFunctionDataFunctions::isAllDone, endMessageHandler, NullablePredicates::isNotNull);
    }

    static ExecutorFunctionData getWithSpecificFilterCondition(Predicate<Data<?>> filterCondition) {
        return ExecutorFunctionDataFactory.getWithDefaultMessageData(ExecutorPredicates::isExecuting, ExecutorFunctionDataFunctions::isAllDone, CoreFormatter::getExecutionEndMessage, filterCondition);
    }

    static ExecutorFunctionData getWithDefaultMessageDataFilterCondition(
        TriPredicate<Data<?>, Integer, Integer> breakCondition,
        QuadPredicate<ExecutionStateData, Integer, Integer, Integer> endCondition,
        QuadFunction<ExecutionStateData, String, Integer, Integer, String> endMessageHandler
    ) {
        return ExecutorFunctionDataFactory.getWithDefaultMessageData(breakCondition, endCondition, endMessageHandler, NullablePredicates::isNotNull);
    }

    static ExecutorFunctionData getWithDefaultMessageDataEndMessageHandler(
        TriPredicate<Data<?>, Integer, Integer> breakCondition,
        QuadPredicate<ExecutionStateData, Integer, Integer, Integer> endCondition,
        Predicate<Data<?>> filterCondition
    ) {
        return ExecutorFunctionDataFactory.getWithDefaultMessageData(breakCondition, endCondition, CoreFormatter::getExecutionEndMessage, filterCondition);
    }

    static ExecutorFunctionData getWithDefaultMessageDataEndCondition(
        TriPredicate<Data<?>, Integer, Integer> breakCondition,
        QuadFunction<ExecutionStateData, String, Integer, Integer, String> endMessageHandler,
        Predicate<Data<?>> filterCondition
    ) {
        return ExecutorFunctionDataFactory.getWithDefaultMessageData(breakCondition, ExecutorFunctionDataFunctions::isAllDone, endMessageHandler, filterCondition);
    }

    static ExecutorFunctionData getWithSpecificBreakConditionAndEndCondition(
        TriPredicate<Data<?>, Integer, Integer> breakCondition,
        QuadPredicate<ExecutionStateData, Integer, Integer, Integer> endCondition
    ) {
        return ExecutorFunctionDataFactory.getWithDefaultMessageData(breakCondition, endCondition, CoreFormatter::getExecutionEndMessage, NullablePredicates::isNotNull);
    }

    static ExecutorFunctionData getWithSpecificBreakConditionAndEndMessageHandler(
        TriPredicate<Data<?>, Integer, Integer> breakCondition,
        QuadFunction<ExecutionStateData, String, Integer, Integer, String> endMessageHandler
    ) {
        return ExecutorFunctionDataFactory.getWithDefaultMessageData(breakCondition, ExecutorFunctionDataFunctions::isAllDone, endMessageHandler, NullablePredicates::isNotNull);
    }

    static ExecutorFunctionData getWithSpecificBreakConditionAndFilterCondition(TriPredicate<Data<?>, Integer, Integer> breakCondition, Predicate<Data<?>> filterCondition) {
        return ExecutorFunctionDataFactory.getWithDefaultMessageData(breakCondition, ExecutorFunctionDataFunctions::isAllDone, CoreFormatter::getExecutionEndMessage, filterCondition);
    }

    static ExecutorFunctionData getWithDefaultMessageDataBreakCondition(
        QuadPredicate<ExecutionStateData, Integer, Integer, Integer> endCondition,
        QuadFunction<ExecutionStateData, String, Integer, Integer, String> endMessageHandler,
        Predicate<Data<?>> filterCondition
    ) {
        return ExecutorFunctionDataFactory.getWithDefaultMessageData(ExecutorPredicates::isExecuting, endCondition, endMessageHandler, filterCondition);
    }

    static ExecutorFunctionData getWithSpecificEndConditionAndEndMessageHandler(
        QuadPredicate<ExecutionStateData, Integer, Integer, Integer> endCondition,
        QuadFunction<ExecutionStateData, String, Integer, Integer, String> endMessageHandler
    ) {
        return ExecutorFunctionDataFactory.getWithDefaultMessageData(ExecutorPredicates::isExecuting, endCondition, endMessageHandler, NullablePredicates::isNotNull);
    }

    static ExecutorFunctionData getWithSpecificEndConditionAndFilterCondition(QuadPredicate<ExecutionStateData, Integer, Integer, Integer> endCondition, Predicate<Data<?>> filterCondition) {
        return ExecutorFunctionDataFactory.getWithDefaultMessageData(ExecutorPredicates::isExecuting, endCondition, CoreFormatter::getExecutionEndMessage, filterCondition);
    }

    static ExecutorFunctionData getWithSpecificEndMessageHandlerAndFilterCondition(QuadFunction<ExecutionStateData, String, Integer, Integer, String> endMessageHandler, Predicate<Data<?>> filterCondition) {
        return ExecutorFunctionDataFactory.getWithDefaultMessageData(ExecutorPredicates::isExecuting, ExecutorFunctionDataFunctions::isAllDone, endMessageHandler, filterCondition);
    }

    static ExecutorFunctionData getWithDefaultExitCondition(IGetMessage messageData, QuadFunction<ExecutionStateData, String, Integer, Integer, String> endMessageHandler) {
        return ExecutorFunctionDataFactory.getWithSpecificMessageDataAndEndMessageHandler(messageData, endMessageHandler);
    }

    static ExecutorFunctionData getWithDefaultExitConditionAndMessageData(QuadFunction<ExecutionStateData, String, Integer, Integer, String> endMessageHandler) {
        return ExecutorFunctionDataFactory.getWithDefaultExitCondition(new SimpleMessageData(), endMessageHandler);
    }
}
