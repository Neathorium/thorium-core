package com.neathorium.thorium.core.wait.exceptions;

import com.neathorium.thorium.core.wait.namespaces.WaitExceptionFunctions;

public class WrappedExecutionException extends RuntimeException {
    public WrappedExecutionException(String message) {
        super(message + WaitExceptionFunctions.getSystemInformationMessage());
    }

    public WrappedExecutionException(String message, Throwable cause) {
        super(message + WaitExceptionFunctions.getSystemInformationMessage(), cause);
    }
}
