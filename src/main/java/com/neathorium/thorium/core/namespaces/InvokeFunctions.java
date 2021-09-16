package com.neathorium.thorium.core.namespaces;

import com.neathorium.thorium.core.exceptions.MethodInvokeException;
import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.constants.CoreConstants;
import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface InvokeFunctions {
    static <ParameterType> Object invoke(Method method, ParameterType base) {
        if (CoreUtilities.areAnyNull(method, base)) {
            throw new MethodInvokeException("Parameters were wrong" + CoreFormatterConstants.END_LINE);
        }

        try {
            return method.invoke(base);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new MethodInvokeException(ex);
        }
    }

    static <ParameterType> Object invokeWithParameters(Method method, ParameterType base, Object[] parameters) {
        if (CoreUtilities.areAnyNull(method, base)) {
            throw new MethodInvokeException("Parameters were wrong" + CoreFormatterConstants.END_LINE);
        }

        try {
            return method.invoke(base, parameters);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new MethodInvokeException(ex);
        }
    }

    static <ParameterType> Function<ParameterType, Object> regularApply(Method method, BiFunction<Method, ParameterType, Object> handler) {
        return CoreUtilities.areNotNull(method, handler) ? base -> handler.apply(method, base) : regularDefault();
    }

    static <ParameterType> Function<ParameterType, Object> defaultApplyGetter(Object object) {
        return parameter -> object;
    }

    static <ParameterType> Function<ParameterType, Object> regularDefault() {
        return defaultApplyGetter(CoreConstants.STOCK_OBJECT);
    }
}
