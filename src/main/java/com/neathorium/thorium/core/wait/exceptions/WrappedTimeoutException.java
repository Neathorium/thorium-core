package com.neathorium.thorium.core.wait.exceptions;

import com.neathorium.thorium.core.wait.namespaces.WaitExceptionFunctions;

public class WrappedTimeoutException extends RuntimeException {
    public WrappedTimeoutException(String message) {
        super(message + WaitExceptionFunctions.getSystemInformationMessage());
    }

    public WrappedTimeoutException(String message, Throwable cause) {
        super(message + WaitExceptionFunctions.getSystemInformationMessage(), cause);
    }
}
