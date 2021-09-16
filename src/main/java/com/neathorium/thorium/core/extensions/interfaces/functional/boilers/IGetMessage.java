package com.neathorium.thorium.core.extensions.interfaces.functional.boilers;

import java.util.function.Function;

@FunctionalInterface
public interface IGetMessage extends IContained<Boolean, String> {
    Function<Boolean, String> get();
}
