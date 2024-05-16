package com.neathorium.thorium.core.wait.records.tasks.common;

import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.java.extensions.namespaces.predicates.EqualsPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public record WaitTaskStateData<T, V>(
    Data<V> DATA,
    T DEPENDENCY,
    AtomicInteger COUNTER,
    int LIMIT
) {}
