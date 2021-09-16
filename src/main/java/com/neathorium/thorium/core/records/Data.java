package com.neathorium.thorium.core.records;

import java.util.Objects;

/**
 * The {@code Data} class is a classic data class; the heart of the command/pipe pattern associated.
 * Used for holding multiple return values from a function, as well, meaning essentially a named tuple.
 *
 * The class can hold an exception, and its message as optional parameters.
 *
 * @param <T>
 */
public class Data<T> {
    public final T object;
    public final boolean status;
    public final MethodMessageData message;
    public final Exception exception;
    public final String exceptionMessage;

    /**
     * Allocates a new {@code Data} instance, so that it represents a:
     * - T type Object,
     * - its status during any kind of execution,
     * - a {@code MethodMessageData} instance describing status,
     * - an exception that might've occurred, or needs to be passed,
     * - an exception message, that is either of the passed, or an overridden.
     *
     * @param object
     *        A {@code T} type.
     * @param status
     *        A {@code boolean}.
     * @param message
     *        A {@code MethodMessageData}.
     * @param exception
     *        A {@code Exception} instance, if any exception occurred, or needs to be passed along the object.
     * @param exceptionMessage
     *        A {@code String} of the exception, or an overriding message for the exception.

     */
    public Data(T object, boolean status, MethodMessageData message, Exception exception, String exceptionMessage) {
        this.object = object;
        this.status = status;
        this.message = message;
        this.exception = exception;
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        if (this == o) {
            return true;
        }

        final var data = (Data<?>) o;
        return (
            Objects.equals(status, data.status) &&
            Objects.equals(object, data.object) &&
            Objects.equals(message, data.message) &&
            Objects.equals(exception, data.exception) &&
            Objects.equals(exceptionMessage, data.exceptionMessage)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(object, status, message, exception, exceptionMessage);
    }
}
