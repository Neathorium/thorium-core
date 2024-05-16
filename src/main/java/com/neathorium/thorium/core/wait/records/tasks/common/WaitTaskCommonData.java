package com.neathorium.thorium.core.wait.records.tasks.common;

import java.util.function.Function;
import java.util.function.Predicate;

public record WaitTaskCommonData<DependencyType, ReturnType, ConditionType>(
    Function<DependencyType, ReturnType> FUNCTION,
    Predicate<ConditionType> EXIT_CONDITION
) {}
