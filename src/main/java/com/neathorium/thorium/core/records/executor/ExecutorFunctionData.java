package com.neathorium.thorium.core.records.executor;

import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.java.extensions.interfaces.functional.QuadFunction;
import com.neathorium.thorium.java.extensions.interfaces.functional.QuadPredicate;
import com.neathorium.thorium.java.extensions.interfaces.functional.TriPredicate;
import com.neathorium.thorium.java.extensions.interfaces.functional.boilers.IGetMessage;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public record ExecutorFunctionData (
    IGetMessage MESSAGE_DATA,
    TriPredicate<Data<?>, Integer, Integer> BREAK_CONDITION,
    QuadPredicate<ExecutionStateData, Integer, Integer, Integer> END_CONDITION,
    QuadFunction<ExecutionStateData, String, Integer, Integer, String> END_MESSAGE_HANDLER,
    BiFunction<String, Integer, String> KEY_FORMATTER,
    Predicate<Data<?>> FILTER_CONDITION
) {}
