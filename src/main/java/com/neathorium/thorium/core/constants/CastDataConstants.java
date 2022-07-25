package com.neathorium.thorium.core.constants;

import com.neathorium.thorium.core.constants.cast.CastConstants;
import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.data.constants.CoreDataConstants;
import com.neathorium.thorium.core.records.caster.BasicCastData;
import com.neathorium.thorium.core.records.caster.WrappedCastData;

public abstract class CastDataConstants {
    public static final WrappedCastData<Object> WRAPPED_OBJECT = new WrappedCastData<>(CoreDataConstants.NULL_OBJECT, CastConstants.OBJECT_CASTER_FUNCTION);
    public static final WrappedCastData<String> WRAPPED_STRING = new WrappedCastData<>(CoreDataConstants.NULL_STRING, CastConstants.STRING_CASTER_FUNCTION);
    public static final WrappedCastData<Boolean> WRAPPED_BOOLEAN = new WrappedCastData<>(CoreDataConstants.NULL_BOOLEAN, CastConstants.BOOLEAN_CASTER_FUNCTION);
    public static final BasicCastData<Object> OBJECT = new BasicCastData<>(CoreConstants.STOCK_OBJECT, CastConstants.OBJECT_CASTER_FUNCTION);
    public static final BasicCastData<String> STRING = new BasicCastData<>(CoreFormatterConstants.EMPTY, CastConstants.STRING_CASTER_FUNCTION);
    public static final BasicCastData<Boolean> BOOLEAN = new BasicCastData<>(false, CastConstants.BOOLEAN_CASTER_FUNCTION);
    public static final BasicCastData<Void> VOID = new BasicCastData<>(null, CastConstants.VOID_CASTER_FUNCTION);
}
