package com.neathorium.thorium.core.constants;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;

import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class FileUtilitiesConstants {
    public static final Path NULL_PATH = Paths.get(CoreFormatterConstants.EMPTY);
}
