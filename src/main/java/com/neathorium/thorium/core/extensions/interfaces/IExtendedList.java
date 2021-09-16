package com.neathorium.thorium.core.extensions.interfaces;

import java.util.List;

public interface IExtendedList<T> extends List<T>, IEmptiableCollection, IAmountPredicates {
    T first();
    int lastIndex();
    T last();
    List<T> tail();
    List<T> initials();
}
