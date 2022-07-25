package com.neathorium.thorium.core.records.process;

import java.util.Map;

public record ProcessGetWithDefaultsData(Map<String, ApplicationData> dataMap, ApplicationData invalidAppData, String applicationPath) {}
