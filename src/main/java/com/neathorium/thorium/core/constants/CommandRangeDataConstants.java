package com.neathorium.thorium.core.constants;

import com.neathorium.thorium.core.namespaces.command.CommandRangeDataFactory;
import com.neathorium.thorium.core.records.command.CommandRangeData;

public abstract class CommandRangeDataConstants {
    public static final int MAXIMUM_COMMAND_LIMIT = 20;
    public static final int MINIMUM_COMMAND_LIMIT = 1;
    public static final int TWO_COMMANDS_VALUE = 2;

    public static final CommandRangeData DEFAULT_RANGE = CommandRangeDataFactory.getWithDefaultRangeInvalidator(MINIMUM_COMMAND_LIMIT, MAXIMUM_COMMAND_LIMIT);
    public static final CommandRangeData TWO_COMMANDS_RANGE = CommandRangeDataFactory.getWithDefaultRangeInvalidator(TWO_COMMANDS_VALUE, TWO_COMMANDS_VALUE);
}
