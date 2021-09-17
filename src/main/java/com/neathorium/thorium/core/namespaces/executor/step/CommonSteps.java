package com.neathorium.thorium.core.namespaces.executor.step;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.extensions.interfaces.functional.boilers.DataSupplier;
import com.neathorium.thorium.core.namespaces.DataFactoryFunctions;
import com.neathorium.thorium.core.namespaces.wait.Wait;
import com.neathorium.thorium.core.records.Data;

import java.util.function.Function;

public interface CommonSteps {
    private static Function<Void, Data<Boolean>> executeParallelTimedCore(int duration, DataSupplier<?>... steps) {
        return (v) -> StepExecutor.execute(duration, steps);
    }

    private static Function<Void, Data<Boolean>> executeParallelEndOnAnyTimedCore(int duration, DataSupplier<?>... steps) {
        return (v) -> StepExecutor.executeEndOnAnyResult(duration, steps);
    }

    private static Data<Boolean> sleepCore(int duration) {
        Wait.sleep(duration);
        return DataFactoryFunctions.getBoolean(true, "sleep", "Sleep(\"" + duration + "\" milliseconds) " + CoreFormatterConstants.WAS_SUCCESSFUL);
    }

    private static Function<Void, Data<Boolean>> sleepCoreF(int duration) {
        return (v) -> sleepCore(duration);
    }

    static DataSupplier<Boolean> executeParallelTimed(int duration, DataSupplier<?>... steps) {
        return StepFactory.voidStep(executeParallelTimedCore(duration, steps));
    }

    static DataSupplier<Boolean> executeParallelEndOnAnyTimed(int duration, DataSupplier<?>... steps) {
        return StepFactory.voidStep(executeParallelEndOnAnyTimedCore(duration, steps));
    }

    static DataSupplier<Boolean> sleep(int duration) {
        return StepFactory.voidStep(sleepCoreF(duration));
    }
}
