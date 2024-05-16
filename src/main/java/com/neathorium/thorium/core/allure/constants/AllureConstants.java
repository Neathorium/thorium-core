package com.neathorium.thorium.core.allure.constants;

import com.neathorium.thorium.core.namespaces.utilities.path.PathFunctions;

public abstract class AllureConstants {
    public static final String ALLURE_SCREENSHOT_PATH = PathFunctions.handlePath(System.getProperty("allureScreenshotPath", ""));
}
