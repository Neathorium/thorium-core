package com.neathorium.thorium.core.constants;

import com.neathorium.thorium.core.extensions.boilers.StringSet;
import com.neathorium.thorium.core.namespaces.DataFactoryFunctions;
import com.neathorium.thorium.core.records.Data;
import com.neathorium.thorium.core.records.MethodData;
import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;

public abstract class CoreDataConstants {
    public static final Data<Boolean> NULL_BOOLEAN = DataFactoryFunctions.getInvalidBooleanWith("nullBoolean", "nullBoolean data" + CoreFormatterConstants.END_LINE);
    public static final Data<Boolean> DATA_PARAMETER_WAS_NULL = DataFactoryFunctions.getInvalidBooleanWith("dataParameterWasNull", "Data parameter" + CoreFormatterConstants.WAS_NULL);
    public static final Data<Boolean> PARAMETERS_NULL_BOOLEAN = DataFactoryFunctions.getInvalidBooleanWith("parametersNullBoolean", "Parameters" + CoreFormatterConstants.WAS_NULL);
    public static final Data<Boolean> NO_STEPS = DataFactoryFunctions.getBoolean(true, "noSteps", "No Steps were provided" + CoreFormatterConstants.END_LINE);

    public static final Data<Integer> NULL_INTEGER = DataFactoryFunctions.getWith(0, false, "nullInteger", "nullInteger data.");
    public static final Data<Integer> NO_ELEMENTS_FOUND_DATA_FALSE_OR_NULL = DataFactoryFunctions.getWith(0, false, "noElementsFoundDataFalseOrNull", "No elements were found, data was false or null" + CoreFormatterConstants.END_LINE);

    public static final Data<MethodData> NULL_METHODDATA = DataFactoryFunctions.getWith(CoreConstants.STOCK_METHOD_DATA, false, "nullMethodData", "Null methodData data.");

    public static final Data<Object> NULL_OBJECT = DataFactoryFunctions.getWith(CoreConstants.STOCK_OBJECT, false, "nullObject", "null object data.");

    public static final Data<Object[]> NULL_PARAMETER_ARRAY = DataFactoryFunctions.getWith(CoreConstants.EMPTY_OBJECT_ARRAY, false, "nullParameterArray", "Null Parameter Array.");

    public static final Data<String> DATA_WAS_NULL_OR_FALSE_STRING = DataFactoryFunctions.getString(CoreFormatterConstants.EMPTY, "dataWasNullOrFalseString", CoreFormatterConstants.DATA_NULL_OR_FALSE);
    public static final Data<String> NULL_STRING = DataFactoryFunctions.getWith(CoreFormatterConstants.EMPTY, false, "nullString", "nullString data.");

    public static final Data<StringSet> NULL_STRING_SET = DataFactoryFunctions.getWith(CoreConstants.NULL_STRING_SET, false, "nullStringSet", "Null String Set data" + CoreFormatterConstants.END_LINE);

    public static final Data<Void> NULL_VOID = DataFactoryFunctions.getWith(null, false, "nullVoid", "Void data" + CoreFormatterConstants.END_LINE);

    public static final Data<Void> VOID_TASK_RAN_SUCCESSFULLY = DataFactoryFunctions.getWith(null, true, "runVoidTaskCore", "Void task successful" + CoreFormatterConstants.END_LINE);
}
