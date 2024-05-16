package com.neathorium.thorium.core.records.executor;

public record ExecutionResultData<T> (
    ExecutionStateData STATE_DATA,
    T RESULT
) {}