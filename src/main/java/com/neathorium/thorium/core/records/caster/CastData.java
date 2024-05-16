package com.neathorium.thorium.core.records.caster;

import java.util.function.Function;

public record CastData<T, U> (
    T DEFAULT_VALUE,
    Function<Object, U> CASTER
) {}
