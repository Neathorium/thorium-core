package com.neathorium.thorium.core.constants;

import com.neathorium.thorium.core.namespaces.factories.MethodMessageDataFactory;
import com.neathorium.thorium.core.records.MethodMessageData;
import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;

public abstract class DataFactoryConstants {
    public static final boolean DEFAULT_STATE = false;
    public static final String DEFAULT_EXCEPTION_MESSAGE = CoreFormatterConstants.NON_EXCEPTION_MESSAGE;
    public static final MethodMessageData DEFAULT_METHOD_MESSAGE_DATA = MethodMessageDataFactory.getWith("getWith", "Default method message" + CoreFormatterConstants.END_LINE);
}
