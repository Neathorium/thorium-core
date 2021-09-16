package com.neathorium.thorium.core.namespaces.exception;

import com.neathorium.thorium.core.constants.CoreConstants;
import com.neathorium.thorium.core.constants.exception.ExceptionConstants;
import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.namespaces.DataFactoryFunctions;
import com.neathorium.thorium.core.records.Data;
import com.neathorium.thorium.core.records.clipboard.ClipboardData;
import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public interface ClipboardExceptionHandlers {
    static Data<Object> transferHandler(ClipboardData<?> data) {
        var result = CoreConstants.STOCK_OBJECT;
        var exception = ExceptionConstants.EXCEPTION;
        try {
            result = data.clipboard.getContents(data.owner).getTransferData(data.flavor);
        } catch (UnsupportedFlavorException | IOException ex) {
            exception = ex;
        }

        final var status = ExceptionFunctions.isNonException(exception);
        return DataFactoryFunctions.getWith(result, status, "transferHandler", "Copying from clipboard was" + (status ? "" : "n't") + CoreFormatterConstants.SUCCESSFUL, exception);
    }

    static Data<Boolean> setContentsHandler(Clipboard clipboard, StringSelection message) {
        var exception = ExceptionConstants.EXCEPTION;
        try {
            clipboard.setContents(message, null);
        } catch (IllegalStateException ex) {
            exception = ex;
        }

        final var status = ExceptionFunctions.isNonException(exception);
        return DataFactoryFunctions.getBoolean(status, "setContentsHandler", "Copying to clipboard was" + (status ? "" : "n't") + CoreFormatterConstants.SUCCESSFUL, exception);
    }
}
