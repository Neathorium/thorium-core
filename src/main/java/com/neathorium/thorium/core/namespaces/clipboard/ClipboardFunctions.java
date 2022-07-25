package com.neathorium.thorium.core.namespaces.clipboard;

import com.neathorium.thorium.core.constants.clipboard.ClipboardConstants;
import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.data.constants.CoreDataConstants;
import com.neathorium.thorium.core.data.namespaces.DataFunctions;
import com.neathorium.thorium.core.data.namespaces.factories.DataFactoryFunctions;
import com.neathorium.thorium.core.data.namespaces.predicates.DataPredicates;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.namespaces.ExceptionHandlers;
import com.neathorium.thorium.core.namespaces.exception.ClipboardExceptionHandlers;
import com.neathorium.thorium.core.namespaces.validators.clipboard.ClipboardValidators;
import com.neathorium.thorium.core.records.HandleResultData;
import com.neathorium.thorium.core.records.clipboard.ClipboardData;
import com.neathorium.thorium.exceptions.namespaces.ExceptionFunctions;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;

import java.awt.datatransfer.StringSelection;
import java.util.function.Function;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface ClipboardFunctions {
    private static Data<Boolean> copyToClipboardCore(ClipboardData<String> data, String message) {
        final var nameof = "copyToClipboard";
        final var errorMessage = ClipboardValidators.isValidClipboardData(data);
        if (isNotBlank(errorMessage)) {
            return DataFactoryFunctions.getInvalidBooleanWith(nameof, errorMessage);
        }

        final var setResult = ClipboardExceptionHandlers.setContentsHandler(data.CLIPBOARD(), new StringSelection(message));
        if (DataPredicates.isInvalidOrFalse(setResult)) {
            return DataFactoryFunctions.replaceMessage(setResult, nameof, DataFunctions.getFormattedMessage(setResult));
        }

        final var getResult = getFromClipboardCore(data);
        final var exception = getResult.EXCEPTION();
        final var status = isNotBlank(getResult.OBJECT()) && ExceptionFunctions.isNonException(exception);
        return DataFactoryFunctions.getBoolean(status, nameof, "Copying(\"" + message + "\") to clipboard was " + (status ? "" : "un") + "successful" + CoreFormatterConstants.END_LINE, exception);
    }

    private static Data<String> getFromClipboardCore(ClipboardData<String> data) {
        final var nameof = "getFromClipboard";
        final var getResult = ClipboardExceptionHandlers.transferHandler(data);
        if (DataPredicates.isInvalidOrFalse(getResult)) {
            return DataFactoryFunctions.getWith(CoreFormatterConstants.EMPTY, getResult.STATUS(), nameof, DataFunctions.getFormattedMessage(getResult), getResult.EXCEPTION());
        }

        final var castResult = ExceptionHandlers.classCastHandler(new HandleResultData<>(data.CAST_DATA().CASTER, getResult.OBJECT(), data.CAST_DATA().DEFAULT_VALUE));
        return DataFactoryFunctions.replaceName(castResult, nameof);
    }

    static Function<String, Data<Boolean>> copyToClipboard() {
        return message -> NullablePredicates.isNotNull(message) ? copyToClipboardCore(ClipboardConstants.CLIPBOARD_DATA, message) : CoreDataConstants.NULL_BOOLEAN;
    }

    static Data<Boolean> copyToClipboard(String message) {
        return copyToClipboard().apply(message);
    }
}
