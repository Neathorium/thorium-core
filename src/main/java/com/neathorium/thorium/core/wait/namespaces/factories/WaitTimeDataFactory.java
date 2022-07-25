package com.neathorium.thorium.core.wait.namespaces.factories;

import com.neathorium.thorium.core.wait.constants.WaitConstants;
import com.neathorium.thorium.core.wait.records.WaitTimeData;

import java.time.Clock;
import java.time.Duration;

public interface WaitTimeDataFactory {
    static WaitTimeData getDefault() {
        return new WaitTimeData(WaitConstants.INTERVAL, WaitConstants.TIMEOUT, WaitConstants.CLOCK);
    }

    static WaitTimeData getWithDefaultClock(Duration interval, Duration timeout) {
        return new WaitTimeData(interval, timeout, WaitConstants.CLOCK);
    }

    static WaitTimeData getWithDefaultTimeout(Duration interval, Clock clock) {
        return new WaitTimeData(interval, WaitConstants.TIMEOUT, clock);
    }

    static WaitTimeData getWithDefaultInterval(Duration timeout, Clock clock) {
        return new WaitTimeData(WaitConstants.INTERVAL, timeout, clock);
    }

    static WaitTimeData getWithDefaultTimeoutAndClock(Duration interval) {
        return new WaitTimeData(interval, WaitConstants.TIMEOUT, WaitConstants.CLOCK);
    }

    static  WaitTimeData getWithDefaultIntervalAndClock(Duration timeout) {
        return new WaitTimeData(WaitConstants.INTERVAL, timeout, WaitConstants.CLOCK);
    }

    static WaitTimeData getWithDefaultIntervalAndTimeout(Clock clock) {
        return new WaitTimeData(WaitConstants.INTERVAL, WaitConstants.TIMEOUT, clock);
    }

    static WaitTimeData getWithDefaultClock(int interval, int timeout) {
        return new WaitTimeData(Duration.ofMillis(interval), Duration.ofMillis(timeout), WaitConstants.CLOCK);
    }

    static WaitTimeData getWithDefaultTimeout(int interval, Clock clock) {
        return new WaitTimeData(Duration.ofMillis(interval), WaitConstants.TIMEOUT, clock);
    }

    static WaitTimeData getWithDefaultInterval(int timeout, Clock clock) {
        return new WaitTimeData(WaitConstants.INTERVAL, Duration.ofMillis(timeout), clock);
    }

    static WaitTimeData getWithDefaultTimeoutAndClock(int interval) {
        return new WaitTimeData(Duration.ofMillis(interval), WaitConstants.TIMEOUT, WaitConstants.CLOCK);
    }

    static  WaitTimeData getWithDefaultIntervalAndClock(int timeout) {
        return new WaitTimeData(WaitConstants.INTERVAL, Duration.ofMillis(timeout), WaitConstants.CLOCK);
    }

    static WaitTimeData getWithDefaultClock(int interval, Duration timeout) {
        return new WaitTimeData(Duration.ofMillis(interval), timeout, WaitConstants.CLOCK);
    }

    static WaitTimeData getWithDefaultClock(Duration interval, int timeout) {
        return new WaitTimeData(interval, Duration.ofMillis(timeout), WaitConstants.CLOCK);
    }
}
