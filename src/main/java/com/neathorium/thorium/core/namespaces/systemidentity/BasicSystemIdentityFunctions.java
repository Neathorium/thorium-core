package com.neathorium.thorium.core.namespaces.systemidentity;

import com.neathorium.thorium.core.constants.systemidentity.BasicSystemIdentityConstants;
import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.extensions.namespaces.EnumKeyFunctions;
import com.neathorium.thorium.core.platform.enums.PlatformKey;

import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface BasicSystemIdentityFunctions {
    static boolean isOS(String os, PlatformKey key) {
        return isNotBlank(os) && EnumKeyFunctions.isIn(os.trim(), key);
    }

    static boolean isLinux(String os) {
        return isOS(os, PlatformKey.LINUX);
    }

    static boolean isMac(String os) {
        return isOS(os, PlatformKey.MAC);
    }

    static boolean isWindows(String os) {
        return isOS(os, PlatformKey.WINDOWS);
    }

    static PlatformKey getSystemTypeOrUnknown(String os) {
        if (isWindows(os)) {
            return PlatformKey.WINDOWS;
        }

        if (isMac(os)) {
            return PlatformKey.MAC;
        }

        if (isLinux(os)) {
            return PlatformKey.LINUX;
        }

        return PlatformKey.UNKNOWN;
    }

    static boolean isLinux() {
        return isLinux(BasicSystemIdentityConstants.OS_NAME);
    }

    static boolean isMac() {
        return isMac(BasicSystemIdentityConstants.OS_NAME);
    }

    static boolean isWindows() {
        return isWindows(BasicSystemIdentityConstants.OS_NAME);
    }

    static PlatformKey getSystemTypeOrUnknown() {
        return getSystemTypeOrUnknown(BasicSystemIdentityConstants.OS_NAME);
    }

    private static PlatformKey isOrUnknown(boolean status, PlatformKey identity) {
        return status ? identity : PlatformKey.UNKNOWN;
    }

    static PlatformKey isWindowsOrUnknown() {
        return isOrUnknown(isWindows(), PlatformKey.WINDOWS);
    }

    static PlatformKey isLinuxOrUnknown() {
        return isOrUnknown(isLinux(), PlatformKey.LINUX);
    }

    static PlatformKey isMacOrUnknown() {
        return isOrUnknown(isMac(), PlatformKey.MAC);
    }

    static PlatformKey isSystemType(String os) {
        final var osType = getSystemTypeOrUnknown(os);

        final var isUnknown = Objects.equals(osType, PlatformKey.UNKNOWN);
        var message = "";
        if (isUnknown) {
            message += "Operating System is (\"" + osType + "\"), which is unsupported" + CoreFormatterConstants.END_LINE;
        }

        final var isExpectedUnsupported = EnumKeyFunctions.isNotIn(os, osType);
        if (CoreUtilities.isFalse(isUnknown) && isExpectedUnsupported) {
            message += "Operating system is (\"" + osType + "\"), expected is (\"" + os + "\"), is unsupported";
        }

        if (isNotBlank(message)) {
            throw new IllegalStateException("There is an issue: " + message);
        }

        return osType;
    }
}
