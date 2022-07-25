package com.neathorium.thorium.core.wait.exceptions;

import com.neathorium.thorium.core.wait.namespaces.WaitExceptionFunctions;

public class WrappedThreadInterruptedException extends RuntimeException {
    public WrappedThreadInterruptedException(String message) {
        super(message + WaitExceptionFunctions.getSystemInformationMessage());
    }

    public WrappedThreadInterruptedException(String message, Throwable cause) {
        super(message + WaitExceptionFunctions.getSystemInformationMessage(), cause);
    }
}
