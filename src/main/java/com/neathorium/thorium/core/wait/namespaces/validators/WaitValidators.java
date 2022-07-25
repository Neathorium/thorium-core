package com.neathorium.thorium.core.wait.namespaces.validators;

import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;
import com.neathorium.thorium.core.wait.records.WaitData;
import com.neathorium.thorium.core.wait.records.VoidWaitData;

public interface WaitValidators {
    static <DependencyType, U, ReturnType> String isValidWaitParameters(DependencyType dependency, WaitData<DependencyType, U, ReturnType> data) {
        return CoreFormatter.getNamedErrorMessageOrEmpty(
            "isValidWaitParameters",
            CoreFormatter.isNullMessageWithName(dependency, "Dependency") + WaitDataValidators.isValidWaitData(data)
        );
    }

    static <T, U, ReturnType> String isValidWaitParameters(VoidWaitData<U, ReturnType> data) {
        return CoreFormatter.getNamedErrorMessageOrEmpty(
            "isValidVoidWaitParameters",
            WaitDataValidators.isValidWaitData(data)
        );
    }
}
