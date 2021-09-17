package com.neathorium.thorium.core.constants.process;

import com.neathorium.thorium.core.platform.enums.PlatformKey;
import com.neathorium.thorium.core.platform.namespaces.PlatformFunctions;

import java.util.Map;

public abstract class ProcessConstants {
    public static final String WIN_NULL_FILE_PATH = "NUL";
    public static final String ANY_NULL_FILE_PATH = "/dev/null";

    public static final ProcessBuilder NULL_BUILDER = new ProcessBuilder();

    public static final Map<PlatformKey, String> NULL_FILE_PLATFORM_MAP = PlatformFunctions.getPlatformMapWithUnknown(WIN_NULL_FILE_PATH, ANY_NULL_FILE_PATH, ANY_NULL_FILE_PATH, ANY_NULL_FILE_PATH);
}
