package com.neathorium.thorium.core.wait.constants;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.data.namespaces.factories.DataFactoryFunctions;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.wait.namespaces.factories.WaitTimeEntryDataPairFactory;
import com.neathorium.thorium.core.wait.records.WaitTimeEntryData;
import com.neathorium.thorium.core.wait.records.WaitTimeEntryDataPair;

import java.time.Clock;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public abstract class WaitConstants {
    public static final Clock CLOCK = Clock.systemDefaultZone();
    public static final int DEFAULT_SLEEP_INTERVAL = 300;
    public static final int DEFAULT_SLEEP_DURATION = 3000;

    public static final WaitTimeEntryData INTERVAL = new WaitTimeEntryData(DEFAULT_SLEEP_INTERVAL, TimeUnit.MILLISECONDS);
    public static final WaitTimeEntryData DURATION = new WaitTimeEntryData(DEFAULT_SLEEP_DURATION, TimeUnit.MILLISECONDS);

    public static final WaitTimeEntryDataPair DEFAULT_TIME_DATA = WaitTimeEntryDataPairFactory.getWith(INTERVAL, DURATION);
    public static final String SLEEP_NAME = "sleep";

    public static final Data<Void> VOID_TASK_RAN_SUCCESSFULLY = DataFactoryFunctions.getWith(null, true, "runVoidTaskCore", "Void task successful" + CoreFormatterConstants.END_LINE);
}
