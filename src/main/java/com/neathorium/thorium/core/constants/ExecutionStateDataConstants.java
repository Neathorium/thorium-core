package com.neathorium.thorium.core.constants;

import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;

import java.util.function.Predicate;

public abstract class ExecutionStateDataConstants {
    public static final Predicate<Data<?>> DEFAULT_FILTER = NullablePredicates::isNotNull;
}
