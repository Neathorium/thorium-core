package com.neathorium.thorium.core.namespaces.validators.clipboard;

import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;
import com.neathorium.thorium.core.records.clipboard.ClipboardData;

import static org.apache.commons.lang3.StringUtils.isBlank;

public interface ClipboardValidators {
    static String isValidClipboardData(ClipboardData<?> data) {
        var message = CoreFormatter.isNullMessageWithName(data, "Clipboard data");
        if (isBlank(message)) {
            message += (
                CoreFormatter.isNullMessageWithName(data.castData, "Basic cast Data") +
                CoreFormatter.isNullMessageWithName(data.clipboard, "Clipboard") +
                CoreFormatter.isNullMessageWithName(data.flavor, "Clipboard Data Flavor")
            );
        }

        return CoreFormatter.getNamedErrorMessageOrEmpty("isValidClipboardData", message);
    }
}
