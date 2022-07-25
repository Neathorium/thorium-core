package com.neathorium.thorium.core.wait.constants;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.data.namespaces.factories.DataFactoryFunctions;
import com.neathorium.thorium.core.data.records.Data;

import java.time.Clock;
import java.time.Duration;

public abstract class WaitConstants {
    public static final int DEFAULT_SLEEP_TIMEOUT = 3000;
    public static final int DEFAULT_SLEEP_INTERVAL = 300;
    public static final Clock CLOCK = Clock.systemDefaultZone();

    public static final Duration DEFAULT_WAIT_DURATION = Duration.ofMillis(DEFAULT_SLEEP_TIMEOUT);
    public static final Duration DEFAULT_WAIT_INTERVAL = Duration.ofMillis(DEFAULT_SLEEP_INTERVAL);
    public static final Duration TIMEOUT = DEFAULT_WAIT_DURATION;
    public static final Duration INTERVAL = DEFAULT_WAIT_INTERVAL;

    public static final String SLEEP_NAME = "sleep";

    public static final Data<Void> VOID_TASK_RAN_SUCCESSFULLY = DataFactoryFunctions.getWith(null, true, "runVoidTaskCore", "Void task successful" + CoreFormatterConstants.END_LINE);
}
