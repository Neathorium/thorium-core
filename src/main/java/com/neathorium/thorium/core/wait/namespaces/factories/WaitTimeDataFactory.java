package com.neathorium.thorium.core.wait.namespaces.factories;

import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;
import com.neathorium.thorium.core.wait.constants.WaitConstants;
import com.neathorium.thorium.core.wait.records.WaitTimeData;
import com.neathorium.thorium.core.wait.records.WaitTimeEntryData;
import com.neathorium.thorium.core.wait.records.WaitTimeEntryDataPair;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;
import org.apache.commons.lang3.StringUtils;

import java.time.Clock;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

public interface WaitTimeDataFactory {
    static WaitTimeData getWithCore(
        BiFunction<WaitTimeEntryDataPair, Clock, WaitTimeData> constructor,
        BiFunction<String, Throwable, IllegalArgumentException> exceptionConstructor,
        WaitTimeEntryDataPair timeData,
        Clock clock
    ) {
        final var errors = (
            CoreFormatter.isNullMessageWithName(constructor, "Constructor") +
            CoreFormatter.isNullMessageWithName(exceptionConstructor, "Exception Constructor") +
            CoreFormatter.isNullMessageWithName(timeData, "Time data") +
            CoreFormatter.isNullMessageWithName(clock, "Clock")
        );
        var localExceptionConstructor = exceptionConstructor;
        if (NullablePredicates.isNull(localExceptionConstructor)) {
            localExceptionConstructor = IllegalArgumentException::new;
        }

        if (StringUtils.isNotBlank(errors)) {
            throw localExceptionConstructor.apply(errors, null);
        }
        return constructor.apply(timeData, clock);
    }
    static WaitTimeData getWith(WaitTimeEntryDataPair timeData, Clock clock) {
        return WaitTimeDataFactory.getWithCore(WaitTimeData::new, IllegalArgumentException::new, timeData, clock);
    }

    static WaitTimeData getDefault() {
        return WaitTimeDataFactory.getWith(WaitConstants.DEFAULT_TIME_DATA, WaitConstants.CLOCK);
    }

    static WaitTimeData getWith(long interval, long duration, Clock clock) {
        final var intervalEntry = new WaitTimeEntryData(interval, TimeUnit.MILLISECONDS);
        final var durationEntry = new WaitTimeEntryData(duration, TimeUnit.MILLISECONDS);
        final var pair = WaitTimeEntryDataPairFactory.getWith(intervalEntry, durationEntry);
        return WaitTimeDataFactory.getWith(pair, clock);
    }

    static WaitTimeData getWithDefaultClock(long interval, long duration) {
        return WaitTimeDataFactory.getWith(interval, duration, WaitConstants.CLOCK);
    }

    static WaitTimeData getWithDefaultClock(int interval, int timeout) {
        return WaitTimeDataFactory.getWith(interval, timeout, WaitConstants.CLOCK);
    }
}
