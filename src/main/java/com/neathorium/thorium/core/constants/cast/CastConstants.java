package com.neathorium.thorium.core.constants.cast;

import com.neathorium.thorium.core.constants.CoreConstants;
import com.neathorium.thorium.java.extensions.namespaces.utilities.BooleanUtilities;

import java.util.function.Function;

public abstract class CastConstants {
    public static final Function<Object, Object> OBJECT_CASTER_FUNCTION = Object.class::cast;
    public static final Function<Object, String> STRING_CASTER_FUNCTION = String.class::cast;
    public static final Function<Object, Boolean> BOOLEAN_CASTER_FUNCTION = BooleanUtilities::castToBoolean;
    public static final Function<Object, Void> VOID_CASTER_FUNCTION = object -> null;
    public static final Function<Object, Object> OBJECT_DEFAULT_FUNCTION = object -> CoreConstants.STOCK_OBJECT;
}
