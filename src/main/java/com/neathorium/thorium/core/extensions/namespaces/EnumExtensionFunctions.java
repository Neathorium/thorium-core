package com.neathorium.thorium.core.extensions.namespaces;

import java.util.Map;

public interface EnumExtensionFunctions {
    static <T extends Enum> T getOrDefault(Map<String, T> enumValues, String name, T defaultValue) {
        return enumValues.getOrDefault(name, defaultValue);
    }

    static <T extends Enum> boolean contains(Map<String, T> enumValues, String name) {
        return enumValues.containsKey(name);
    }
}
