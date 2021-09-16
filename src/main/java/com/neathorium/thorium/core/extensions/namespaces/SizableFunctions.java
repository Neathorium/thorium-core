package com.neathorium.thorium.core.extensions.namespaces;

import java.util.function.Supplier;

public interface SizableFunctions {
    static int size(Supplier<Integer> sizeFunction) {
        return NullableFunctions.isNotNull(sizeFunction) ? sizeFunction.get() : 0;
    }

    static int size(Object[] object) {
        return NullableFunctions.isNotNull(object) ? object.length : 0;
    }
}
