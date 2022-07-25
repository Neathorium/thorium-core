package com.neathorium.thorium.core.namespaces.exception;

import com.neathorium.thorium.core.constants.CoreConstants;
import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.data.namespaces.factories.DataFactoryFunctions;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.records.clipboard.ClipboardData;
import com.neathorium.thorium.exceptions.constants.ExceptionConstants;
import com.neathorium.thorium.exceptions.namespaces.ExceptionFunctions;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public interface ClipboardExceptionHandlers {
    static Data<Object> transferHandler(ClipboardData<?> data) {
        var result = CoreConstants.STOCK_OBJECT;
        var exception = ExceptionConstants.EXCEPTION;
        try {
            result = data.CLIPBOARD().getContents(data.OWNER()).getTransferData(data.FLAVOR());
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
