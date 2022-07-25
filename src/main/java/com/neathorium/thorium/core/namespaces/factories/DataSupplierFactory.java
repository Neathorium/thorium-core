package com.neathorium.thorium.core.namespaces.factories;

import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.data.interfaces.DataSupplier;

import java.util.function.Function;

public interface DataSupplierFactory {
    static <T> DataSupplier<T> get(Function<Void, Data<T>> function) {
        return function::apply;
    }
}
