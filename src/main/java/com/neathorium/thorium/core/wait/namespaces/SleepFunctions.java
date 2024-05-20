package com.neathorium.thorium.core.wait.namespaces;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.data.namespaces.factories.DataFactoryFunctions;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.data.interfaces.DataSupplier;
import com.neathorium.thorium.core.executor.namespaces.step.StepFactory;

import java.util.function.Function;

public interface SleepFunctions {
    private static Data<Boolean> sleepCore(int duration) {
        final var nameof = "SleepFunctions.sleep";
        WaitFunctions.sleep(duration);
        return DataFactoryFunctions.getBoolean(true, nameof, "Sleep(\"" + duration + "\" milliseconds)" + CoreFormatterConstants.WAS_SUCCESSFUL);
    }

    static DataSupplier<Boolean> sleep(int duration) {
        return StepFactory.step(SleepFunctions::sleepCore, duration);
    }

    static Function<?, Void> sleepFunction(int timeout) {
        return any -> {
            SleepFunctions.sleep(timeout).apply();
            return null;
        };
    }
}
