package com.neathorium.thorium.core.extensions.namespaces.predicates;

import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.namespaces.predicates.DataPredicates;
import com.neathorium.thorium.core.records.Data;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;

public interface ExecutorPredicates {
    static boolean isFalseStatus(Data<?> data, int index, int length) {
        return NullableFunctions.isNotNull(data) && CoreUtilities.isFalse(data.status) && BasicPredicates.isSmallerThan(index, length);
    }

    static boolean isExecuting(Data<?> data, int index, int length) {
        return DataPredicates.isValidNonFalse(data) && BasicPredicates.isSmallerThan(index, length);
    }
}
