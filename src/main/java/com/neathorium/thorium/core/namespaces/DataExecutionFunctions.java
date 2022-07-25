package com.neathorium.thorium.core.namespaces;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.data.namespaces.factories.DataFactoryFunctions;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface DataExecutionFunctions {
    static <DependencyType, ParameterType, ReturnType> Function<DependencyType, Data<ReturnType>> conditionalChain(
        Predicate<Data<ParameterType>> guard,
        Function<DependencyType, Data<ParameterType>> dependency,
        Function<Data<ParameterType>, Data<ReturnType>> positive,
        Data<ReturnType> negative
    ) {
        return BaseExecutionFunctions.conditionalChain(guard, dependency, positive, DataFactoryFunctions.prependMessage(negative, "conditionalChain", "Dependency parameter failed the guard" + CoreFormatterConstants.COLON_SPACE + "guard message was false" + CoreFormatterConstants.END_LINE));
    }

    static <DependencyType, ParameterType, ReturnType> Function<DependencyType, Data<ReturnType>> conditionalChain(
        Function<Data<ParameterType>, String> guard,
        Function<DependencyType, Data<ParameterType>> dependency,
        Function<Data<ParameterType>, Data<ReturnType>> positive,
        Data<ReturnType> negative
    ) {
        return object -> {
            final var positiveDependency = dependency.apply(object);
            final var guardMessage = guard.apply(positiveDependency);
            return isBlank(guardMessage) ? positive.apply(positiveDependency) : DataFactoryFunctions.prependMessage(negative, "conditionalChain", "Dependency parameter failed the guard" + CoreFormatterConstants.COLON_SPACE + guardMessage);
        };
    }

    static <DependencyType, ParameterType, ReturnType> Function<DependencyType, Data<ReturnType>> conditionalUnwrapChain(
        Predicate<Data<ParameterType>> guard,
        Function<DependencyType, Data<ParameterType>> dependency,
        Function<ParameterType, Data<ReturnType>> positive,
        Data<ReturnType> negative
    ) {
        return object -> {
            final var positiveDependency = dependency.apply(object);
            return guard.test(positiveDependency) ? positive.apply(positiveDependency.OBJECT()) : negative;
        };
    }

    static <DependencyType, ParameterType, ReturnType> Function<DependencyType, Data<ReturnType>> conditionalUnwrapChain(
        Function<Data<ParameterType>, String> guard,
        Function<DependencyType, Data<ParameterType>> dependency,
        Function<ParameterType, Data<ReturnType>> positive,
        Data<ReturnType> negative
    ) {
        return object -> {
            final var positiveDependency = dependency.apply(object);
            final var guardMessage = guard.apply(positiveDependency);
            return isBlank(guardMessage) ? positive.apply(positiveDependency.OBJECT()) : DataFactoryFunctions.prependMessage(negative, "conditionalChain", "Dependency parameter failed the guard" + CoreFormatterConstants.COLON_SPACE + guardMessage);
        };
    }

    static <DependencyType, ParameterType, ReturnType> Function<DependencyType, Data<ReturnType>> validChain(Function<DependencyType, Data<ParameterType>> dependency, Function<Data<ParameterType>, Data<ReturnType>> positive, Data<ReturnType> negative) {
        return conditionalChain(CoreFormatter::isInvalidOrFalseMessage, dependency, positive, negative);
    }

    static <DependencyType, ParameterType, ReturnType> Function<Data<ReturnType>, Function<DependencyType, Data<ReturnType>>> validChain(Function<DependencyType, Data<ParameterType>> dependency, Function<Data<ParameterType>, Data<ReturnType>> positive) {
        return negative -> validChain(dependency, positive, negative);
    }

    static <DependencyType, ParameterType, ReturnType> Function<DependencyType, Data<ReturnType>> validUnwrapChain(Function<DependencyType, Data<ParameterType>> dependency, Function<ParameterType, Data<ReturnType>> positive, Data<ReturnType> negative) {
        return conditionalUnwrapChain(CoreFormatter::isInvalidOrFalseMessage, dependency, positive, negative);
    }

    static <ReturnType> Data<ReturnType> ifDependencyAnyCore(String nameof, Data<ReturnType> data) {
        final var name = isNotBlank(nameof) ? nameof : "ifDependencyAnyCore";
        return NullablePredicates.isNotNull(data) ? (
            data
        ) : DataFactoryFunctions.getInvalidWith(null, name, "Data " + CoreFormatterConstants.WAS_NULL);
    }

    private static <DependencyType, ReturnType> Data<ReturnType> ifDependencyAnyWrappedCore(DependencyType dependency, String nameof, Function<DependencyType, Data<ReturnType>> function) {
        return ifDependencyAnyCore(nameof, function.apply(dependency));
    }

    private static <DependencyType, ReturnType> Function<DependencyType, Data<ReturnType>> ifDependencyAnyWrappedCore(String nameof, Function<DependencyType, Data<ReturnType>> function) {
        return dependency -> ifDependencyAnyWrappedCore(dependency, nameof, function);
    }

    static <DependencyType, ReturnType> Function<DependencyType, Data<ReturnType>> ifDependency(String nameof, boolean condition, Function<DependencyType, Data<ReturnType>> positive, Data<ReturnType> negative) {
        final var lNameof = isBlank(nameof) ? "ifDependency" : nameof;
        return condition && NullablePredicates.isNotNull(positive) ? ifDependencyAnyWrappedCore(lNameof, positive) : dependency -> ifDependencyAnyCore(lNameof, negative);
    }

    static <DependencyType, ReturnType> Function<DependencyType, Data<ReturnType>> ifDependency(String nameof, String errorMessage, Function<DependencyType, Data<ReturnType>> positive, Data<ReturnType> negative) {
        return ifDependency(nameof, isBlank(errorMessage), positive, DataFactoryFunctions.replaceMessage(negative, nameof, errorMessage));
    }

    static <DependencyType, ParameterType, ReturnType> Function<DependencyType, Data<ReturnType>> ifDependency(
        String nameof,
        boolean status,
        Predicate<Data<ParameterType>> guard,
        Function<DependencyType, Data<ParameterType>> function,
        Function<Data<ParameterType>, Data<ReturnType>> positive,
        Data<ReturnType> negative
    ) {
        return ifDependency(nameof, status && NullablePredicates.areNotNull(guard, function, positive), conditionalChain(guard, function, positive, negative), negative);
    }

    static <DependencyType, ParameterType, ReturnType> Function<DependencyType, Data<ReturnType>> ifDependency(
        String nameof,
        boolean status,
        Function<Data<ParameterType>, String> guard,
        Function<DependencyType, Data<ParameterType>> function,
        Function<Data<ParameterType>, Data<ReturnType>> positive,
        Data<ReturnType> negative
    ) {
        return ifDependency(nameof, status && NullablePredicates.areNotNull(guard, function, positive), conditionalChain(guard, function, positive, negative), negative);
    }
}
