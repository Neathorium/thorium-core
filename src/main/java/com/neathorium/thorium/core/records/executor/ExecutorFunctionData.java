package com.neathorium.thorium.core.records.executor;

import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.java.extensions.interfaces.functional.QuadFunction;
import com.neathorium.thorium.java.extensions.interfaces.functional.QuadPredicate;
import com.neathorium.thorium.java.extensions.interfaces.functional.TriPredicate;
import com.neathorium.thorium.java.extensions.interfaces.functional.boilers.IGetMessage;

import java.util.Objects;
import java.util.function.Predicate;

public class ExecutorFunctionData {
    public final IGetMessage messageData;
    public final TriPredicate<Data<?>, Integer, Integer> breakCondition;
    public final QuadPredicate<ExecutionStateData, Integer, Integer, Integer> endCondition;
    public final QuadFunction<ExecutionStateData, String, Integer, Integer, String> endMessageHandler;
    public final Predicate<Data<?>> filterCondition;

    public ExecutorFunctionData(
        IGetMessage messageData,
        TriPredicate<Data<?>, Integer, Integer> breakCondition,
        QuadPredicate<ExecutionStateData, Integer, Integer, Integer> endCondition,
        QuadFunction<ExecutionStateData, String, Integer, Integer, String> endMessageHandler,
        Predicate<Data<?>> filterCondition
    ) {
        this.messageData = messageData;
        this.breakCondition = breakCondition;
        this.endCondition = endCondition;
        this.endMessageHandler = endMessageHandler;
        this.filterCondition = filterCondition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (ExecutorFunctionData) o;
        return (
            Objects.equals(messageData, that.messageData) &&
            Objects.equals(breakCondition, that.breakCondition) &&
            Objects.equals(endCondition, that.endCondition) &&
            Objects.equals(endMessageHandler, that.endMessageHandler) &&
            Objects.equals(filterCondition, that.filterCondition)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageData, breakCondition, endCondition, endMessageHandler, filterCondition);
    }
}
