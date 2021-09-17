package com.neathorium.thorium.core.extensions.namespaces.validators;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.extensions.namespaces.FileUtilities;
import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;


import static org.apache.commons.lang3.StringUtils.isBlank;

public interface FileUtilitiesValidators {
    static String isExistingMessage(String path) {
        var message = CoreFormatter.isBlankMessageWithName(path, "Path");
        if (isBlank(message)) {
            if (!FileUtilities.isExisting(path)) {
                message += "File with path (\"" + path + "\") doesn't exist" + CoreFormatterConstants.END_LINE;
            }
        }

        return CoreFormatter.getNamedErrorMessageOrEmpty("isExistingMessage", message);
    }
}
