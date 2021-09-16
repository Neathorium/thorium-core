package com.neathorium.thorium.core.namespaces;

import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Predicate;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public interface StringUtilities {
    private static boolean parameterGuard(String object, String expected) {
        return NullableFunctions.isNotNull(object) && isNotEmpty(expected);
    }

    static boolean contains(String object, String expected) {
        return parameterGuard(object, expected) && StringUtils.contains(object, expected);
    }

    static Predicate<String> contains(String value) {
        return (expected) -> contains(value, expected);
    }

    static boolean startsWithCaseSensitive(String object, String expected) {
        return parameterGuard(object, expected) && StringUtils.startsWith(object, expected);
    }

    static boolean startsWithCaseInsensitive(String object, String expected) {
        return parameterGuard(object, expected) && StringUtils.startsWithIgnoreCase(object, expected);
    }

    static boolean endsWithCaseSensitive(String object, String expected) {
        return parameterGuard(object, expected) && StringUtils.endsWith(object, expected);
    }

    static boolean endsWithCaseInsensitive(String object, String expected) {
        return parameterGuard(object, expected) && StringUtils.endsWithIgnoreCase(object, expected);
    }

    static boolean uncontains(String object, String expected) {
        return parameterGuard(object, expected) && !contains(object, expected);
    }

    static Predicate<String> uncontains(String value) {
        return (expected) -> uncontains(value, expected);
    }

    static boolean startsNotWithCaseSensitive(String object, String expected) {
        return parameterGuard(object, expected) && !startsWithCaseSensitive(object, expected);
    }

    static boolean startsNotWithCaseInsensitive(String object, String expected) {
        return parameterGuard(object, expected) && !startsWithCaseInsensitive(object, expected);
    }

    static boolean endsNotWithCaseSensitive(String object, String expected) {
        return parameterGuard(object, expected) && !endsWithCaseSensitive(object, expected);
    }

    static boolean endsNotWithCaseInsensitive(String object, String expected) {
        return parameterGuard(object, expected) &&  !endsWithCaseInsensitive(object, expected);
    }
}
