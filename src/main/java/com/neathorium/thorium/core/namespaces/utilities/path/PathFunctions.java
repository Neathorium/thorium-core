package com.neathorium.thorium.core.namespaces.utilities.path;

import com.neathorium.thorium.core.platform.namespaces.systemidentity.BasicSystemIdentityFunctions;
import com.neathorium.thorium.java.extensions.namespaces.utilities.BooleanUtilities;
import org.apache.commons.lang.StringUtils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public interface PathFunctions {
    static String remove(String value, String remove) {
        return StringUtils.removeStartIgnoreCase(value, remove);
    }

    static String removeQuotes(String value) {
        return PathFunctions.remove(value, "\"");
    }

    static String handleSpaces(String value) {
        var result = URLDecoder.decode(value, StandardCharsets.UTF_8)
                .replaceAll("file:///", "")
                .replaceAll("file:/", "")
                .replaceAll("\\u0200", "\\ ");

        if (
                BooleanUtilities.isFalse(BasicSystemIdentityFunctions.isWindows()) &&
                BooleanUtilities.isFalse(StringUtils.startsWithIgnoreCase(result, "/"))
        ) {
            result = "/" + result;
        }

        return result;
    }

    static String handlePath(String value) {
        final var localValue = PathFunctions.removeQuotes(value);
        return PathFunctions.handleSpaces(localValue);
    }
}