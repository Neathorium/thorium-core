package com.neathorium.thorium.core.namespaces.validators;

import com.neathorium.thorium.core.namespaces.validators.wait.WaitParameterValidators;
import com.neathorium.thorium.core.records.Data;
import com.neathorium.thorium.core.records.wait.WaitTimeData;

import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.apache.commons.lang3.StringUtils.isBlank;

public interface RepeatWaitValidators {
    static <T, V> String validateUntilParameters(Function<T, Data<?>>[] functions, BiPredicate<Predicate<Data<?>>, Data<?>[]> continueCondition, WaitTimeData timeData) {
        final var message = (
            CoreFormatter.isNullMessageWithName(functions, "Functions") +
            CoreFormatter.isNullMessageWithName(continueCondition, "ContinueCondition") +
            WaitParameterValidators.validateWaitTimeData(timeData)
        );

        return isBlank(message) ? message : ("Wait.until: " + message);
    }
}
