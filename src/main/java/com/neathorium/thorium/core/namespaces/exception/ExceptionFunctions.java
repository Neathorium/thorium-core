package com.neathorium.thorium.core.namespaces.exception;

import com.neathorium.thorium.core.constants.exception.ExceptionConstants;
import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;
import com.neathorium.thorium.core.records.Data;

public interface ExceptionFunctions {
    private static <T extends Exception> boolean isReferenceOrMessageEquals(T exception, T expected) {
        return (
            CoreUtilities.isEqual(expected, exception) ||
            CoreUtilities.isEqual(expected.getMessage(), exception.getMessage())
        );
    }

    static <T extends Exception> boolean isException(Exception ex) {
        final var exception = ExceptionConstants.EXCEPTION;
        return (
            NullableFunctions.isNotNull(ex) &&
            CoreUtilities.isFalse(isReferenceOrMessageEquals(ex, exception))
        );
    }

    static boolean isException(Data<?> data) {
        return NullableFunctions.isNotNull(data) && isException(data.exception);
    }

    static boolean isNonException(Exception ex) {
        final var exception = ExceptionConstants.EXCEPTION;
        return (
            NullableFunctions.isNotNull(ex) && isReferenceOrMessageEquals(ex, exception)
        );
    }
}
