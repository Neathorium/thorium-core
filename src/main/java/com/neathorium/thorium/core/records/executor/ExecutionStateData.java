package com.neathorium.thorium.core.records.executor;

import com.neathorium.thorium.core.data.records.Data;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public record ExecutionStateData(
    Map<String, Data<?>> EXECUTION_MAP,
    List<Integer> INDICES
) {}
