package com.neathorium.thorium.core.constants.wait;

import com.neathorium.thorium.core.namespaces.DataFactoryFunctions;
import com.neathorium.thorium.core.records.Data;

public abstract class WaitDataConstants {
    public static final Data<Void> SLEEP_START_DATA = DataFactoryFunctions.getInvalidWith(null, "sleep", "sleep");

}
