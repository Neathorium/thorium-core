package com.neathorium.thorium.core.namespaces.wait;

import com.neathorium.thorium.core.constants.systemidentity.ConcreteSystemIdentityConstants;
import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;

public interface WaitExceptionFunctions {
    static String getSystemInformationMessage() {
        return (
            CoreFormatterConstants.NEW_LINE +
            "System info: host: \"" +
            ConcreteSystemIdentityConstants.HOST_NAME +
            "\", ip: \"" +
            ConcreteSystemIdentityConstants.HOST_ADDRESS +
            "\", os.name: \"" +
            System.getProperty("os.name") +
            "\", os.arch: \"" +
            System.getProperty("os.arch") +
            "\", os.version: \"" +
            System.getProperty("os.version") +
            "\", java.version: \"" +
            System.getProperty("java.version") +
            "\"."
        );
    }
}
