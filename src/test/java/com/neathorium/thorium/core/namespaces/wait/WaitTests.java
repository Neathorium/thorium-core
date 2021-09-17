package com.neathorium.thorium.core.namespaces.wait;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.exceptions.WaitTimeoutException;
import com.neathorium.thorium.core.namespaces.DataFactoryFunctions;
import com.neathorium.thorium.core.namespaces.DataFunctions;
import com.neathorium.thorium.core.namespaces.executor.step.StepExecutor;
import com.neathorium.thorium.core.namespaces.executor.step.StepFactory;
import com.neathorium.thorium.core.namespaces.factories.wait.WaitDataFactory;
import com.neathorium.thorium.core.namespaces.predicates.DataPredicates;
import com.neathorium.thorium.core.namespaces.factories.wait.WaitTimeDataFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

public class WaitTests {
    private static int count1 = 0;
    private static int increaseAndGetCount1() {
        return ++count1;
    }

    private static int count2 = 0;
    private static int increaseAndGetCount2() {
        return ++count2;
    }

    private static int count3 = 0;
    private static int increaseAndGetCount3() {
        return ++count3;
    }

    private static int count32 = 0;
    private static int increaseAndGetCount32() {
        return ++count32;
    }

    private static int count33 = 0;
    private static int increaseAndGetCount33() {
        return ++count33;
    }

    private static int count4 = 0;
    private static int increaseAndGetCount4() {
        return ++count4;
    }

    @DisplayName("Wait Repeat - one always fails second")
    @Test
    void oneFailsSecond() {
        final var countStep = StepFactory.voidStep((Void nothing) -> DataFactoryFunctions.getBoolean(increaseAndGetCount1() == 3, "test1", "Step was okay"));
        final var trueStringStep = StepFactory.voidStep((Void nothing) -> DataFactoryFunctions.getWith("Applesauce", false, "test2", "StringStep was oookay"));
        final var steps = StepExecutor.executeState("waitRepeat Test result message", countStep, trueStringStep);
        final var waitData = WaitDataFactory.getWith(steps, DataPredicates::isExecutionValidNonFalse, "Steps passed", WaitTimeDataFactory.getWithDefaultClock(100, 1000));

        Assertions.assertThrows(WaitTimeoutException.class, () -> Wait.repeatWithDefaultState(waitData));
    }

    @DisplayName("Wait Repeat - one always fails first")
    @Test
    void oneFailsFirst() {
        final var countStep = StepFactory.step((Void nothing) -> DataFactoryFunctions.getBoolean(increaseAndGetCount2() == 3, "test1", "Step was okay"), null);
        final var trueStringStep = StepFactory.step((Void nothing) -> DataFactoryFunctions.getWith("Applesauce", false, "test2", "StringStep was oookay"), null);
        final var steps = StepExecutor.executeState("waitRepeat Test result message", trueStringStep, countStep);
        final var waitData = WaitDataFactory.getWith(steps, DataPredicates::isExecutionValidNonFalse, "Steps passed", WaitTimeDataFactory.getWithDefaultClock(100, 1000));

        Assertions.assertThrows(WaitTimeoutException.class, () -> Wait.repeatWithDefaultState(waitData));
    }

    @DisplayName("Wait Repeat - none fails over time")
    @Test
    @Tags(@Tag("slow"))
    void anoneFails() {
        final var countStep = StepFactory.step((Void nothing) -> DataFactoryFunctions.getBoolean(increaseAndGetCount3() == 3, "test1", "Step was okay"), null);
        final var trueStringStep = StepFactory.step((Void nothing) -> DataFactoryFunctions.getWith("Applesauce", true, "test2", "StringStep was oookay"), null);
        final var steps = StepExecutor.executeState("waitRepeat Test result message", countStep, trueStringStep);
        final var waitData = WaitDataFactory.getWith(steps, DataPredicates::isExecutionValidNonFalse, "Steps passed", WaitTimeDataFactory.getWithDefaultClock(100, 300000));
        final var result = Wait.repeatWithDefaultState(waitData);

        Assertions.assertTrue(result.status, DataFunctions.getFormattedMessage(result));
    }

    @DisplayName("Wait Repeat - none fails over time, limit 3")
    @Test
    @Tags(@Tag("slow"))
    void noneFailsLimitThree() {
        final var countStep = StepFactory.step((Void nothing) -> DataFactoryFunctions.getBoolean(increaseAndGetCount32() == 3, "test1", "Step was okay"), null);
        final var trueStringStep = StepFactory.step((Void nothing) -> DataFactoryFunctions.getWith("Applesauce", true, "test2", "StringStep was oookay"), null);
        final var steps = StepExecutor.executeState("waitRepeat Test result message", countStep, trueStringStep);
        final var waitData = WaitDataFactory.getWith(steps, DataPredicates::isExecutionValidNonFalse, "Steps passed", WaitTimeDataFactory.getWithDefaultClock(100, 300000));
        final var result = Wait.repeatWithDefaultState(waitData, 3);

        Assertions.assertTrue(result.status, DataFunctions.getFormattedMessage(result));
    }

    @DisplayName("Wait Repeat - none fails over time, limit 2")
    @Test
    @Tags(@Tag("slow"))
    void noneFailsLimitTwo() {
        final var countStep = StepFactory.step((Void nothing) -> DataFactoryFunctions.getBoolean(increaseAndGetCount33() == 3, "test1", "Step was okay"), null);
        final var trueStringStep = StepFactory.step((Void nothing) -> DataFactoryFunctions.getWith("Applesauce", true, "test2", "StringStep was oookay"), null);
        final var steps = StepExecutor.executeState("waitRepeat Test result message", countStep, trueStringStep);
        final var waitData = WaitDataFactory.getWith(steps, DataPredicates::isExecutionValidNonFalse, "Steps passed", WaitTimeDataFactory.getWithDefaultClock(100, 300000));

        Assertions.assertThrows(WaitTimeoutException.class, () -> Wait.repeatWithDefaultState(waitData, 2));
    }

    @DisplayName("Wait Repeat - both always fail")
    @Test
    void waitRepeatTest() {
        final var countStep = StepFactory.step((Void nothing) -> DataFactoryFunctions.getBoolean(increaseAndGetCount4() < -1, "test1", "Step was okay"), null);
        final var trueStringStep = StepFactory.step((Void nothing) -> DataFactoryFunctions.getWith("Applesauce", false, "test2", "StringStep was oookay"), null);
        final var steps = StepExecutor.executeState("waitRepeat Test result message", countStep, trueStringStep);
        final var waitData = WaitDataFactory.getWith(steps, DataPredicates::isExecutionValidNonFalse, "Steps passed", WaitTimeDataFactory.getWithDefaultClock(100, 1000));

        Assertions.assertThrows(WaitTimeoutException.class, () -> Wait.repeatWithDefaultState(waitData));
    }

    @DisplayName("Wait Repeat - single one always fails")
    @Test
    void singleOneAlwaysFailsTest() {
        final var countStep = StepFactory.step((Void nothing) -> DataFactoryFunctions.getBoolean(increaseAndGetCount4() < -1, "test1", "Step was okay"), null);
        final var steps = StepExecutor.executeState("waitRepeat Test result message", countStep);
        final var waitData = WaitDataFactory.getWith(steps, DataPredicates::isExecutionValidNonFalse, "Steps passed", WaitTimeDataFactory.getWithDefaultClock(100, 1000));

        Assertions.assertThrows(WaitTimeoutException.class, () -> Wait.repeatWithDefaultState(waitData));
    }

    @DisplayName("Sleep test")
    @Test
    void sleepTest() {
        Assertions.assertDoesNotThrow(() -> Wait.sleep(3000), "Sleep threw an exception" + CoreFormatterConstants.END_LINE);
    }
}
