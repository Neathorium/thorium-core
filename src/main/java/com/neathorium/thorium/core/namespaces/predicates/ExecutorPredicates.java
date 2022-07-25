package com.neathorium.thorium.core.namespaces.predicates;

import com.neathorium.thorium.core.data.namespaces.predicates.DataPredicates;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.java.extensions.namespaces.predicates.BasicPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;
import com.neathorium.thorium.java.extensions.namespaces.utilities.BooleanUtilities;

public interface ExecutorPredicates {
    static boolean isFalseStatus(Data<?> data, int index, int length) {
        return (
            NullablePredicates.isNotNull(data) &&
            BooleanUtilities.isFalse(data.STATUS()) &&
            BasicPredicates.isSmallerThan(index, length)
        );
    }

    static boolean isExecuting(Data<?> data, int index, int length) {
        return DataPredicates.isValidNonFalse(data) && BasicPredicates.isSmallerThan(index, length);
    }
}
