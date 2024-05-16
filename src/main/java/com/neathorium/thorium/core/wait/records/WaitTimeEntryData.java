package com.neathorium.thorium.core.wait.records;

import java.util.concurrent.TimeUnit;

public record WaitTimeEntryData(long LENGTH, TimeUnit TIME_UNIT) {}