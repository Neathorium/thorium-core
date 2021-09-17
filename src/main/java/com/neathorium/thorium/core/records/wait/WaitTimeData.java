package com.neathorium.thorium.core.records.wait;

import java.time.Clock;
import java.time.Duration;
import java.util.Objects;

public class WaitTimeData {
    public final Duration interval;
    public final Duration duration;
    public final Clock clock;

    public WaitTimeData(Duration interval, Duration duration, Clock clock) {
        this.interval = interval;
        this.duration = duration;
        this.clock = clock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (WaitTimeData) o;
        return Objects.equals(interval, that.interval) && Objects.equals(duration, that.duration) && Objects.equals(clock, that.clock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interval, duration, clock);
    }
}
