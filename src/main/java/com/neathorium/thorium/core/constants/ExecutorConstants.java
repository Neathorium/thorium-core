package com.neathorium.thorium.core.constants;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.data.namespaces.factories.DataFactoryFunctions;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.executor.namespaces.ExecutorFunctionDataFactory;
import com.neathorium.thorium.core.executor.namespaces.ExecutorFunctionDataFunctions;
import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;
import com.neathorium.thorium.core.records.SimpleMessageData;
import com.neathorium.thorium.core.records.executor.ExecuteParametersData;
import com.neathorium.thorium.core.records.executor.ExecutorFunctionData;

public abstract class ExecutorConstants {
    public static final Data<Boolean> NO_STEPS = DataFactoryFunctions.getBoolean(true, "noSteps", "No Steps were provided" + CoreFormatterConstants.END_LINE);
    public static final ExecuteParametersData DEFAULT_EXECUTION_DATA = new ExecuteParametersData(
        CommandRangeDataConstants.DEFAULT_RANGE,
        ExecutorFunctionDataFunctions::isAllDone,
        CoreFormatter::getExecutionEndMessage
    );
    public static final ExecutorFunctionData DEFAULT_EXECUTION_ENDED = ExecutorFunctionDataFactory.getWithExecuteParametersDataAndDefaultExitCondition(
        new SimpleMessageData(CoreFormatterConstants.EXECUTION_ENDED),
        ExecutorConstants.DEFAULT_EXECUTION_DATA
    );
}
