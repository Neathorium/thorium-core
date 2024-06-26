package com.neathorium.thorium.core.namespaces;

import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.data.interfaces.DataSupplier;
import com.neathorium.thorium.core.executor.namespaces.step.StepFactory;

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
