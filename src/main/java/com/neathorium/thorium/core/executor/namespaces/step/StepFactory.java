package com.neathorium.thorium.core.executor.namespaces.step;

import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.data.interfaces.DataSupplier;
import com.neathorium.thorium.core.records.executor.ExecutionKeyStepData;
import com.neathorium.thorium.core.records.executor.ExecutionStepData;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public interface StepFactory {
    static <DependencyType, ReturnType> DataSupplier<ReturnType> step(Function<DependencyType, Data<ReturnType>> function, DependencyType dependency) {
        return new ExecutionStepData<>(function, () -> dependency);
    }

    static <DependencyType, KeyType, ReturnType> DataSupplier<ReturnType> step(Function<DependencyType, Data<ReturnType>> function, Function<KeyType, DependencyType> dependencyGetter, KeyType key) {
        return new ExecutionKeyStepData<>(function, dependencyGetter, key);
    }

    static <DependencyType, ReturnType> DataSupplier<ReturnType> voidStep(Function<DependencyType, Data<ReturnType>> function) {
        return StepFactory.step(function, null);
    }

    static <ReturnType> DataSupplier<ReturnType> voidStep(Supplier<Data<ReturnType>> supplier) {
        return StepFactory.step((v) -> supplier.get(), null);
    }

    static <DependencyType, KeyType, ReturnType> BiFunction<Function<DependencyType, Data<ReturnType>>, KeyType, DataSupplier<ReturnType>> step(Function<KeyType, DependencyType> dependencyGetter) {
        return (function, dependencyKey) -> StepFactory.step(function, dependencyGetter, dependencyKey);
    }
}
