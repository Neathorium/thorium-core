package com.neathorium.thorium.core.platform.enums;

import com.neathorium.thorium.core.platform.constants.PlatformConstants;
import com.neathorium.thorium.java.extensions.interfaces.IEnumKey;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum PlatformKey implements IEnumKey {
    WINDOWS(Arrays.asList(PlatformConstants.WINDOWS, "windows", "Windows10", "Win", "win", "windows10", "win10", "Win10").toArray(new String[0])),
    MAC(Arrays.asList(PlatformConstants.MAC, "MacOS", "mac", "macOS", "macOS", "Mac OS", "mac OS", "Apple").toArray(new String[0])),
    LINUX(Arrays.asList(PlatformConstants.LINUX, "linux", "Ubuntu", "ubuntu", "Ubuntu 18.04").toArray(new String[0])),
    UNKNOWN(Arrays.asList(PlatformConstants.UNKNOWN, "unknown", "N/A", "Not Available").toArray(new String[0]));

    private static final Map<String, PlatformKey> VALUES = new HashMap<>();
    private final String[] NAMES;
    private final String NAME;

    static {
        for(var value : values()) {
            VALUES.putIfAbsent(value.NAME, value);
        }
    }

    PlatformKey(String[] names, String name) {
        this.NAMES = names;
        this.NAME = name;
    }

    PlatformKey(String[] names) {
        this(names, names[0]);
    }

    public String getName() {
        return this.NAME;
    }

    public String[] getNames() {
        return this.NAMES;
    }

    public static PlatformKey getValueOf(String name) {
        return VALUES.getOrDefault(name, PlatformKey.WINDOWS);
    }
}
