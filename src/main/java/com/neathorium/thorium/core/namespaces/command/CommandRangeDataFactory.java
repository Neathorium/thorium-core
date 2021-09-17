package com.neathorium.thorium.core.namespaces.command;

import com.neathorium.thorium.core.constants.CommandRangeDataConstants;
import com.neathorium.thorium.core.extensions.interfaces.functional.TriFunction;
import com.neathorium.thorium.core.namespaces.validators.Range;
import com.neathorium.thorium.core.records.command.CommandRangeData;

public interface CommandRangeDataFactory {
    static CommandRangeData getWithDefaultRangeValues(TriFunction<Integer, Integer, Integer, Boolean> invalidator) {
        return new CommandRangeData(invalidator, CommandRangeDataConstants.MINIMUM_COMMAND_LIMIT, CommandRangeDataConstants.MAXIMUM_COMMAND_LIMIT);
    }

    static CommandRangeData getWithDefaultRangeInvalidator(int min, int max) {
        return new CommandRangeData(Range::isOutOfRange, min, max);
    }

    static CommandRangeData getWithDefaults() {
        return getWithDefaultRangeInvalidator(CommandRangeDataConstants.MINIMUM_COMMAND_LIMIT, CommandRangeDataConstants.MAXIMUM_COMMAND_LIMIT);
    }
}
