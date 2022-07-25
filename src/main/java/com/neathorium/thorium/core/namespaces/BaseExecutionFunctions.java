package com.neathorium.thorium.core.namespaces;

import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;
import java.util.function.Predicate;

public interface BaseExecutionFunctions {
    private static <DependencyType, ParameterType, ReturnType> ReturnType conditionalChainCore(DependencyType dependency, Function<ParameterType, Boolean> guard, Function<DependencyType, ParameterType> dependencyHandler, Function<ParameterType, ReturnType> positive, ReturnType negative) {
        final var positiveDependency = dependencyHandler.apply(dependency);
        return guard.apply(positiveDependency) ? positive.apply(positiveDependency) : negative;
    }

    static <DependencyType, ParameterType, ReturnType> Function<DependencyType, ReturnType> conditionalChain(Predicate<ParameterType> guard, Function<DependencyType, ParameterType> dependency, Function<ParameterType, ReturnType> positive, ReturnType negative) {
        return d -> conditionalChainCore(d, guard::test, dependency, positive, negative);
    }

    static <DependencyType, ParameterType, ReturnType> Function<DependencyType, ReturnType> conditionalChain(Function<ParameterType, String> guard, Function<DependencyType, ParameterType> dependency, Function<ParameterType, ReturnType> positive, ReturnType negative) {
        return d -> conditionalChainCore(d, guard.andThen(StringUtils::isBlank), dependency, positive, negative);
    }

    static <ReturnType> ReturnType ifDependencyAnyCore(ReturnType data) {
        return NullablePredicates.isNotNull(data) ? data : null;
    }

    private static <DependencyType, ReturnType> ReturnType ifDependencyAnyWrappedCore(DependencyType dependency, Function<DependencyType, ReturnType> function) {
        return ifDependencyAnyCore(function.apply(dependency));
    }

    private static <DependencyType, ReturnType> Function<DependencyType, ReturnType> ifDependencyAnyWrappedCore(Function<DependencyType, ReturnType> function) {
        return dependency -> ifDependencyAnyWrappedCore(dependency, function);
    }

    static <DependencyType, ReturnType> Function<DependencyType, ReturnType> ifDependency(boolean condition, Function<DependencyType, ReturnType> positive, ReturnType negative) {
        return condition && NullablePredicates.isNotNull(positive) ? ifDependencyAnyWrappedCore(positive) : dependency -> ifDependencyAnyCore(negative);
    }

    static <DependencyType, ParameterType, ReturnType> Function<DependencyType, ReturnType> ifDependency(
        boolean status,
        Predicate<ParameterType> guard,
        Function<DependencyType, ParameterType> function,
        Function<ParameterType, ReturnType> positive,
        ReturnType negative
    ) {
        return ifDependency(status && NullablePredicates.areNotNull(guard, function, positive), BaseExecutionFunctions.conditionalChain(guard, function, positive, negative), negative);
    }

    static <DependencyType, ParameterType, ReturnType> Function<DependencyType, ReturnType> ifDependency(
        boolean status,
        Function<ParameterType, String> guard,
        Function<DependencyType, ParameterType> function,
        Function<ParameterType, ReturnType> positive,
        ReturnType negative
    ) {
        return ifDependency(status && NullablePredicates.areNotNull(guard, function, positive), BaseExecutionFunctions.conditionalChain(guard, function, positive, negative), negative);
    }
}
