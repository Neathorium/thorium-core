package com.neathorium.thorium.core.wait.namespaces.factories;

import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;
import com.neathorium.thorium.core.wait.records.WaitTimeEntryData;
import com.neathorium.thorium.core.wait.records.WaitTimeEntryDataPair;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

public interface WaitTimeEntryDataPairFactory {
    private static WaitTimeEntryDataPair getWithCore(
        BiFunction<WaitTimeEntryData, WaitTimeEntryData, WaitTimeEntryDataPair> constructor,
        BiFunction<String, Throwable, IllegalArgumentException> exceptionConstructor,
        WaitTimeEntryData intervalData,
        WaitTimeEntryData durationData
    ) {
        final var errors = (
                CoreFormatter.isNullMessageWithName(constructor, "WaitTimeEntryDataPair Constructor") +
                CoreFormatter.isNullMessageWithName(intervalData, "Interval Data") +
                CoreFormatter.isNullMessageWithName(durationData, "Duration Data")
        );
        var localExceptionConstructor = exceptionConstructor;
        if (NullablePredicates.isNull(localExceptionConstructor)) {
            localExceptionConstructor = IllegalArgumentException::new;
        }
        if (StringUtils.isNotBlank(errors)) {
            throw localExceptionConstructor.apply(errors, null);
        }

        return constructor.apply(intervalData, durationData);
    }

    static WaitTimeEntryDataPair getWith(WaitTimeEntryData intervalData, WaitTimeEntryData durationData) {
        return WaitTimeEntryDataPairFactory.getWithCore(WaitTimeEntryDataPair::new, IllegalArgumentException::new, intervalData, durationData);
    }

    static WaitTimeEntryDataPair getWithMilliseconds(long interval, long duration) {
        return WaitTimeEntryDataPairFactory.getWith(new WaitTimeEntryData(interval, TimeUnit.MILLISECONDS), new WaitTimeEntryData(duration, TimeUnit.MILLISECONDS));
    }

    static WaitTimeEntryDataPair getDurationInMilliseconds(long duration) {
        return WaitTimeEntryDataPairFactory.getWithMilliseconds(0, duration);
    }
}
