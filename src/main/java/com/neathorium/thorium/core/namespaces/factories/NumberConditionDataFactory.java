package com.neathorium.thorium.core.namespaces.factories;

import com.neathorium.thorium.core.constants.formatter.NumberConditionConstants;
import com.neathorium.thorium.core.records.formatter.NumberConditionData;

import java.util.function.BiPredicate;

public interface NumberConditionDataFactory {
    static NumberConditionData getWith(String nameof, String descriptor, String parameterName, BiPredicate<Integer, Integer> function) {
        return new NumberConditionData(nameof, descriptor, parameterName, function);
    }

    static NumberConditionData getWithDefaultName(String descriptor, String parameterName, BiPredicate<Integer, Integer> function) {
        return getWith(NumberConditionConstants.NAME, descriptor, parameterName, function);
    }

    static NumberConditionData getWithDefaultParameterName(String nameof, String descriptor, BiPredicate<Integer, Integer> function) {
        return getWith(nameof, descriptor, NumberConditionConstants.PARAMETER_NAME, function);
    }

    static NumberConditionData getWithDefaultNameAndParameterName(String descriptor, BiPredicate<Integer, Integer> function) {
        return getWithDefaultParameterName(NumberConditionConstants.NAME, descriptor, function);
    }
}
