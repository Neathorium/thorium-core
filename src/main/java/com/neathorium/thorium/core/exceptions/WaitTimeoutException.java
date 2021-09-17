package com.neathorium.thorium.core.exceptions;

import com.neathorium.thorium.core.namespaces.wait.WaitExceptionFunctions;

public class WaitTimeoutException extends RuntimeException {
    public WaitTimeoutException(String message) {
        super(message + WaitExceptionFunctions.getSystemInformationMessage());
    }

    public WaitTimeoutException(String message, Throwable cause) {
        super(message + WaitExceptionFunctions.getSystemInformationMessage(), cause);
    }
}
