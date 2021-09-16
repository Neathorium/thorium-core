package com.neathorium.thorium.core.constants;

import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.namespaces.executor.ExecutorFunctionDataFactory;
import com.neathorium.thorium.core.records.executor.ExecuteParametersData;
import com.neathorium.thorium.core.records.executor.ExecutorFunctionData;
import com.neathorium.thorium.core.records.SimpleMessageData;
import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;

public abstract class ExecutorConstants {
    public static final ExecuteParametersData DEFAULT_EXECUTION_DATA = new ExecuteParametersData(CommandRangeDataConstants.DEFAULT_RANGE, CoreUtilities::isAllDone, CoreFormatter::getExecutionEndMessage);
    public static final ExecutorFunctionData DEFAULT_EXECUTION_ENDED = ExecutorFunctionDataFactory.getWithExecuteParametersDataAndDefaultExitCondition(new SimpleMessageData(CoreFormatterConstants.EXECUTION_ENDED), DEFAULT_EXECUTION_DATA);
}
