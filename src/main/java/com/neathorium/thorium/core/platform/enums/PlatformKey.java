package com.neathorium.thorium.core.platform.enums;

import com.neathorium.thorium.core.platform.constants.PlatformConstants;
import com.neathorium.thorium.java.extensions.interfaces.IEnumKey;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum PlatformKey implements IEnumKey {
    WINDOWS(Arrays.asList(PlatformConstants.WINDOWS, "windows", "Windows10", "Win", "win", "windows10", "win10", "Win10").toArray(new String[0])),
    WINDOWS_11(Arrays.asList(PlatformConstants.WINDOWS_11, "windows 11", "Windows11", "windows11", "Win11", "win11", "Win 11", "win 11", "Windows Eleven").toArray(new String[0])),
    WINDOWS_10(Arrays.asList(PlatformConstants.WINDOWS_10, "windows 10", "Windows10", "windows10", "Win10", "win10", "Win 10", "win 10", "Windows Ten").toArray(new String[0])),
    MAC(Arrays.asList(PlatformConstants.MAC, "MacOS", "mac", "macOS", "macOS", "Mac OS", "mac OS", "Apple").toArray(new String[0])),
    MAC_BIG_SUR(Arrays.asList(PlatformConstants.MAC_BIG_SUR, "Mac BigSur", "Mac BS").toArray(new String[0])),
    MAC_CATALINA(Arrays.asList(PlatformConstants.MAC_CATALINA, "Mac catalina").toArray(new String[0])),
    MAC_MONTEREY(Arrays.asList(PlatformConstants.MAC_MONTEREY, "Mac monterey").toArray(new String[0])),
    MAC_VENTURA(Arrays.asList(PlatformConstants.MAC_VENTURA, "Mac ventura").toArray(new String[0])),
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
