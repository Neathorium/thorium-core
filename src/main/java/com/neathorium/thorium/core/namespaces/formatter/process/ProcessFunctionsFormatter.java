package com.neathorium.thorium.core.namespaces.formatter.process;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.records.process.ApplicationData;

public interface ProcessFunctionsFormatter {
    static String getBuilderFormattedParametersMessage(String name, String path, String arguments) {
        final var nameFragment = name + " starting" + CoreFormatterConstants.COLON_NEWLINE;
        final var pathFragment = "Path: " + path + CoreFormatterConstants.COLON_NEWLINE;
        final var argumentFragment = "Arguments: " + arguments + CoreFormatterConstants.END_LINE;
        return nameFragment + pathFragment + argumentFragment;
    }

    static String getBuilderFormattedParametersMessage(ApplicationData data) {
        return getBuilderFormattedParametersMessage(data.name, data.path, data.arguments);
    }
}