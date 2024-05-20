package com.neathorium.thorium.core.executor.namespaces.step;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.executor.exceptions.ParallelStepExecutionException;
import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ParallelStepExecutionExceptionFactory {
    static ParallelStepExecutionException getWith() {
        return new ParallelStepExecutionException();
    }

    static ParallelStepExecutionException getWith(String message, Map<Integer, Throwable> map) {
        final var nameof = "ParallelStepExecutionExceptionFactory.getWith";
        final var errors = (
            CoreFormatter.isBlankMessageWithName(message, "Exception message") +
            CoreFormatter.isNullMessageWithName(map, "Exception causes map")
        );
        if (StringUtils.isNotBlank(errors)) {
            throw new IllegalArgumentException(nameof + ": " + errors);
        }

        return new ParallelStepExecutionException(message, map);
    }

    static ParallelStepExecutionException getWith(String message, List<Throwable> list) {
        final Map<Integer, Throwable> map = new HashMap<>();
        final var length = list.size();
        for (var index = 0; index < length; ++index) {
            map.put(index, list.get(index));
        }

        return ParallelStepExecutionExceptionFactory.getWith(message, map);
    }

    static ParallelStepExecutionException getWith(String message, Throwable... throwables) {
        final List<Throwable> list = NullablePredicates.isNotNull(throwables) ? Arrays.asList(throwables) : List.of();
        return ParallelStepExecutionExceptionFactory.getWith(message, list);
    }

    static ParallelStepExecutionException getWith(List<Throwable> list) {
        final var message = "Exception occurred during parallel step execution" + CoreFormatterConstants.END_LINE;
        return ParallelStepExecutionExceptionFactory.getWith(message, list);
    }

    static ParallelStepExecutionException getWith(Map<Integer, Throwable> map) {
        final var message = "Exception occurred during parallel step execution" + CoreFormatterConstants.END_LINE;
        return ParallelStepExecutionExceptionFactory.getWith(message, map);
    }

    static ParallelStepExecutionException getWith(Throwable... throwables) {
        final List<Throwable> list = NullablePredicates.isNotNull(throwables) ? Arrays.asList(throwables) : List.of();
        return ParallelStepExecutionExceptionFactory.getWith(list);
    }
}
