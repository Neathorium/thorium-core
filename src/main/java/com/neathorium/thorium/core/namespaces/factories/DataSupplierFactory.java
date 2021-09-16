package com.neathorium.thorium.core.namespaces.factories;

import com.neathorium.thorium.core.extensions.interfaces.functional.boilers.DataSupplier;
import com.neathorium.thorium.core.records.Data;

import java.util.function.Function;

public interface DataSupplierFactory {
    static <T> DataSupplier<T> get(Function<Void, Data<T>> function) {
        return function::apply;
    }
}
