package com.neathorium.thorium.core.constants;

import com.neathorium.thorium.core.constants.cast.CastConstants;
import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.data.constants.CoreDataConstants;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.records.caster.CastData;

public abstract class CastDataConstants {
    public static final CastData<Data<Object>, Object> WRAPPED_OBJECT = new CastData<>(CoreDataConstants.NULL_OBJECT, CastConstants.OBJECT_CASTER_FUNCTION);
    public static final CastData<Data<String>, String> WRAPPED_STRING = new CastData<>(CoreDataConstants.NULL_STRING, CastConstants.STRING_CASTER_FUNCTION);
    public static final CastData<Data<Boolean>, Boolean> WRAPPED_BOOLEAN = new CastData<>(CoreDataConstants.NULL_BOOLEAN, CastConstants.BOOLEAN_CASTER_FUNCTION);
    public static final CastData<Object, Object> OBJECT = new CastData<>(CoreConstants.STOCK_OBJECT, CastConstants.OBJECT_CASTER_FUNCTION);
    public static final CastData<String, String> STRING = new CastData<>(CoreConstants.EMPTY_STRING, CastConstants.STRING_CASTER_FUNCTION);
    public static final CastData<Boolean, Boolean> BOOLEAN = new CastData<>(false, CastConstants.BOOLEAN_CASTER_FUNCTION);
    public static final CastData<Void, Void> VOID = new CastData<>(null, CastConstants.VOID_CASTER_FUNCTION);
    public static final CastData<String, String> CLIPBOARD_STRING = new CastData<>(CoreConstants.EMPTY_STRING, CastConstants.STRONGER_STRING_CASTER_FUNCTION);
}
