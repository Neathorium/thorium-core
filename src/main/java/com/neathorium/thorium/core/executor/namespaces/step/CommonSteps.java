package com.neathorium.thorium.core.executor.namespaces.step;

import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.data.interfaces.DataSupplier;
import com.neathorium.thorium.core.wait.namespaces.SleepFunctions;

import java.util.function.Function;

public interface CommonSteps {
    private static Function<Void, Data<Boolean>> executeParallelTimedCore(int duration, DataSupplier<?>... steps) {
        return (v) -> StepExecutor.execute(duration, steps);
    }

    private static Function<Void, Data<Boolean>> executeParallelEndOnAnyTimedCore(int duration, DataSupplier<?>... steps) {
        return (v) -> StepExecutor.executeEndOnAnyResult(duration, steps);
    }

    static DataSupplier<Boolean> executeParallelTimed(int duration, DataSupplier<?>... steps) {
        return StepFactory.voidStep(executeParallelTimedCore(duration, steps));
    }

    static DataSupplier<Boolean> executeParallelEndOnAnyTimed(int duration, DataSupplier<?>... steps) {
        return StepFactory.voidStep(executeParallelEndOnAnyTimedCore(duration, steps));
    }

    static DataSupplier<Boolean> sleep(int duration) {
        return SleepFunctions.sleep(duration);
    }
}
