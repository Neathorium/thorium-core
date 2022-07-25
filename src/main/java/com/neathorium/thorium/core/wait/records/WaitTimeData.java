package com.neathorium.thorium.core.wait.records;

import java.time.Clock;
import java.time.Duration;

public record WaitTimeData(Duration INTERVAL, Duration DURATION, Clock CLOCK) {}
