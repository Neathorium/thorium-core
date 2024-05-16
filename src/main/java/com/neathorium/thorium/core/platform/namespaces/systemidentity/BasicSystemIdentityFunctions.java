package com.neathorium.thorium.core.platform.namespaces.systemidentity;

import com.neathorium.thorium.core.constants.systemidentity.BasicSystemIdentityConstants;
import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;

import com.neathorium.thorium.core.data.namespaces.factories.DataFactoryFunctions;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.platform.enums.PlatformKey;
import com.neathorium.thorium.exceptions.constants.ExceptionConstants;
import com.neathorium.thorium.exceptions.namespaces.ExceptionFunctions;
import com.neathorium.thorium.java.extensions.namespaces.enums.EnumKeyFunctions;
import com.neathorium.thorium.java.extensions.namespaces.predicates.EqualsPredicates;
import com.neathorium.thorium.java.extensions.namespaces.utilities.BooleanUtilities;
import com.neathorium.thorium.java.extensions.namespaces.utilities.FileUtilities;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface BasicSystemIdentityFunctions {
    static Data<String> alwaysWriteToFile(String path, String content) {
        final var localPath = Paths.get(path);
        var exception = ExceptionConstants.EXCEPTION;
        try {
            Files.writeString(localPath, content, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException ex) {
            exception = ex;
        }

        final var status = ExceptionFunctions.isNonException(exception);
        final var message = "File(\"" + localPath + "\") written " + (status ? "" : "un") + "successfully with content(\"" + content + "\")" + CoreFormatterConstants.END_LINE;
        return DataFactoryFunctions.getWith(path, status, "alwaysWriteToFile", message, exception);
    }

    static Data<String> writeToFile(String path, String content) {
        final var nameof = "writeToFile";
        if (FileUtilities.isExisting(path)) {
            return DataFactoryFunctions.getValidWith(path, nameof, "File already exists at path (\"" + path + "\")" + CoreFormatterConstants.END_LINE);
        }

        final var data = BasicSystemIdentityFunctions.alwaysWriteToFile(path, content);
        return DataFactoryFunctions.replaceName(data, nameof);
    }

    static boolean isOS(String os, PlatformKey key) {
        return StringUtils.isNotBlank(os) && EnumKeyFunctions.isIn(os.trim(), key);
    }

    static boolean isLinux(String os) {
        return BasicSystemIdentityFunctions.isOS(os, PlatformKey.LINUX);
    }

    static boolean isMac(String os) {
        return BasicSystemIdentityFunctions.isOS(os, PlatformKey.MAC);
    }

    static boolean isWindows(String os) {
        return BasicSystemIdentityFunctions.isOS(os, PlatformKey.WINDOWS);
    }

    static PlatformKey getSystemTypeOrUnknown(String os) {
        if (BasicSystemIdentityFunctions.isWindows(os)) {
            return PlatformKey.WINDOWS;
        }

        if (BasicSystemIdentityFunctions.isMac(os)) {
            return PlatformKey.MAC;
        }

        if (BasicSystemIdentityFunctions.isLinux(os)) {
            return PlatformKey.LINUX;
        }

        return PlatformKey.UNKNOWN;
    }

    static boolean isLinux() {
        return BasicSystemIdentityFunctions.isLinux(BasicSystemIdentityConstants.OS_NAME);
    }

    static boolean isMac() {
        return BasicSystemIdentityFunctions.isMac(BasicSystemIdentityConstants.OS_NAME);
    }

    static boolean isWindows() {
        return BasicSystemIdentityFunctions.isWindows(BasicSystemIdentityConstants.OS_NAME);
    }

    static PlatformKey getSystemTypeOrUnknown() {
        return BasicSystemIdentityFunctions.getSystemTypeOrUnknown(BasicSystemIdentityConstants.OS_NAME);
    }

    private static PlatformKey isOrUnknown(boolean status, PlatformKey identity) {
        return status ? identity : PlatformKey.UNKNOWN;
    }

    static PlatformKey isWindowsOrUnknown() {
        return BasicSystemIdentityFunctions.isOrUnknown(isWindows(), PlatformKey.WINDOWS);
    }

    static PlatformKey isLinuxOrUnknown() {
        return BasicSystemIdentityFunctions.isOrUnknown(isLinux(), PlatformKey.LINUX);
    }

    static PlatformKey isMacOrUnknown() {
        return BasicSystemIdentityFunctions.isOrUnknown(isMac(), PlatformKey.MAC);
    }

    static PlatformKey isSystemType(String os) {
        final var osType = BasicSystemIdentityFunctions.getSystemTypeOrUnknown(os);

        final var isUnknown = EqualsPredicates.isEqual(osType, PlatformKey.UNKNOWN);
        var message = "";
        if (isUnknown) {
            message += "Operating System is (\"" + osType + "\"), which is unsupported" + CoreFormatterConstants.END_LINE;
        }

        final var isExpectedUnsupported = EnumKeyFunctions.isNotIn(os, osType);
        if (BooleanUtilities.isFalse(isUnknown) && isExpectedUnsupported) {
            message += "Operating system is (\"" + osType + "\"), expected is (\"" + os + "\"), is unsupported";
        }

        if (isNotBlank(message)) {
            throw new IllegalStateException("There is an issue: " + message);
        }

        return osType;
    }
}
