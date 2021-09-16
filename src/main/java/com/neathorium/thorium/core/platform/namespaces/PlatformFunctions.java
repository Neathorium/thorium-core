package com.neathorium.thorium.core.platform.namespaces;

import com.neathorium.thorium.core.platform.enums.PlatformKey;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import static java.util.Map.entry;

public interface PlatformFunctions {
    static <T> Map<PlatformKey, T> getPlatformMap(T windowsEntry, T macEntry, T linuxEntry) {
        return Collections.unmodifiableMap(
            new EnumMap<>(
                Map.ofEntries(
                    entry(PlatformKey.WINDOWS, windowsEntry),
                    entry(PlatformKey.MAC, macEntry),
                    entry(PlatformKey.LINUX, linuxEntry)
                )
            )
        );
    }

    static <T> Map<PlatformKey, T> getPlatformMapWithUnknown(T windowsEntry, T macEntry, T linuxEntry, T unknownEntry) {
        final var map = new EnumMap<>(
            getPlatformMap(windowsEntry, macEntry, linuxEntry)
        );
        map.putIfAbsent(PlatformKey.UNKNOWN, unknownEntry);

        return Collections.unmodifiableMap(map);
    }
}
