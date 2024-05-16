package com.neathorium.thorium.core.wait.records;

import java.time.Clock;

public record WaitTimeData(WaitTimeEntryDataPair ENTRY_PAIR_DATA, Clock CLOCK) {}
