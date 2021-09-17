package com.neathorium.thorium.core.constants;

import com.neathorium.thorium.core.records.caster.BasicCastData;
import com.neathorium.thorium.core.records.caster.WrappedCastData;
import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;

public abstract class CastDataConstants {
    public static final WrappedCastData<Object> WRAPPED_OBJECT = new WrappedCastData<>(CoreDataConstants.NULL_OBJECT, CoreConstants.OBJECT_CASTER_FUNCTION);
    public static final WrappedCastData<String> WRAPPED_STRING = new WrappedCastData<>(CoreDataConstants.NULL_STRING, CoreConstants.STRING_CASTER_FUNCTION);
    public static final WrappedCastData<Boolean> WRAPPED_BOOLEAN = new WrappedCastData<>(CoreDataConstants.NULL_BOOLEAN, CoreConstants.BOOLEAN_CASTER_FUNCTION);
    public static final BasicCastData<Object> OBJECT = new BasicCastData<>(CoreConstants.STOCK_OBJECT, CoreConstants.OBJECT_CASTER_FUNCTION);
    public static final BasicCastData<String> STRING = new BasicCastData<>(CoreFormatterConstants.EMPTY, CoreConstants.STRING_CASTER_FUNCTION);
    public static final BasicCastData<Boolean> BOOLEAN = new BasicCastData<>(false, CoreConstants.BOOLEAN_CASTER_FUNCTION);
    public static final BasicCastData<Void> VOID = new BasicCastData<>(null, CoreConstants.VOID_CASTER_FUNCTION);
}
