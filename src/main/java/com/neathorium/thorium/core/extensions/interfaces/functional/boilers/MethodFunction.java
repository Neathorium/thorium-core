package com.neathorium.thorium.core.extensions.interfaces.functional.boilers;

import java.lang.reflect.Method;
import java.util.function.Function;

@FunctionalInterface
public interface MethodFunction<ReturnType> extends Function<Method, ReturnType> {}
