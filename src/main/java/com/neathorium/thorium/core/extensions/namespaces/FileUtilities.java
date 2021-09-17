package com.neathorium.thorium.core.extensions.namespaces;

import com.neathorium.thorium.core.constants.FileUtilitiesConstants;
import com.neathorium.thorium.core.constants.exception.ExceptionConstants;
import com.neathorium.thorium.core.namespaces.exception.ExceptionFunctions;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public interface FileUtilities {
    static boolean isExisting(String path) {
        var localPath = FileUtilitiesConstants.NULL_PATH;
        var exception = ExceptionConstants.EXCEPTION;
        try {
            localPath = Paths.get(path);
        } catch (InvalidPathException ex) {
            exception = new InvalidPathException(ex.getInput(), "isExisting: " + ex.getMessage());
        }

        return ExceptionFunctions.isNonException(exception) && Files.exists(localPath);
    }
}
