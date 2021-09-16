package com.neathorium.thorium.core.namespaces;

import com.neathorium.thorium.core.extensions.interfaces.functional.boilers.DataSupplier;
import com.neathorium.thorium.core.namespaces.executor.step.StepFactory;
import com.neathorium.thorium.core.records.Data;

public interface DataSupplierExecutionFunctions {
    static <ReturnType> DataSupplier<ReturnType> ifDependency(String nameof, boolean condition, DataSupplier<ReturnType> positive, Data<ReturnType> negative) {
        return StepFactory.voidStep(
            DataExecutionFunctions.ifDependency(nameof, condition, positive, negative)
        );
    }

    static <ReturnType> DataSupplier<ReturnType> ifDependency(String nameof, String errorMessage, DataSupplier<ReturnType> positive, Data<ReturnType> negative) {
        return StepFactory.voidStep(
            DataExecutionFunctions.ifDependency(nameof, errorMessage, positive, negative)
        );
    }
}
