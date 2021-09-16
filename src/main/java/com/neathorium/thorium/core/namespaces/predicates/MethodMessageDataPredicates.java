package com.neathorium.thorium.core.namespaces.predicates;

import com.neathorium.thorium.core.records.MethodMessageData;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface MethodMessageDataPredicates {
    static boolean isValid(MethodMessageData data) {
        return NullableFunctions.isNotNull(data) && isNotBlank(data.message) && NullableFunctions.isNotNull(data.nameof);
    }
}
