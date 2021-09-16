package com.neathorium.thorium.core.namespaces.predicates;

import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;
import com.neathorium.thorium.core.extensions.namespaces.predicates.BasicPredicates;
import com.neathorium.thorium.core.records.Data;
import com.neathorium.thorium.core.records.executor.ExecutionResultData;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface DataPredicates {
    static boolean isInitialized(Data<?> data) {
        return NullableFunctions.isNotNull(data);
    }

    static boolean isValid(Data<?> data) {
        return isInitialized(data) && NullableFunctions.isNotNull(data.exception) && isNotBlank(data.exceptionMessage) && MethodMessageDataPredicates.isValid(data.message);
    }

    static boolean isInvalid(Data<?> data) {
        return !isValid(data);
    }

    static boolean isFalse(Data<?> data) {
        return DataPredicates.isValid(data) && CoreUtilities.isFalse(data.object);
    }

    static boolean isTrue(Data<?> data) {
        return DataPredicates.isValid(data) && CoreUtilities.isTrue(data.object);
    }

    static boolean isFalse(Data<?> data, int index, int length) {
        return isFalse(data) && BasicPredicates.isSmallerThan(index, length);
    }

    static boolean isTrue(Data<?> data, int index, int length) {
        return isTrue(data) && BasicPredicates.isSmallerThan(index, length);
    }

    static boolean isValidNonFalse(Data<?> data) {
        return isValid(data) && CoreUtilities.isTrue(data.status);
    }

    static boolean isValidNonFalseAndNonNullContained(Data<?> data) {
        return isValidNonFalse(data) && NullableFunctions.isNotNull(data.object);
    }

    static boolean isInvalidOrFalse(Data<?> data) {
        return !isValidNonFalse(data);
    }

    static boolean isExecutionValidNonFalse(Data<ExecutionResultData<Object>> data) {
        return isValid(data) && CoreUtilities.isTrue(data.status);
    }

    static boolean isExecutionInvalidOrFalse(Data<ExecutionResultData<Object>> data) {
        return !isValidNonFalse(data);
    }

    static boolean isValidAndFalse(Data<?> data) {
        return isValid(data) && CoreUtilities.isFalse(data.status);
    }

    static <T> boolean isValidNonFalseAndValidContained(Data<T> data, Predicate<T> validator) {
        return isValidNonFalse(data) && validator.test(data.object);
    }

    static <T> boolean isValidNonFalseAndValidContained(Data<T> data, Function<T, String> validator) {
        return isValidNonFalse(data) && isNotBlank(validator.apply(data.object));
    }

    static <T> Predicate<Data<T>> isValidNonFalseAndValidContains(Function<T, String> validator) {
        return data -> isValidNonFalseAndValidContained(data, validator);
    }

    static <T> Predicate<Data<T>> isValidNonFalseAndValidContains(Predicate<T> validator) {
        return data -> isValidNonFalseAndValidContained(data, validator);
    }
}
