package com.neathorium.thorium.core.records.executor;

import com.neathorium.thorium.core.extensions.interfaces.functional.QuadFunction;
import com.neathorium.thorium.core.extensions.interfaces.functional.QuadPredicate;
import com.neathorium.thorium.core.records.command.CommandRangeData;

import java.util.Objects;

public class ExecuteParametersData {
    public final CommandRangeData range;
    public final QuadPredicate<ExecutionStateData, Integer, Integer, Integer> endCondition;
    public final QuadFunction<ExecutionStateData, String, Integer, Integer, String> messageHandler;

    public ExecuteParametersData(CommandRangeData range, QuadPredicate<ExecutionStateData, Integer, Integer, Integer> endCondition, QuadFunction<ExecutionStateData, String, Integer, Integer, String> messageHandler) {
        this.range = range;
        this.endCondition = endCondition;
        this.messageHandler = messageHandler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (ExecuteParametersData) o;
        return Objects.equals(range, that.range) && Objects.equals(endCondition, that.endCondition) && Objects.equals(messageHandler, that.messageHandler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(range, endCondition, messageHandler);
    }
}
