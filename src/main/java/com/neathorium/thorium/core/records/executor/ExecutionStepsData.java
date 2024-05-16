package com.neathorium.thorium.core.records.executor;

import com.neathorium.thorium.core.data.records.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public record ExecutionStepsData<DependencyType> (
    List<Function<DependencyType, Data<?>>> STEPS,
    DependencyType DEPENDENCY,
    int LENGTH
) {}
