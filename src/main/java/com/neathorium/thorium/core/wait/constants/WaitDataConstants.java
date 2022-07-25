package com.neathorium.thorium.core.wait.constants;

import com.neathorium.thorium.core.data.namespaces.factories.DataFactoryFunctions;
import com.neathorium.thorium.core.data.records.Data;

public abstract class WaitDataConstants {
    public static final Data<Void> SLEEP_START_DATA = DataFactoryFunctions.getInvalidWith(null, WaitConstants.SLEEP_NAME, WaitConstants.SLEEP_NAME);
}
