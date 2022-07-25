package com.neathorium.thorium.core.wait.namespaces.predicates;

import com.neathorium.thorium.core.data.namespaces.predicates.DataPredicates;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.records.executor.ExecutionResultData;
import com.neathorium.thorium.java.extensions.namespaces.utilities.BooleanUtilities;

public interface WaitPredicates {
    static boolean isExecutionValidNonFalse(Data<ExecutionResultData<Object>> data) {
        return DataPredicates.isValid(data) && BooleanUtilities.isTrue(data.STATUS());
    }
}
