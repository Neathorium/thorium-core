package com.neathorium.thorium.core.constants;

import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;
import com.neathorium.thorium.core.records.Data;

import java.util.function.Predicate;

public abstract class ExecutionStateDataConstants {
    public static final Predicate<Data<?>> DEFAULT_FILTER = NullableFunctions::isNotNull;
}
