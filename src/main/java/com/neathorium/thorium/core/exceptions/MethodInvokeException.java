package com.neathorium.thorium.core.exceptions;

public class MethodInvokeException extends RuntimeException {
    public MethodInvokeException() {
        super();
    }

    public MethodInvokeException(String message) {
        super(message);
    }

    public MethodInvokeException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public MethodInvokeException(Throwable throwable) {
        super(throwable);
    }
}
