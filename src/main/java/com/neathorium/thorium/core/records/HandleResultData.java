package com.neathorium.thorium.core.records;

import java.util.function.Function;

public record HandleResultData<ParameterType, ReturnType>(Function<ParameterType, ReturnType> CASTER, ParameterType PARAMETER, ReturnType DEFAULT_VALUE) {}
