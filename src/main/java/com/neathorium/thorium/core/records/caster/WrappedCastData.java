package com.neathorium.thorium.core.records.caster;

import com.neathorium.thorium.core.abstracts.casting.AbstractCastData;
import com.neathorium.thorium.core.data.records.Data;

import java.util.function.Function;

public class WrappedCastData<T> extends AbstractCastData<Data<T>, T> {
    public WrappedCastData(Data<T> object, Function<Object, T> caster) {
        super(object, caster);
    }
}
