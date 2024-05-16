package com.neathorium.thorium.core.executor.exceptions;

import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParallelStepExecutionException extends RuntimeException {
    private final Map<Integer, Throwable> causes;

    public ParallelStepExecutionException() {
        super();
        causes = Collections.emptyMap();
    }
    public ParallelStepExecutionException(String message) {
        super(message);
        causes = Collections.emptyMap();
    }

    public ParallelStepExecutionException(String message, Throwable cause) {
        super(message, cause);
        causes = Map.ofEntries(
            Map.entry(0, cause)
        );
    }

    public ParallelStepExecutionException(String message, Throwable cause, List<Throwable> list) {
        super(message, cause);
        causes = new HashMap<>();
        if (NullablePredicates.isNotNull(list)) {
            for (var index = 0; index < list.size(); ++index) {
                causes.put(index, list.get(index));
            }
        }
    }

    public ParallelStepExecutionException(String message, Map<Integer, Throwable> map) {
        super(message, NullablePredicates.isNotNull(map) ? map.getOrDefault(0, null) : null);
        causes = NullablePredicates.isNotNull(map) ? map : Collections.emptyMap();
    }
}
