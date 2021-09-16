package com.neathorium.thorium.core.extensions.records;

import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class ExtensionListData<T> {
    public final Function<List<T>, T> GETTER;
    public final Function<List<T>, Integer> END_INDEX_FUNCTION;
    public final int START_INDEX;

    public ExtensionListData(Function<List<T>, T> getter, Function<List<T>, Integer> endIndexFunction, int startIndex) {
        this.GETTER = getter;
        this.END_INDEX_FUNCTION = endIndexFunction;
        this.START_INDEX = startIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (NullableFunctions.isNull(o) || CoreUtilities.isNotEqual(getClass(), o.getClass())) {
            return false;
        }

        final var that = (ExtensionListData<?>) o;
        return (
            CoreUtilities.isEqual(START_INDEX, that.START_INDEX) &&
            Objects.equals(GETTER, that.GETTER) &&
            Objects.equals(END_INDEX_FUNCTION, that.END_INDEX_FUNCTION)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(GETTER, END_INDEX_FUNCTION, START_INDEX);
    }
}
