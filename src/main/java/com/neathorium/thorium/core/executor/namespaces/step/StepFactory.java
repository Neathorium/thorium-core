package com.neathorium.thorium.core.executor.namespaces.step;

import com.neathorium.thorium.core.data.namespaces.DataFunctions;
import com.neathorium.thorium.core.data.namespaces.predicates.DataPredicates;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.data.interfaces.DataSupplier;
import com.neathorium.thorium.core.records.executor.ExecutionKeyStepData;
import com.neathorium.thorium.core.records.executor.ExecutionStepData;
import com.neathorium.thorium.exceptions.constants.ExceptionConstants;
import com.neathorium.thorium.exceptions.namespaces.ExceptionFunctions;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StatusDetails;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.util.ResultsUtils;

import java.util.UUID;
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

    static <T> Data<T> allureStep(String stepName, DataSupplier<T> step) {
        final var lifecycle = Allure.getLifecycle();
        final var uuid = UUID.randomUUID().toString();
        final var stepResult = new StepResult().setName(stepName);
        lifecycle.startStep(uuid, stepResult);

        Data<T> data = null;
        var exception = ExceptionConstants.EXCEPTION;
        try {
            data = step.apply();
        } catch (Exception ex) {
            exception = ex;
            lifecycle.updateStep(
                uuid,
                s -> s.setStatus(Status.FAILED).setStatusDetails(ResultsUtils.getStatusDetails(ex).orElse(null)).setDescription(ex.getLocalizedMessage())
            );
            throw ex;
        } finally {
            final var message = DataFunctions.getFormattedMessage(data);
            final var stepStatus = DataPredicates.isValidNonFalse(data) ? Status.PASSED : Status.FAILED;
            lifecycle.updateStep(uuid, s -> s.setStatus(stepStatus).setStatusDetails(new StatusDetails().setMessage(message)));
            if (ExceptionFunctions.isException(exception)) {
                Allure.attachment("Exception message", exception.getLocalizedMessage());
            } else {
                Allure.attachment("Step Message", message);
            }
            lifecycle.stopStep(uuid);
        }

        return data;
    }

    static <T> DataSupplier<T> allureStepF(String name, DataSupplier<T> step) {
        return StepFactory.voidStep(() -> StepFactory.allureStep(name, step));
    }
}
