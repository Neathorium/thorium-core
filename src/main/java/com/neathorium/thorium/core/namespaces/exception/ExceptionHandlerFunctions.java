package com.neathorium.thorium.core.namespaces.exception;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.data.namespaces.factories.DataFactoryFunctions;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.exceptions.constants.ExceptionConstants;
import com.neathorium.thorium.exceptions.namespaces.ExceptionFunctions;
import com.neathorium.thorium.java.extensions.namespaces.predicates.GuardPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;
import com.neathorium.thorium.java.extensions.namespaces.utilities.BooleanUtilities;
import org.apache.commons.lang3.StringUtils;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public interface ExceptionHandlerFunctions {
    static IllegalStateException getExceptionWith(String message, Throwable cause) {
        if (NullablePredicates.isNull(cause)) {
            return new IllegalStateException(message);
        }

        return new IllegalStateException(message, cause);
    }


    static <T extends RuntimeException> Data<Boolean> getExceptionData(
        String name,
        BiFunction<String, Throwable, T> exceptionGetter,
        Exception first,
        Exception second
    ) {
        final var nameof = "ExceptionHandlerFunctions.getExceptionData";
        final var isFirstNonException = ExceptionFunctions.isNonException(first);
        final var isSecondNonException = ExceptionFunctions.isNonException(second);
        var exception = ExceptionConstants.EXCEPTION;
        var message = "";
        var status = false;
        final var nameFragment = StringUtils.isNotBlank(name) ? "During execution of process(\"" + name + "\") " : "";
        if (GuardPredicates.areAll((Predicate<Boolean>) BooleanUtilities::isFalse, isFirstNonException, isSecondNonException)) {
            message = (
                nameFragment +
                "Both encountered issues" + CoreFormatterConstants.COLON_NEWLINE +
                "First exception: " + first.getLocalizedMessage() + CoreFormatterConstants.NEW_LINE +
                "Second exception: " + second.getLocalizedMessage() + CoreFormatterConstants.NEW_LINE
            );
            exception = exceptionGetter.apply(message, null);
        }

        if (isFirstNonException && BooleanUtilities.isFalse(isSecondNonException)) {
            message = "Second encountered issues: " + second.getLocalizedMessage();
            exception = exceptionGetter.apply(message, second);
        }

        if (BooleanUtilities.isFalse(isFirstNonException) && isSecondNonException) {
            message = "First encountered issues: " + first.getLocalizedMessage();
            exception = exceptionGetter.apply(message, first);
        }

        if (isFirstNonException && isSecondNonException) {
            exception = ExceptionConstants.EXCEPTION;
            message = exception.getLocalizedMessage();
            status = true;
        }

        return DataFactoryFunctions.getBoolean(status, nameof, message, exception);
    }

    static <T extends RuntimeException> Data<Boolean> getExceptionData(
        String name,
        BiFunction<String, Throwable, T> exceptionGetter,
        Data<?> firstData,
        Data<?> secondData
    ) {
        return ExceptionHandlerFunctions.getExceptionData(name, exceptionGetter, firstData.EXCEPTION(), secondData.EXCEPTION());
    }

    static <T extends RuntimeException> Data<Boolean> getExceptionData(
        String name,
        BiFunction<String, Throwable, T> exceptionGetter,
        Data<?> firstData,
        Exception second
    ) {
        return ExceptionHandlerFunctions.getExceptionData(name, exceptionGetter, firstData.EXCEPTION(), second);
    }


    static <T extends RuntimeException> Data<Boolean> getExceptionData(
        BiFunction<String, Throwable, T> exceptionGetter,
        Exception first,
        Exception second
    ) {
        return ExceptionHandlerFunctions.getExceptionData("", exceptionGetter, first, second);
    }

    static <T extends RuntimeException> Data<Boolean> getExceptionData(
        BiFunction<String, Throwable, T> exceptionGetter,
        Data<?> firstData,
        Data<?> secondData
    ) {
        return ExceptionHandlerFunctions.getExceptionData("", exceptionGetter, firstData.EXCEPTION(), secondData.EXCEPTION());
    }
}
