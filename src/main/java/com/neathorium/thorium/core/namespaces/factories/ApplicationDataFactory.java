package com.neathorium.thorium.core.namespaces.factories;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.records.process.ApplicationData;

public interface ApplicationDataFactory {
    static ApplicationData getWith(String name, String path, String arguments) {
        return new ApplicationData(name, path, arguments);
    }

    static ApplicationData getWithNoArguments(String name, String path) {
        return getWith(name, path, CoreFormatterConstants.EMPTY);
    }
}
