package com.neathorium.thorium.core.records.process;

import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;

import java.util.Map;
import java.util.Objects;

public class ProcessGetWithDefaultsData {
    public final Map<String, ApplicationData> dataMap;
    public final ApplicationData invalidAppData;
    public final String applicationPath;

    public ProcessGetWithDefaultsData(Map<String, ApplicationData> dataMap, ApplicationData invalidAppData, String applicationPath) {
        this.dataMap = dataMap;
        this.invalidAppData = invalidAppData;
        this.applicationPath = applicationPath;
    }

    @Override
    public boolean equals(Object o) {
        if (CoreUtilities.isEqual(this, o)) {
            return true;
        }

        if (NullableFunctions.isNull(o) || CoreUtilities.isNotEqual(getClass(), o.getClass())) {
            return false;
        }

        final var that = (ProcessGetWithDefaultsData) o;
        return (
            CoreUtilities.isEqual(dataMap, that.dataMap) &&
            CoreUtilities.isEqual(invalidAppData, that.invalidAppData) &&
            CoreUtilities.isEqual(applicationPath, that.applicationPath)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataMap, invalidAppData, applicationPath);
    }
}
