package com.neathorium.thorium.core.wait.namespaces;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.data.namespaces.factories.DataFactoryFunctions;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.data.interfaces.DataSupplier;
import com.neathorium.thorium.core.namespaces.executor.step.StepFactory;

import java.util.function.Function;

public interface SleepFunctions {
    private static Data<Boolean> sleepCore(int duration) {
        WaitFunctions.sleep(duration);
        return DataFactoryFunctions.getBoolean(true, "sleep", "Sleep(\"" + duration + "\" milliseconds) " + CoreFormatterConstants.WAS_SUCCESSFUL);
    }

    private static Function<Void, Data<Boolean>> sleepCoreF(int duration) {
        return (v) -> sleepCore(duration);
    }

    static DataSupplier<Boolean> sleep(int duration) {
        return StepFactory.voidStep(sleepCoreF(duration));
    }
}
