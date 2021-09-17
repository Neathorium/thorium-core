package com.neathorium.thorium.core.extensions.interfaces;

public interface IEmptiable extends ISizable {
    boolean isEmpty();
    boolean isNullOrEmpty();
    boolean isNotNullAndNonEmpty();
}
