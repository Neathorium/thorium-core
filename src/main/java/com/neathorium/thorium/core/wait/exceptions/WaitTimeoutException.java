package com.neathorium.thorium.core.wait.exceptions;

import com.neathorium.thorium.core.wait.namespaces.WaitExceptionFunctions;

public class WaitTimeoutException extends RuntimeException {
    public WaitTimeoutException(String message) {
        super(message + WaitExceptionFunctions.getSystemInformationMessage());
    }

    public WaitTimeoutException(String message, Throwable cause) {
        super(message + WaitExceptionFunctions.getSystemInformationMessage(), cause);
    }
}
