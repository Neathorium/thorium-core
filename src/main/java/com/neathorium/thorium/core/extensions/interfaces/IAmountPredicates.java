package com.neathorium.thorium.core.extensions.interfaces;

public interface IAmountPredicates {
    boolean isSingle();
    boolean isMany();
    boolean hasMoreThan(int amount);
    boolean hasAtleast(int amount);
    boolean hasIndex(int index);
}
